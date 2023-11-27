package com.example.expensetracker.ExpenseTrackerDb.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.expensetracker.ExpenseTrackerDb.Entities.PaymentMethod;

import java.util.List;

@Dao
public interface PaymentMethodDAO {
    @Insert
    void insertPaymentMethod(PaymentMethod... paymentMethods);

    @Insert
    void insertAllPaymentMethods(PaymentMethod[] paymentMethods);

    @Update
    void updatePaymentMethod(PaymentMethod... paymentMethods);

    @Delete
    void deletePaymentMethod(PaymentMethod... paymentMethods);

    @Query("SELECT COUNT(*) AS NumberOfPaymentMethods FROM payment_method_table")
    Integer numberOfPaymentMethods();

    @Query("SELECT * FROM payment_method_table")
    List<PaymentMethod> getAllPaymentMethods();
}
