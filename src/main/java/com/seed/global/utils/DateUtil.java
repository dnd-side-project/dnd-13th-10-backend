package com.seed.global.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class DateUtil {
    private DateUtil() {}

    // 표준 포맷 상수
    public static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm");

    // ---- parse helpers ----
    public static LocalDate parseDate(String date) {
        if (isBlank(date)) return null;
        try {
            return LocalDate.parse(date.trim(), DATE_FMT);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format (expected yyyy-MM-dd): " + date);
        }
    }

    public static LocalTime parseTime(String time) {
        if (isBlank(time)) return null;
        try {
            return LocalTime.parse(time.trim(), TIME_FMT);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid time format (expected HH:mm): " + time);
        }
    }

    /** date(yyyy-MM-dd) + time(HH:mm) -> LocalDateTime (둘 중 하나라도 null이면 null 반환) */
    public static LocalDateTime combine(String date, String time) {
        LocalDate d = parseDate(date);
        LocalTime t = parseTime(time);
        if (d == null || t == null) return null;
        return LocalDateTime.of(d, t);
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty() || "null".equalsIgnoreCase(s.trim());
    }
}
