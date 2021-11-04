package me.rhys.uploader.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    private static final SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static String getSystemTime() {
        return simpleDateFormat.format(new Date(System.currentTimeMillis()));
    }

    public static String getMonthFormat() {
        return monthFormat.format(new Date(System.currentTimeMillis()));
    }
}
