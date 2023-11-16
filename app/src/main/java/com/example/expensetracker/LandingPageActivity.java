package com.example.expensetracker;


import static com.example.expensetracker.Preferences.EXPENSE_TRACKER_PREFERENCES;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.expensetracker.Components.ExpenseAdapter;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.ExpenseDAO;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.UserDAO;
import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;
import com.example.expensetracker.databinding.ActivityLandingPageBinding;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LandingPageActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    ActivityLandingPageBinding mLandingPageBinding;
    TextView mMonthsExpenses;
    TextView mCurrentDate;
    RecyclerView mExpensesRecyclerView;
    ImageView mSettingsImg;

    ExpenseDAO expenseDAO;
    UserDAO userDAO;

    LocalDate currDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, d MMM", Locale.ENGLISH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences(EXPENSE_TRACKER_PREFERENCES, MODE_PRIVATE);

        initializeDatabase();

        initializeViews();

        displayData();

        mSettingsImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingPageActivity.this, UserSettingsActivity.class));
                finish();
            }
        });
    }

    private void initializeViews() {
        mLandingPageBinding = ActivityLandingPageBinding.inflate(getLayoutInflater());
        setContentView(mLandingPageBinding.getRoot());

        mMonthsExpenses = mLandingPageBinding.monthsExpenses;
        mCurrentDate = mLandingPageBinding.currentDate;
        mSettingsImg = mLandingPageBinding.settingsImg;

        mExpensesRecyclerView = mLandingPageBinding.expensesRecyclerView;
    }

    private void initializeDatabase() {
        ExpenseTrackerDatabase expenseTrackerDatabase = Room.databaseBuilder(
                this, ExpenseTrackerDatabase.class, ExpenseTrackerDatabase.DATABASE_NAME).allowMainThreadQueries().build();

        userDAO = expenseTrackerDatabase.userDAO();
        expenseDAO = expenseTrackerDatabase.expenseDAO();
    }

    private void displayData() {
        Double loggedInUserTotalExpenses = expenseDAO.getTotalExpensesByUser(sharedPreferences.getLong("userId", -1));

        mExpensesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mExpensesRecyclerView.setAdapter(new ExpenseAdapter(getApplicationContext(), expenseDAO.getExpensesByUser(1)));

        mMonthsExpenses.setText("$" + Double.toString(loggedInUserTotalExpenses));
        mCurrentDate.setText(currDate.format(formatter));
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, LandingPageActivity.class);
    }
}