package com.example.expensetracker.ExpenseTrackerDb.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;

@Entity(tableName = ExpenseTrackerDatabase.CURRENCY_TABLE)
public class Currency {
    // PRIMARY KEY
    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String ISO;

    // COLUMNS
    @NonNull
    private String name;

    @NonNull
    private String symbol;

    public Currency(String ISO, @NonNull String name, @NonNull String symbol) {
        this.ISO = ISO;
        this.name = name;
        this.symbol = symbol;
    }

    public String getISO() {
        return ISO;
    }

    public void setISO(String ISO) {
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
}
