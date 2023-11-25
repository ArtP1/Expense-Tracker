package com.example.expensetracker.ExpenseTrackerDb.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;

@Entity(tableName = ExpenseTrackerDatabase.PAYMENT_METHOD_TABLE)
public class PaymentMethod {
    // PRIMARY KEY
    @NonNull
    @PrimaryKey
    private String method;

    // COLUMNS
    private String icon;


    @Ignore
    public PaymentMethod(String method) {
        this.method = method;
    }

    public PaymentMethod(String method, String icon) {
        this.method = method;
        this.icon = icon;
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

    @Override
    public String toString() {
        return method;
    }
}
