package com.web.pharma.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;

public final class DateTimeUtil {

    private DateTimeUtil() {
        // prevent instantiation
    }

    // ----------------------------
    // String -> LocalDate
    // ----------------------------
    public static LocalDate parseToDate(String date) {
        List<DateTimeFormatter> formatters = List.of(
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                DateTimeFormatter.ofPattern("MM/dd/yyyy")
        );

        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDate.parse(date, formatter);
            } catch (DateTimeParseException ignored) {
            }
        }
        throw new IllegalArgumentException("Invalid date format: " + date);
    }

    // ----------------------------
    // LocalDate -> String (Date only)
    // ----------------------------
    public static String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    // ----------------------------
    // LocalDateTime -> String (24-hour)
    // ----------------------------
    public static String formatDateTime24(LocalDateTime dateTime) {
        return dateTime.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );
    }

    // ----------------------------
    // LocalDateTime -> String (12-hour with AM/PM)
    // ----------------------------
    public static String formatDateTime12(LocalDateTime dateTime) {
        return dateTime.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a", Locale.ENGLISH)
        );
    }
}

