package utils;

import org.apache.commons.lang3.RandomStringUtils;

public class EmailGenerator {

    public static String generateTestioEmail() {
        return "test_automation" + System.currentTimeMillis() + "@testpro.io";
    }

    public static String generateGmailEmail() {
        String time = String.valueOf(System.currentTimeMillis());
        String shortTime = time.substring(time.length() - 3);
        return "s.trofimov.testing" + shortTime + "@gmail.com";
    }

    public static String generatePlusEmail() {
        String time = String.valueOf(System.currentTimeMillis());
        String shortTime = time.substring(time.length() - 3);
        return "test_automation+" + shortTime + "@testpro.io";
    }

    public static String generatePlusTextEmail() {
        String randomLetters = RandomStringUtils.randomAlphabetic(5).toLowerCase();
        return "test_automation+" + randomLetters + "@testpro.io";
    }
}
