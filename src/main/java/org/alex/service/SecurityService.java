package org.alex.service;

import jakarta.servlet.http.HttpServlet;
import org.alex.entity.User;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;

public class SecurityService extends HttpServlet {
    List<String> userTokens;
    UserService userService;

    public SecurityService(List<String> userTokens, UserService userService) {
        this.userTokens = userTokens;
        this.userService = userService;
    }

    public String generateCookie() {
        String userToken = UUID.randomUUID().toString();
        System.out.println("User Token: " + userToken);

        userTokens.add(userToken);
        for (String token : userTokens) {
            System.out.println(token);
        }
        return userToken;
    }

    public boolean cookieCheck(String cookieValue) {
        for (String token : userTokens) {
            if (userTokens.contains(cookieValue)) {
                return true;
            }
            break;
        }
        return false;
    }

    public boolean cookieRemove(String cookieValue) {
        return userTokens.remove(cookieValue);


    }

    public boolean loginCheck(String email, String password) throws NoSuchAlgorithmException, NoSuchProviderException {
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
    public boolean emailDuplicateCheck(String email) throws NoSuchAlgorithmException, NoSuchProviderException {
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

    public String getHashedPassword(String password) throws NoSuchAlgorithmException {

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
