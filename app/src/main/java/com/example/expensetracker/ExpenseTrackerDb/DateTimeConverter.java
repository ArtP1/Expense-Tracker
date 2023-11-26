package com.example.expensetracker.ExpenseTrackerDb;

import androidx.room.TypeConverter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateTimeConverter {
    @TypeConverter
    public static LocalDateTime toLocalDate(Long timestamp) {
        if (timestamp == null) {
            return null;
        }

        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }

    @TypeConverter
    public static Long toTimestamp(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }

        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
