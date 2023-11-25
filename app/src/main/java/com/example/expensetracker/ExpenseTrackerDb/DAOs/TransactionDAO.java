package com.example.expensetracker.ExpenseTrackerDb.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.example.expensetracker.ExpenseTrackerDb.DateConverter;
import com.example.expensetracker.ExpenseTrackerDb.Entities.Transaction;
import com.example.expensetracker.ExpenseTrackerDb.Models.TransactionCategoryWithAmount;

import java.util.List;

@Dao
@TypeConverters(DateConverter.class)
public interface TransactionDAO {
    // Batch Operations
    @Insert
    void insertTransaction(Transaction... transactions);

    @Insert
    void insertAll(Transaction[] transactions);

    @Update
    void updateTransaction(Transaction... transactions);

    @Delete
    void deleteTransaction(Transaction... transactions);

    @Query("SELECT * FROM transaction_table WHERE id = :transactionId")
    Transaction getTransactionById(int transactionId);

    @Query("SELECT * FROM transaction_table WHERE user_id = :userId")
    List<Transaction> getTransactionsByUser(long userId);

    @Query("SELECT * FROM transaction_table WHERE category_name = :category_name")
    List<Transaction> getTransactionsByCategory(String category_name);

//    @Query("SELECT * FROM transaction_table WHERE dateSubmitted BETWEEN :startDate AND :endDate")
//    List<Transaction> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate);

    @Query("SELECT SUM(amount) AS TotalExpenses FROM transaction_table WHERE user_id = :user_id AND transType = 'EXPENSE' AND STRFTIME('%Y-%m', dateSubmitted / 1000, 'unixepoch') = STRFTIME('%Y-%m', 'now')")
    double getTotalMonthlyExpensesByUserID(long user_id);

    @Query("SELECT SUM(amount) AS TotalEarnings FROM transaction_table WHERE user_id = :user_id AND transType = 'EARNING' AND STRFTIME('%Y-%m', dateSubmitted / 1000, 'unixepoch') = STRFTIME('%Y-%m', 'now')")
    double getTotalMonthlyEarningsByUserID(long user_id);

    @Query("SELECT * FROM transaction_table WHERE user_id = :user_id AND transType = 'EXPENSE' AND STRFTIME('%Y-%m', dateSubmitted / 1000, 'unixepoch') = STRFTIME('%Y-%m', 'now') ORDER BY dateSubmitted DESC LIMIT 10")
    LiveData<List<Transaction>> getMonthMostRecentExpensesByUserID(long user_id);

    /**
     * Retrieves the total amount spent per expense category for a given user.
     *
     * @param userId
     * @return LiveData holding a list of Model CategoryWithAmount representing
     *         the categories and their respective total expenses for the current month.
     *
     * Query:
     * This query joins the 'category_table' and 'transaction_table' to calculate the total amount spent per
     * expense category for a specific user. It retrieves the category name and the total expense
     * amount. The query filters transactions for the given 'userId' and 'EXPENSE' transactions occurring in the current month.
     * The results are grouped by category name and ordered by category name, showcasing the total expenses for each category.
     */
    @Query("SELECT c.name AS category_name, SUM(t.amount) AS total_amount, c.icon AS icon " +
            "FROM category_table c " +
            "LEFT JOIN transaction_table t ON c.name = t.category_name " +
            "WHERE t.user_id = :userId " +
            "AND t.transType = 'EXPENSE' " +
            "AND STRFTIME('%Y-%m', dateSubmitted / 1000, 'unixepoch') = STRFTIME('%Y-%m', 'now')" +
            "GROUP BY category_name ORDER BY total_amount DESC")
    LiveData<List<TransactionCategoryWithAmount>> getExpenseCategoriesWithAmount(long userId);
}
