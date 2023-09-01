package org.alex.service;

import org.alex.entity.Credentials;
import org.alex.web.util.Session;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SecurityServiceTest {
    private final UserService userService = mock(UserService.class);
    private final List<Session> sessionList = Collections.synchronizedList(new ArrayList<>());
    private final SecurityService securityService = new SecurityService(userService, sessionList);
    private final Credentials credentials = new Credentials("test@test.com", "qwerty");
    private int sessionTimeToLive = 60;

    @Test
    void testToGenerateUserToken() {
        String userToken = securityService.generateToken();
        assertNotNull(userToken);
    }

    @Test
    void testToAuthenticateUser() throws NoSuchAlgorithmException, NoSuchProviderException {
        securityService.authenticate(credentials);
    }

    @Test
    void testToGetSession() {
        Session session = Session.builder()
                .token("test")
                .expireDate(LocalDateTime.now().plusMinutes(sessionTimeToLive))
                .userType("USER")
                .cart(new ArrayList<>())
                .build();
        sessionList.add(session);
        assertTrue(sessionList.contains(session));
        assertEquals(session, securityService.getSession("test"));
        sessionList.clear();
    }

    @Test
    void testToLogout() {
        Session session = Session.builder().token("test").build();
        sessionList.add(session);
        assertTrue(sessionList.contains(session));
        securityService.logout("test");
        assertFalse(sessionList.contains(session));
    }

    @Test
    void testToCheckIfAuthorized() throws NoSuchAlgorithmException {
        securityService.isAuth("test@test.com", "qwerty");
    }

    @Test
    void testEmailDuplicateCheck() {
        assertTrue(securityService.emailDuplicateCheck("test@test.com"));
    }

    @Test
    void testEmailAndPasswordNotEmpty() {
        assertFalse(securityService.emailAndPasswordNotNullCheck("", ""));
        assertFalse(securityService.emailAndPasswordNotNullCheck("", "123"));
        assertFalse(securityService.emailAndPasswordNotNullCheck("123", ""));
        assertTrue(securityService.emailAndPasswordNotNullCheck("abc", "123"));
    }
}