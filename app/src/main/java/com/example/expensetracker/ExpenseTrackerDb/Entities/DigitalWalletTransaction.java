package com.example.expensetracker.ExpenseTrackerDb.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;

import java.time.LocalDate;

@Entity(tableName = ExpenseTrackerDatabase.DIGITAL_TRANSACTION_TABLE,
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
                ),
                @ForeignKey(
                        entity = UserDigitalWallet.class,
                        parentColumns = "id",
                        childColumns = "walletId",
                        onDelete = ForeignKey.SET_NULL,
                        onUpdate = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index("user_id"),
                @Index("category_name"),
                @Index("payment_method"),
                @Index("walletId")
        }
)
public class DigitalWalletTransaction extends Transaction {
    // Additional fields if needed for digital wallet transactions
    private long walletId;

    @Ignore
    public DigitalWalletTransaction(long user_id, String category_name, String payment_method, double amount, @NonNull String title, String description, String location, @NonNull Type transType, long walletId) {
        super(user_id, category_name, payment_method, amount, title, description, location, transType);
        this.walletId = walletId;
    }

    public DigitalWalletTransaction(long user_id, String category_name, String payment_method, double amount, @NonNull String title, @NonNull LocalDate dateSubmitted, String description, String location, @NonNull Type transType, long walletId) {
        super(user_id, category_name, payment_method, amount, title, dateSubmitted, description, location, transType);
        this.walletId = walletId;
    }

    public long getWalletId() {
        return walletId;
    }

    public void setWalletId(long walletId) {
        this.walletId = walletId;
    }
}