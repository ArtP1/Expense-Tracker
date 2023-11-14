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

import java.util.Date;

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
                        childColumns = "currency_type",
                        onUpdate = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index("user_id"),
                @Index("category_id"),
                @Index("payment_method_id"),
                @Index("currency_type")
        }
)
public class Expense {
    // PRIMARY KEY
    @PrimaryKey(autoGenerate = true)
    private int id;

    // FOREIGN KEY(s)
    @NonNull
    private int user_id; // CANNOT BE NULL

    @NonNull
    private int category_id;

    @NonNull
    private int payment_method_id;

    @NonNull
    @ColumnInfo(collate = ColumnInfo.NOCASE) // Case-Insensitive Collation
    private String currency_type;

    // COLUMNS
    private double amount;

    @TypeConverters(DateConverter.class)
    private Date date;

    private String description;
    private String location;

    @Ignore
    public Expense(int id, int user_id, int category_id, int payment_method_id, @NonNull String currency_type, double amount, Date date, String description, String location) {
        this.id = id;
        this.user_id = user_id;
        this.category_id = category_id;
        this.payment_method_id = payment_method_id;
        this.currency_type = currency_type;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.location = location;
    }

    public Expense(double amount, Date date, String description, String location) {
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.location = location;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getCurrency_type() {
        return currency_type;
    }

    public void setCurrency_type(String currency_type) {
        this.currency_type = currency_type;
    }
}
