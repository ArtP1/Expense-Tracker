package com.example.expensetracker;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.expensetracker.Components.ExpenseAdapter;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.ExpenseDAO;
import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;
import com.example.expensetracker.databinding.ActivityLandingPageBinding;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LandingPageActivity extends AppCompatActivity {
    ActivityLandingPageBinding mLandingPageBinding;
    TextView mMonthsExpenses;
    TextView mCurrentDate;
    RecyclerView mExpensesRecyclerView;

    ExpenseDAO expenseDAO;

    LocalDate currDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, d MMM", Locale.ENGLISH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        expenseDAO = Room.databaseBuilder(this, ExpenseTrackerDatabase.class, ExpenseTrackerDatabase.DATABASE_NAME).allowMainThreadQueries().build().expenseDAO();

        mLandingPageBinding = ActivityLandingPageBinding.inflate(getLayoutInflater());
        setContentView(mLandingPageBinding.getRoot());

        mMonthsExpenses = mLandingPageBinding.monthsExpenses;
        mCurrentDate = mLandingPageBinding.currentDate;


        mExpensesRecyclerView = mLandingPageBinding.expensesRecyclerView;
        mExpensesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mExpensesRecyclerView.setAdapter(new ExpenseAdapter(getApplicationContext(), expenseDAO.getExpensesByUser(1)));

//        mMonthsExpenses.setText();
        mCurrentDate.setText(currDate.format(formatter));
    }


    public static Intent getIntent(Context context) {
        return new Intent(context, LandingPageActivity.class);
    }
}