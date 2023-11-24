package com.example.expensetracker.ExpenseTrackerDb.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;

@Entity(tableName = ExpenseTrackerDatabase.CURRENCY_TABLE)
public class Currency {
    // PRIMARY KEY
    @NonNull
    @ColumnInfo(collate = ColumnInfo.NOCASE)
    @PrimaryKey(autoGenerate = false)
    private String ISO;

    // COLUMNS
    @NonNull
    private String name;

    @NonNull
    private String symbol;

    public Currency(@NonNull String ISO, @NonNull String name, @NonNull String symbol) {
        this.ISO = ISO;
        this.name = name;
        this.symbol = symbol;
    }

    @NonNull
    public String getISO() {
        return ISO;
    }

    public void setISO(@NonNull String ISO) {
        this.ISO = ISO;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(@NonNull String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return name + " - " + ISO;
    }
}
