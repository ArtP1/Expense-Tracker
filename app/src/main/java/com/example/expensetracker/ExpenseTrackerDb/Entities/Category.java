package com.example.expensetracker.ExpenseTrackerDb.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;

@Entity(tableName = ExpenseTrackerDatabase.CATEGORY_TABLE)
public class Category {
    // PRIMARY KEY
    @PrimaryKey(autoGenerate = true)
    private int id;

    // COLUMNS
    private String color_hex;

    @NonNull
    private String name;

    @NonNull
    private String icon;

    @Ignore
    public Category(int id, String color_hex, @NonNull String name, @NonNull String icon) {
        this.id = id;
        this.color_hex = color_hex;
        this.name = name;
        this.icon = icon;
    }

    @Ignore
    public Category(@NonNull String name) {
        this.name = name;
    }

    public Category(String color_hex, @NonNull String name, @NonNull String icon) {
        this.color_hex = color_hex;
        this.name = name;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor_hex() {
        return color_hex;
    }

    public void setColor_hex(String color_hex) {
        this.color_hex = color_hex;
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
