package com.example.expensetracker.ExpenseTrackerDb.Entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;

@Entity(tableName = ExpenseTrackerDatabase.PAYMENT_METHOD_TABLE)
public class PaymentMethod {
    // PRIMARY KEY
    @PrimaryKey(autoGenerate = true)
    private int id;

    // COLUMNS
    private String method;
    private String icon;

    @Ignore
    public PaymentMethod(int id, String method, String icon) {
        this.id = id;
        this.method = method;
        this.icon = icon;
    }

    public PaymentMethod(String method, String icon) {
        this.method = method;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
