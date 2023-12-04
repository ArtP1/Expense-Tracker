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
    @Insert
    void insertTransaction(Transaction... transactions);

    @Insert
    void insertAllUserTransactions(Transaction[] transactions);

    @Insert
    void insertTransactions(Transaction[] digitalWalletTransactions);

    @Update
    void updateTransaction(Transaction... digitalWalletTransactions);

    @Delete
    void deleteTransaction(Transaction... digitalWalletTransactions);

    @Query("SELECT * FROM transaction_table WHERE user_id = :user_id AND STRFTIME('%Y-%m', dateSubmitted / 1000, 'unixepoch') = STRFTIME('%Y-%m', 'now') AND wallet_id IS NULL ORDER BY dateSubmitted DESC")
    LiveData<List<Transaction>> getMonthMostRecentTransactionsByUserID(long user_id);

    @Query("SELECT * FROM transaction_table WHERE user_id = :user_id AND transType = 'EXPENSE' AND STRFTIME('%Y-%m', dateSubmitted / 1000, 'unixepoch') = STRFTIME('%Y-%m', 'now') AND wallet_id IS NULL ORDER BY dateSubmitted DESC")
    LiveData<List<Transaction>> getMonthMostRecentExpensesByUserID(long user_id);
    @Query("SELECT * FROM transaction_table WHERE user_id = :user_id AND STRFTIME('%Y-%m', dateSubmitted / 1000, 'unixepoch') = STRFTIME('%Y-%m', 'now') AND wallet_id IS NOT NULL ORDER BY dateSubmitted DESC")
    LiveData<List<Transaction>> getMonthMostRecentWalletTransactionsByUserID(long user_id);

    /**
     * @param userId
     * @return LiveData holding a list of Model CategoryWithAmount representing the categories and their respective total expenses for the current month.
     */
    @Query("SELECT c.name AS category_name, SUM(t.amount) AS total_amount, c.icon AS icon FROM " +
            "category_table c LEFT JOIN transaction_table t ON c.name = t.category_name " +
            "WHERE t.user_id = :userId AND t.transType = 'EXPENSE' AND STRFTIME('%Y-%m', dateSubmitted / 1000, 'unixepoch') = STRFTIME('%Y-%m', 'now') " +
            "GROUP BY category_name ORDER BY total_amount DESC LIMIT 10")
    LiveData<List<TransactionCategoryWithAmount>> getExpenseCategoriesWithAmount(long userId);

    @Query("SELECT c.name AS category_name, SUM(t.amount) AS total_amount, c.icon AS icon FROM " +
            "category_table c LEFT JOIN transaction_table t ON c.name = t.category_name " +
            "WHERE t.user_id = :userId AND t.transType = 'EXPENSE' AND STRFTIME('%Y-%m', dateSubmitted / 1000, 'unixepoch') = STRFTIME('%Y-%m', 'now') " +
            "GROUP BY category_name ORDER BY total_amount DESC LIMIT 3")
    LiveData<List<TransactionCategoryWithAmount>> getTopThreeExpenseCategoriesWithAmount(long userId);

    /**
     * @param user_id
     * @return The total month's earnings amount for the specified user
     */
    @Query("SELECT SUM(amount) AS TotalEarningsAmount FROM transaction_table WHERE user_id = :user_id AND STRFTIME('%Y-%m', dateSubmitted / 1000, 'unixepoch') = STRFTIME('%Y-%m', 'now') AND transType = 'EARNING'")
    double getTotalMonthlyEarningsByUserID(long user_id);

    /**
     * @param user_id
     * @return The total month's expenses amount for the specified user
     */
    @Query("SELECT SUM(amount) AS TotalExpensesAmount FROM transaction_table WHERE user_id = :user_id AND STRFTIME('%Y-%m', dateSubmitted / 1000, 'unixepoch') = STRFTIME('%Y-%m', 'now') AND transType = 'EXPENSE'")
    double getTotalMonthlyExpensesByUserID(long user_id);


}
