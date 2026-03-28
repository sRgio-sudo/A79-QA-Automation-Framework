package utils;

import java.util.Random;

public class DataGenerator {

    public static int generateRandomInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    public static String generateRandomName(String prefix) {
        return prefix + " " + System.currentTimeMillis();
    }

    public static String generateStringLength(int length) {
        if (length <0) return "";
        return "A".repeat(length);
    }
}
