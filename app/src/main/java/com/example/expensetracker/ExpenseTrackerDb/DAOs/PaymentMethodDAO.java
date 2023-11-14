package com.example.expensetracker.ExpenseTrackerDb.DAOs;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.expensetracker.ExpenseTrackerDb.Entities.PaymentMethod;

import java.util.List;


public interface PaymentMethodDAO {
    // Batch Operations
    @Insert
    void insertExpense(PaymentMethod... paymentMethods);
    @Update
    void updateExpense(PaymentMethod... paymentMethods);
    @Delete
    void deleteExpense(PaymentMethod... paymentMethods);

    @Query("SELECT * FROM payment_method_table")
    List<PaymentMethod> getAllPaymentMethods();
}
