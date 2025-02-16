package rubberduck.org.sportinksystemalt.shared.common.util;

import java.util.Random;

public class RandomStringUtil {
    private static final String ALPHA = "abcdefghijklmnopqrstuvwxyz"; // a-z
    private static final String ALPHA_UPPER_CASE = ALPHA.toUpperCase(); // A-Z
    private static final String ALPHA_NUMERIC = ALPHA + ALPHA_UPPER_CASE + DIGITS;
    private static final String ALL = ALPHA_NUMERIC + SPECIALS;
    private static final String DIGITS = "0123456789"; // 0-9
    private static final String SPECIALS = "~=+%^*/()[]{}/!@#$?|"; // special characters
    private static final Random generator = new Random();

    public static int randomNumber(int min, int max) {
        return generator.nextInt((max - min) + 1) + min;
    }

    public static String randomAlphaNumeric(int numberOfCharacter) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfCharacter; i++) {
            int number = randomNumber(0, ALL.length() - 1);
            char ch = ALL.charAt(number);
            sb.append(ch);
        }
        return sb.toString();
    }
}
