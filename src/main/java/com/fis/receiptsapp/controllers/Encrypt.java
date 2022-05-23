package com.fis.receiptsapp.controllers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Encrypt {

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static String sha256(String message) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(
                    message.getBytes(StandardCharsets.UTF_8));

            return bytesToHex(encodedhash);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return null;
    }


}
