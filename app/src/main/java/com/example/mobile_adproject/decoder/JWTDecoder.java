package com.example.mobile_adproject.decoder;

import java.util.Base64;

public class JWTDecoder {
    public static String decodeBase64(String input) {
        try {
            byte[] decodedBytes = Base64.getUrlDecoder().decode(input);
            return new String(decodedBytes, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}




