package com.example.expensetracker.ExpenseTrackerDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.expensetracker.ExpenseTrackerDb.DAOs.ExpenseDAO;
import com.example.expensetracker.ExpenseTrackerDb.Entities.Category;
import com.example.expensetracker.ExpenseTrackerDb.Entities.Expense;
import com.example.expensetracker.ExpenseTrackerDb.Entities.User;

@Database(entities = {Expense.class, User.class, Category.class}, version = 1)
public abstract class ExpenseTrackerDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "expense_tracker_db";
    public static final String EXPENSE_TABLE = "expense_table";
    public static final String USER_TABLE = "user_table";
    public static final String CATEGORY_TABLE = "category_table";
    public static final String PAYMENT_METHOD_TABLE = "payment_method_table";

    private static volatile ExpenseTrackerDatabase dbInstance; // Singleton instance var
    private static final Object LOCK = new Object();

    public abstract ExpenseDAO expenseDAO();

    public static ExpenseTrackerDatabase getInstance(Context context) {
        if(dbInstance == null) {
            synchronized (LOCK) {
                if(dbInstance == null) {
                    dbInstance = Room.databaseBuilder(context.getApplicationContext(), ExpenseTrackerDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return dbInstance;
    }
}
