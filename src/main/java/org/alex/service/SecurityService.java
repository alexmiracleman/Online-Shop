package org.alex.service;

import org.alex.entity.Salt;
import org.alex.entity.User;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;

public class SecurityService {
    private List<String> userTokens;
    private UserService userService;
    private SaltService saltService;

    public SecurityService(List<String> userTokens, UserService userService, SaltService saltService) {
        this.userTokens = userTokens;
        this.userService = userService;
        this.saltService = saltService;
    }

    public String generateToken() {
        String userToken = UUID.randomUUID().toString();
        System.out.println("User Token: " + userToken);
        return userToken;
    }
    public String getCookieValue() {
        String token = generateToken();
        userTokens.add(token);
        return token;
    }


    public boolean cookieCheck(String cookieValue) {
        for (String token : userTokens) {
            if (token.contains(cookieValue)) {
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
                if (user.getPassword().equals(getHashedPassword(password) + getSalt(email)) ) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

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



    public String generateSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
        byte[] rawSalt = new byte[16];
        sr.nextBytes(rawSalt);
        String salt = rawSalt.toString();
        return salt;

    }
    public String getSalt(String email) {
        List<Salt> salts = saltService.findAll();
        for (Salt salt : salts) {
            if (salt.getEmail().equals(email)) {
                return salt.getPassSalt();
            }
        }
        return null;
    }


}
