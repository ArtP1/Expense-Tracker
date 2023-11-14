package com.example.expensetracker.ExpenseTrackerDb.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;

@Entity(tableName = ExpenseTrackerDatabase.CATEGORY_TABLE)
public class Category {
    // PRIMARY KEY
    @PrimaryKey(autoGenerate = true)
    private int id;

    // FOREIGN KEY
    private Integer color_id;

    // COLUMNS
    @NonNull
    private String name;

    @NonNull
    private String icon;

    public Category(int id, Integer color_id, @NonNull String name, @NonNull String icon) {
        this.id = id;
        this.color_id = color_id;
        this.name = name;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getColor_id() {
        return color_id;
    }

    public void setColor_id(Integer color_id) {
        this.color_id = color_id;
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
}
