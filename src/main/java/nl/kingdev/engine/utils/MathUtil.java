package nl.kingdev.engine.utils;

import java.util.Random;

public class MathUtil {

    private static Random RANDOM = new Random(System.currentTimeMillis());
    public static double distance(float x, float y, float x1, float y1) {
        return Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1));
    }
    public static int randomInt(int min, int max) {
        return RANDOM.nextInt((max - min)) + min;
    }


}