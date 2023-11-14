package com.example.expensetracker.ExpenseTrackerDb.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.expensetracker.ExpenseTrackerDb.Entities.Expense;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Dao
public interface ExpenseDAO {
    // Batch Operations
    @Insert
    void insertExpense(Expense... expenses);
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

    @Query("SELECT * FROM expense_table WHERE date BETWEEN :startDate AND :endDate")
    List<Expense> getExpensesByDateRange(Date startDate, Date endDate);

    // Aggregate methods
    @Query("SELECT * FROM expense_table WHERE user_id==:user_id")
    BigDecimal getTotalExpensesByUser(int user_id);

    @Query("SELECT * FROM expense_table WHERE category_id==:category_id")
    BigDecimal getUserTotalExpensesByCategory(int category_id);

    // Other methods
    @Query("SELECT * FROM expense_table WHERE user_id==:userId ORDER BY date DESC LIMIT :limit")
    List<Expense> getRecentExpenses(int userId, int limit);

}
