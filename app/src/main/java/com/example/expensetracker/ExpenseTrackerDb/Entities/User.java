package com.example.expensetracker.ExpenseTrackerDb.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;

@Entity(tableName = ExpenseTrackerDatabase.USER_TABLE,
        foreignKeys = @ForeignKey(entity = Currency.class,
                        parentColumns = "ISO",
                        childColumns = "currency",
                        onDelete = ForeignKey.SET_NULL,
                        onUpdate = ForeignKey.CASCADE),
        indices = @Index("currency")
)
public class User {
    // PRIMARY KEY
    @PrimaryKey(autoGenerate = true)
    private int id;

    // FOREIGN KEY(s)
    private String currency;

    // COLUMNS
    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String firstName;

    private Double budget;

    public enum UserRole {
        USER, ADMIN, SUPER_ADMIN
    }

    @NonNull
    @ColumnInfo(collate = ColumnInfo.NOCASE) // Case-Insensitive Collation
    private UserRole role;

    @Ignore
    public User(int id, String currency, @NonNull String username, @NonNull String password, @NonNull String firstName, @NonNull UserRole role) {
        this.id = id;
        this.currency = currency;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.role = role;
    }

    public User(@NonNull String username, @NonNull String password, @NonNull String firstName, @NonNull UserRole role) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NonNull String firstName) {
        this.firstName = firstName;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    @NonNull
    public UserRole getRole() {
        return role;
    }

    public void setRole(@NonNull UserRole role) {
        this.role = role;
    }
}
