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


@Entity(tableName = ExpenseTrackerDatabase.EXPENSE_TABLE,
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "user_id",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Category.class,
                        parentColumns = "id",
                        childColumns = "category_id",
                        onUpdate = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = PaymentMethod.class,
                        parentColumns = "id",
                        childColumns = "payment_method_id",
                        onUpdate = ForeignKey.CASCADE
                ),
                @ForeignKey(entity = Currency.class,
                        parentColumns = "ISO",
                        childColumns = "currency",
                        onUpdate = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index("user_id"),
                @Index("category_id"),
                @Index("payment_method_id"),
                @Index("currency")
        }
)
public class Expense {
    // PRIMARY KEY
    @PrimaryKey(autoGenerate = true)
    private int id;

    // FOREIGN KEY(s)
    private int user_id;

    private int category_id;

    private int payment_method_id;

    @ColumnInfo(collate = ColumnInfo.NOCASE) // Case-Insensitive Collation
    private String currency;

    // COLUMNS
    private double amount;

    @NonNull
    private String title;

    @TypeConverters(DateConverter.class)
    private LocalDate dateSubmitted;

    private String description;
    private String location;

    @Ignore
    public Expense(int id, int user_id, int category_id, int payment_method_id, @NonNull String title, String currency, double amount, String description, String location) {
        LocalDate currDate = LocalDate.now();
        this.id = id;
        this.user_id = user_id;
        this.category_id = category_id;
        this.payment_method_id = payment_method_id;
        this.title = title;
        this.currency = currency;
        this.amount = amount;
        this.dateSubmitted = currDate;
        this.description = description;
        this.location = location;
    }

    public Expense(int user_id, int category_id, int payment_method_id, @NonNull String title, String currency, double amount, String description, String location) {
        LocalDate currDate = LocalDate.now();
        this.user_id = user_id;
        this.category_id = category_id;
        this.payment_method_id = payment_method_id;
        this.title = title;
        this.currency = currency;
        this.amount = amount;
        this.dateSubmitted = currDate;
        this.description = description;
        this.location = location;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(LocalDate dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getPayment_method_id() {
        return payment_method_id;
    }

    public void setPayment_method_id(int payment_method_id) {
        this.payment_method_id = payment_method_id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
