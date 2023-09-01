package org.alex.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;


public class PasswordUtils {

    public String generateHash(String password, String salt) throws NoSuchAlgorithmException {
        String saltyPassword = password + salt;
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(saltyPassword.getBytes());
        BigInteger bigInteger = new BigInteger(1, messageDigest);
        String hashedPassword = bigInteger.toString(16);
        return hashedPassword;
    }

    public String generateSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
        byte[] rawSalt = new byte[16];
        sr.nextBytes(rawSalt);
        String salt = rawSalt.toString();
        return salt;

    }

}
