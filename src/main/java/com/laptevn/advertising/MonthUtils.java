package com.laptevn.advertising;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class MonthUtils {
    public static String monthToString(Month month) {
        return month.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }
}