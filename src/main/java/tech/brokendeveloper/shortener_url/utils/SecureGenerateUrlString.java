package tech.brokendeveloper.shortener_url.utils;

import java.security.SecureRandom;

public class SecureGenerateUrlString {

    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateUrlString() {
        int length = secureRandom.nextInt(4) + 5;
        StringBuilder stringBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = secureRandom.nextInt(CHARS.length());
            stringBuilder.append(CHARS.charAt(index));
        }

        String generatedUrl = stringBuilder.toString();

        return generatedUrl;
    }

}
