package org.alex.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.alex.entity.Credentials;
import org.alex.entity.User;
import org.alex.util.PasswordUtils;
import org.alex.web.util.Session;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SecurityService {
    private List<Session> sessionList = Collections.synchronizedList(new ArrayList<>());
    private UserService userService;
    private PasswordUtils passwordUtils;
    private int sessionTimeToLive;

    public SecurityService(UserService userService, List<Session> sessionList) {
        this.userService = userService;
        this.sessionList = sessionList;
    }

    public String generateToken() {
        String userToken = UUID.randomUUID().toString();
        return userToken;
    }

    public Session authenticate(Credentials credentials) throws NoSuchAlgorithmException, NoSuchProviderException {
        User user = isAuth(credentials.getEmail(), credentials.getPassword());
        if (user != null) {
            String token = generateToken();
            LocalDateTime expireDate = LocalDateTime.now().plusMinutes(sessionTimeToLive);
            Session session = Session.builder()
                    .token(token)
                    .expireDate(expireDate)
                    .userType(user.getUserType())
                    .cart(new ArrayList<>())
                    .build();
            sessionList.add(session);
            return session;
        } else {
            return null;
        }
    }

    public Session getSession(String token) {
        for (Session session : sessionList) {
            if (session.getToken().equals(token)) {
                if (session == null) {
                    return null;
                }
                if (session.getExpireDate().isAfter(LocalDateTime.now())) {
                    return session;
                }
                if (session.getExpireDate().isBefore(LocalDateTime.now())) {
                    sessionList.remove(session);
                    return null;
                }
                return session;
            }
        }
        return null;
    }

    public void logout(String tokenToRemove) {
        for (Session session : sessionList) {
            if (session.getToken().equals(tokenToRemove)) {
                sessionList.remove(session);
                break;
            }
        }
    }

    public User isAuth(String email, String password) throws NoSuchAlgorithmException {
        List<User> users = userService.findAll();
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                if (user.getPassword().equals(passwordUtils.generateHash(password, user.getSalt()))) {
                    return user;
                }
                break;
            }
        }
        return null;
    }

    public boolean emailDuplicateCheck(String email) {
        List<User> users = userService.findAll();
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }

    public boolean emailAndPasswordNotNullCheck(String email, String password) {
        if (password.isBlank() || email.isBlank()) {
            return false;
        }
        return true;
    }
}

