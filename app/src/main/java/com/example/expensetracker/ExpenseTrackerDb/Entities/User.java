package com.example.expensetracker.ExpenseTrackerDb.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;

@Entity(tableName = ExpenseTrackerDatabase.USER_TABLE)
public class User {
    // PRIMARY KEY
    @PrimaryKey(autoGenerate = true)
    private int id;

    // FOREIGN KEY(s)
    private Integer currency_id;

    // COLUMNS
    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String first_name;

    private String last_name;

    private double budget;

    public enum UserRole {
        USER, ADMIN, SUPER_ADMIN
    }

    @NonNull
    @ColumnInfo(collate = ColumnInfo.NOCASE) // Case-Insensitive Collation
    private UserRole role;

    public User(int id, Integer currency_id, String username, String password, String first_name, String last_name, double budget, UserRole role) {
        this.id = id;
        this.currency_id = currency_id;
        this.username = username;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.budget = budget;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getCurrency() {
        return currency_id;
    }

    public void setCurrency(Integer currency_id) {
        this.currency_id = currency_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
