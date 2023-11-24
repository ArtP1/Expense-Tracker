package com.example.expensetracker.ExpenseTrackerDb.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.expensetracker.ExpenseTrackerDb.DateConverter;
import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;

import java.time.LocalDate;

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
    private long id;

    // FOREIGN KEY(s)
    private String currency;

    // COLUMNS
    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String firstName;

    @NonNull
    @TypeConverters(DateConverter.class)
    private LocalDate dateJoined;

    private double budget;

    public enum UserRole {
        USER, ADMIN, SUPER_ADMIN
    }

    private boolean hasNotifications;

    @NonNull
    @ColumnInfo(collate = ColumnInfo.NOCASE) // Case-Insensitive Collation
    private UserRole role;

    @Ignore
    public User(long id, String currency, @NonNull String username, @NonNull String password, @NonNull String firstName, @NonNull UserRole role) {
        LocalDate currDate = LocalDate.now();
        this.id = id;
        this.currency = currency;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.hasNotifications = false;
        this.budget = 0.0;
        this.role = role;
        this.dateJoined = currDate;
    }

    public User(@NonNull String username, @NonNull String password, @NonNull String firstName, @NonNull UserRole role) {
        LocalDate currDate = LocalDate.now();
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.role = role;
        this.budget = 0.0;
        this.hasNotifications = false;
        this.dateJoined = currDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    @NonNull
    public UserRole getRole() {
        return role;
    }

    public void setRole(@NonNull UserRole role) {
        this.role = role;
    }

    @NonNull
    public LocalDate getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(@NonNull LocalDate dateJoined) {
        this.dateJoined = dateJoined;
    }

    public boolean hasNotifications() {
        return hasNotifications;
    }

    public void setHasNotifications(boolean hasNotifications) {
        this.hasNotifications = hasNotifications;
    }
}
