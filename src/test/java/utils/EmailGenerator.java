package utils;

public class EmailGenerator {

    public static String generateTestioEmail() {
        return "test_automation" + System.currentTimeMillis() + "@testpro.io";
    }

    public static String generateGmailEmail() {
        return "test_automation" + System.currentTimeMillis() + "@gmail.com";
    }

    public static String generatePlusEmail() {
        return "test_automation+1_" + System.currentTimeMillis() + "@testpro.io";
    }
}
