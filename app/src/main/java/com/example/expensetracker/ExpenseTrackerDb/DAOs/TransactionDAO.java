package com.example.expensetracker.ExpenseTrackerDb.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;


import com.example.expensetracker.ExpenseTrackerDb.DateConverter;
import com.example.expensetracker.ExpenseTrackerDb.Entities.Transaction;

import java.time.LocalDate;
import java.util.List;

@Dao
@TypeConverters(DateConverter.class)
public interface TransactionDAO {
    // Batch Operations
    @Insert
    void insertExpense(Transaction... expens);
    @Insert
    void insertAll(Transaction[] expens);

    @Update
    void updateExpense(Transaction... expens);
    @Delete
    void deleteExpense(Transaction... expens);

    // Retrieve methods
    @Query("SELECT * FROM transaction_table WHERE id==:expenseId")
    Transaction getExpenseById(int expenseId);

    @Query("SELECT * FROM transaction_table WHERE user_id==:userId")
    List<Transaction> getExpensesByUser(long userId);

    @Query("SELECT * FROM transaction_table WHERE category_id==:category_id")
    List<Transaction> getExpensesByCategory(int category_id);

    @Query("SELECT * FROM transaction_table WHERE dateSubmitted BETWEEN :startDate AND :endDate")
    List<Transaction> getExpensesByDateRange(LocalDate startDate, LocalDate endDate);

    // Aggregate methods
    @Query("SELECT SUM(e.amount) AS TotalExpenses FROM transaction_table e WHERE user_id==:user_id")
    Double getTotalExpensesByUser(long user_id);

//    @Query("SELECT e.category_id, SUM(e.amount) AS TotalExpenses FROM expense_table e WHERE user_id==:user_id AND category_id==:category_id")
//    List<Object[]> getUserTotalExpensesByCategory(int user_id, int category_id);

    // Other methods
    @Query("SELECT * FROM transaction_table WHERE user_id==:userId ORDER BY dateSubmitted DESC LIMIT :limit")
    List<Transaction> getRecentExpenses(long userId, int limit);

}
