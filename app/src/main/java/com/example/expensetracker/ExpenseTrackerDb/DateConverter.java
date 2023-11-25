package com.example.expensetracker.ExpenseTrackerDb;

import androidx.room.TypeConverter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class DateConverter {
    @TypeConverter
    public static LocalDate toLocalDate(Long timestamp) {
        if (timestamp == null) {
            return null;
        }

        return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @TypeConverter
    public static Long toTimestamp(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }

        return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
