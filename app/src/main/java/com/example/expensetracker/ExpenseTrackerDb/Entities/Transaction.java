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


@Entity(tableName = ExpenseTrackerDatabase.TRANSACTION_TABLE,
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
                        parentColumns = "name",
                        childColumns = "category_name",
                        onUpdate = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = PaymentMethod.class,
                        parentColumns = "method",
                        childColumns = "payment_method",
                        onUpdate = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index("user_id"),
                @Index("category_name"),
                @Index("payment_method")
        }
)
public class Transaction {
    // PRIMARY KEY
    @PrimaryKey(autoGenerate = true)
    private long id;

    // FOREIGN KEY(s)
    private long user_id;

    private String category_name;

    private String payment_method;

    // COLUMNS
    @NonNull
    private double amount;

    @NonNull
    private String title;

    @NonNull
    @TypeConverters(DateConverter.class)
    private LocalDate dateSubmitted;

    private String description;
    private String location;

    public enum Type {
        EARNING,
        EXPENSE
    }

    @NonNull
    @ColumnInfo(collate = ColumnInfo.NOCASE) // Case-Insensitive Collation
    private Type transType;

    /**
     *  Constructs a Transaction object with specified parameters, setting the submission date to the current system date
     *
     * @param user_id
     * @param category_name
     * @param payment_method
     * @param amount
     * @param title
     * @param description
     * @param location
     * @param transType
     */
    @Ignore
    public Transaction(long user_id, String category_name, String payment_method, @NonNull double amount, @NonNull String title, String description, String location, @NonNull Type transType) {
        LocalDate currDate = LocalDate.now();
        this.user_id = user_id;
        this.category_name = category_name;
        this.payment_method = payment_method;
        this.amount = amount;
        this.title = title;
        this.dateSubmitted = currDate;
        this.description = description;
        this.location = location;
        this.transType = transType;
    }

    /**
     *  Constructs a Transaction object with specified parameters, setting the submission date to a specified date (future, past, present).
     *
     * @param user_id
     * @param category_name
     * @param payment_method
     * @param amount
     * @param title
     * @param description
     * @param location
     * @param transType
     */
    public Transaction(long user_id, String category_name, String payment_method, double amount, @NonNull String title, @NonNull LocalDate dateSubmitted, String description, String location, @NonNull Type transType) {
        this.user_id = user_id;
        this.category_name = category_name;
        this.payment_method = payment_method;
        this.amount = amount;
        this.title = title;
        this.dateSubmitted = dateSubmitted;
        this.description = description;
        this.location = location;
        this.transType = transType;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public Type getTransType() {
        return transType;
    }

    public void setTransType(@NonNull Type transType) {
        this.transType = transType;
    }
}
