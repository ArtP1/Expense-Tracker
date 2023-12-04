package com.example.expensetracker.ExpenseTrackerDb.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;

@Entity(tableName = ExpenseTrackerDatabase.DIGITAL_WALLET_TABLE)

public class DigitalWallet {
    // PRIMARY KEY(S)
    @NonNull
    @PrimaryKey
    private String name;

    // COLUMN(S)
    @NonNull
    private String img;

    public enum Type {
        DIGITAL,
        CRYPTO,
        BANK,
        OTHER
    }

    @NonNull
    @ColumnInfo(collate = ColumnInfo.NOCASE)
    private Type type;


    public DigitalWallet(@NonNull String name, @NonNull String img, @NonNull Type type) {
        this.name = name;
        this.img = img;
        this.type = type;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getImg() {
        return img;
    }

    public void setImg(@NonNull String img) {
        this.img = img;
    }

    @NonNull
    public Type getType() {
        return type;
    }

    public void setType(@NonNull Type type) {
        this.type = type;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
