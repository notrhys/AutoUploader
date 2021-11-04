package me.rhys.uploader.util;

public class Logger {
    public static void log(String s) {
        System.out.printf("[LOG (%s)] %s\n", TimeUtil.getSystemTime(), s);
    }

    public static void warn(String s) {
        System.out.printf("[WARN (%s)] %s\n", TimeUtil.getSystemTime(), s);
    }

    public static void error(String s) {
        System.out.printf("[ERROR (%s)] %s\n", TimeUtil.getSystemTime(), s);
    }

    public static void debug(String s) {
        System.out.printf("[DEBUG (%s)] %s\n", TimeUtil.getSystemTime(), s);
    }
}
