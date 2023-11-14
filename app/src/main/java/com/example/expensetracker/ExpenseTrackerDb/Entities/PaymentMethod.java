package com.example.expensetracker.ExpenseTrackerDb.Entities;

import androidx.room.Entity;
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

    public PaymentMethod(int id, String method, String icon) {
        this.id = id;
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

//    create table PaymentMethods ( -- Inserts, Updates
//        id int auto_increment,
//        method varchar(50) not null,
//        icon varchar(255) not null,
//        primary key(id),
//        constraint PAY_METHOD_CHECK check(method regexp '^[a-zA-Z ]+$')
//        );