package com.example.expensetracker.ExpenseTrackerDb.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;


import com.example.expensetracker.ExpenseTrackerDb.DateConverter;
import com.example.expensetracker.ExpenseTrackerDb.Entities.Expense;

import java.time.LocalDate;
import java.util.List;

@Dao
@TypeConverters(DateConverter.class)
public interface ExpenseDAO {
    // Batch Operations
    @Insert
    void insertExpense(Expense... expenses);
    @Insert
    void insertAll(Expense[] expenses);

    @Update
    void updateExpense(Expense... expenses);
    @Delete
    void deleteExpense(Expense... expenses);

    // Retrieve methods
    @Query("SELECT * FROM expense_table WHERE id==:expenseId")
    Expense getExpenseById(int expenseId);

    @Query("SELECT * FROM expense_table WHERE user_id==:userId")
    List<Expense> getExpensesByUser(int userId);

    @Query("SELECT * FROM expense_table WHERE category_id==:category_id")
    List<Expense> getExpensesByCategory(int category_id);

    @Query("SELECT * FROM expense_table WHERE dateSubmitted BETWEEN :startDate AND :endDate")
    List<Expense> getExpensesByDateRange(LocalDate startDate, LocalDate endDate);

    // Aggregate methods
    @Query("SELECT SUM(e.amount) AS TotalExpenses FROM expense_table e WHERE user_id==:user_id")
    Double getTotalExpensesByUser(long user_id);

//    @Query("SELECT e.category_id, SUM(e.amount) AS TotalExpenses FROM expense_table e WHERE user_id==:user_id AND category_id==:category_id")
//    List<Object[]> getUserTotalExpensesByCategory(int user_id, int category_id);

    // Other methods
    @Query("SELECT * FROM expense_table WHERE user_id==:userId ORDER BY dateSubmitted DESC LIMIT :limit")
    List<Expense> getRecentExpenses(int userId, int limit);

}
