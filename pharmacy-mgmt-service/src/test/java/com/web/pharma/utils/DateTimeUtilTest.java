package com.web.pharma.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeUtilTest {

    // ----------------------------
    // parseToDate tests
    // ----------------------------

    @Test
    void shouldParseIsoDateFormat() {
        LocalDate date = DateTimeUtil.parseToDate("1989-04-06");
        assertEquals(LocalDate.of(1989, 4, 6), date);
    }

    @Test
    void shouldParseDayMonthYearFormat() {
        LocalDate date = DateTimeUtil.parseToDate("06-04-1989");
        assertEquals(LocalDate.of(1989, 4, 6), date);
    }

    @Test
    void shouldParseSlashSeparatedFormat() {
        LocalDate date = DateTimeUtil.parseToDate("04/06/1989");
        assertEquals(LocalDate.of(1989, 4, 6), date);
    }

    @Test
    void shouldThrowExceptionForInvalidDateFormat() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> DateTimeUtil.parseToDate("1989.04.06")
        );

        assertTrue(exception.getMessage().contains("Invalid date format"));
    }

    // ----------------------------
    // formatDate tests
    // ----------------------------

    @Test
    void shouldFormatLocalDateToString() {
        LocalDate date = LocalDate.of(2024, 1, 15);
        String result = DateTimeUtil.formatDate(date);

        assertEquals("2024-01-15", result);
    }

    // ----------------------------
    // formatDateTime24 tests
    // ----------------------------

    @Test
    void shouldFormatDateTimeIn24HourFormat() {
        LocalDateTime dateTime =
                LocalDateTime.of(2024, 1, 15, 18, 30, 45);

        String result = DateTimeUtil.formatDateTime24(dateTime);

        assertEquals("2024-01-15 18:30:45", result);
    }

    // ----------------------------
    // formatDateTime12 tests
    // ----------------------------

    @Test
    void shouldFormatDateTimeIn12HourFormatPM() {
        LocalDateTime dateTime =
                LocalDateTime.of(2024, 1, 15, 18, 30, 45);

        String result = DateTimeUtil.formatDateTime12(dateTime);

        assertEquals("2024-01-15 06:30:45 PM", result);
    }

    @Test
    void shouldFormatDateTimeIn12HourFormatAM() {
        LocalDateTime dateTime =
                LocalDateTime.of(2024, 1, 15, 9, 15, 10);

        String result = DateTimeUtil.formatDateTime12(dateTime);

        assertEquals("2024-01-15 09:15:10 AM", result);
    }

    @Test
    void shouldHandleMidnightIn12HourFormat() {
        LocalDateTime dateTime =
                LocalDateTime.of(2024, 1, 15, 0, 0, 0);

        String result = DateTimeUtil.formatDateTime12(dateTime);

        assertEquals("2024-01-15 12:00:00 AM", result);
    }

    @Test
    void shouldHandleNoonIn12HourFormat() {
        LocalDateTime dateTime =
                LocalDateTime.of(2024, 1, 15, 12, 0, 0);

        String result = DateTimeUtil.formatDateTime12(dateTime);

        assertEquals("2024-01-15 12:00:00 PM", result);
    }

    // ----------------------------
    // null handling (optional)
    // ----------------------------

    @Test
    void shouldThrowNullPointerExceptionWhenFormattingNullDate() {
        assertThrows(
                NullPointerException.class,
                () -> DateTimeUtil.formatDate(null)
        );
    }
}

