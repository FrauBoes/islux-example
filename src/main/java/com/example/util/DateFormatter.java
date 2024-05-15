package com.example.util;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateFormatter {
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN).withLocale(Locale.ENGLISH);
}
