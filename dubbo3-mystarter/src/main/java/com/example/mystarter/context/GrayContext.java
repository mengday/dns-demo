package com.example.mystarter.context;

public class GrayContext {
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void set(String value) {
        threadLocal.set(value);
    }

    public static String get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }

    public static boolean isGray() {
        return "gray".equals(get());
    }
}
