package com.example.expensetracker.ExpenseTrackerDb.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;

@Entity(tableName = ExpenseTrackerDatabase.CATEGORY_TABLE)
public class Category {

    // PRIMARY KEY
    @NonNull
    @PrimaryKey
    private String name;

    // COLUMN(S)
    @NonNull
    private String icon;


    public Category(String name, @NonNull String icon) {
        this.name = name;
        this.icon = icon;
    }

    @Ignore
    public Category(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getIcon() {
        return icon;
    }

    public void setIcon(@NonNull String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return name;
    }
}
