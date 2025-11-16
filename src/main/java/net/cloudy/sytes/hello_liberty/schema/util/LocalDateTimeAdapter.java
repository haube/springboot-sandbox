package net.cloudy.sytes.hello_liberty.schema.util;

import java.time.LocalDateTime;

public class LocalDateTimeAdapter {
    public static LocalDateTime parse(String value) {
        return value != null ? LocalDateTime.parse(value) : null;
    }

    public static String print(LocalDateTime value) {
        return value != null ? value.toString() : null;
    }
}