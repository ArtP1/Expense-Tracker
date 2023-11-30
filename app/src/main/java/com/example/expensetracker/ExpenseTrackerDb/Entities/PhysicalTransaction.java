package com.example.expensetracker.ExpenseTrackerDb.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;

import java.time.LocalDate;

@Entity(tableName = ExpenseTrackerDatabase.PHYSICAL_TRANSACTION_TABLE,
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
        },
        indices = {
                @Index("user_id"),
                @Index("category_name"),
                @Index("payment_method")
        }
)
public class PhysicalTransaction extends Transaction {
    @Ignore
    public PhysicalTransaction(long user_id, String category_name, String payment_method, double amount, @NonNull String title, String description, String location, @NonNull Type transType) {
        super(user_id, category_name, payment_method, amount, title, description, location, transType);
    }

    public PhysicalTransaction(long user_id, String category_name, String payment_method, double amount, @NonNull String title, @NonNull LocalDate dateSubmitted, String description, String location, @NonNull Type transType) {
        super(user_id, category_name, payment_method, amount, title, dateSubmitted, description, location, transType);
    }
}
