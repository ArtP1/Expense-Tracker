package com.example.expensetracker.ExpenseTrackerDb;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ExpenseTrackerDatabase.initializeDatabase(this);
    }
}

