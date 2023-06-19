package org.alex.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.alex.entity.User;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.List;

public class SecurityService extends HttpServlet {
    List<String> userTokens;
    UserService userService;

    public SecurityService(List<String> userTokens, UserService userService) {
        this.userTokens = userTokens;
        this.userService = userService;
    }

    public boolean cookieCheck(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    if (userTokens.contains(cookie.getValue())) {
                        return true;
                    }
                    break;
                }
            }
        }
        return false;
    }

    public void cookieRemove(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    if (userTokens.contains(cookie.getValue())) {
                        userTokens.remove(cookie.getValue());
                    }
                }

            }
        }
    }

    public boolean loginCheck(HttpServletRequest req) throws NoSuchAlgorithmException, NoSuchProviderException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        List<User> users = userService.findAll();
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                if (user.getPassword().equals(getHashedPassword(password))) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

    //And
    public boolean emailDuplicateCheck(HttpServletRequest req) throws NoSuchAlgorithmException, NoSuchProviderException {
        String email = req.getParameter("email");
        List<User> users = userService.findAll();
        for (User user : users) {
            if (user.getEmail().equals(email)) {

                    return false;
                }
            }

        return true;
    }

    public boolean passwordNotNullCheck(HttpServletRequest req) {
        String password = req.getParameter("password");
        if (password.isBlank()) {
            return false;
        }
        return true;
    }

    public String getHashedPassword(String password) throws NoSuchAlgorithmException, NoSuchProviderException {

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(password.getBytes());
        BigInteger bigInteger = new BigInteger(1, messageDigest);
        String generatedPassword = bigInteger.toString(16);
        System.out.println(generatedPassword);
        return generatedPassword;

    }

    private static String getSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt.toString();
    }


}
