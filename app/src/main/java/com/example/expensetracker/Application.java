package com.example.expensetracker;

import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ExpenseTrackerDatabase.initializeDatabase(this);
    }
}

