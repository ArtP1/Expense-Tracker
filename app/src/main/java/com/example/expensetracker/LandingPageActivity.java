package com.example.expensetracker;


import static com.example.expensetracker.Preferences.EXPENSE_TRACKER_PREFERENCES;
import static com.example.expensetracker.Preferences.USER_ID_KEY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.expensetracker.Components.ExpenseAdapter;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.ExpenseDAO;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.UserDAO;
import com.example.expensetracker.ExpenseTrackerDb.Entities.Expense;
import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;
import com.example.expensetracker.databinding.ActivityLandingPageBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class LandingPageActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    ActivityLandingPageBinding mLandingPageBinding;

    TextView mMonthsExpenses;
    TextView mCurrentDate;
    TextView mEmptyExpensesTextView;
    RecyclerView mExpensesRecyclerView;
    ImageView mSettingsImg;
    BottomNavigationView mBottomNavigation;

    MenuItem mExpensesActivity;
    MenuItem mWalletActivity;
    MenuItem mAccountActivity;

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
        setupMenu();

        displayData();

        mSettingsImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingPageActivity.this, UserSettingsActivity.class));
                finish();
            }
        });

        mBottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Class<?> targetActivity = null;

                if (item.equals(mExpensesActivity)) {
                    targetActivity = ExpensesActivity.class;
                } else if (item.equals(mWalletActivity)) {
                    targetActivity = WalletActivity.class;
                } else if (item.equals(mAccountActivity)) {
                    targetActivity = AccountActivity.class;
                }

                startActivity(new Intent(LandingPageActivity.this, targetActivity));

                return false;
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

        mBottomNavigation = mLandingPageBinding.userNavbar;
        mEmptyExpensesTextView = mLandingPageBinding.emptyExpensesTextView;
    }

    private void initializeDatabase() {
        ExpenseTrackerDatabase expenseTrackerDatabase = Room.databaseBuilder(
                this, ExpenseTrackerDatabase.class, ExpenseTrackerDatabase.DATABASE_NAME).allowMainThreadQueries().build();

        userDAO = expenseTrackerDatabase.userDAO();
        expenseDAO = expenseTrackerDatabase.expenseDAO();
    }

    private void displayData() {
        Long currUser = sharedPreferences.getLong(USER_ID_KEY, -1);
        Double loggedInUserTotalExpenses = expenseDAO.getTotalExpensesByUser(currUser);

        List<Expense> expenseList = expenseDAO.getExpensesByUser(currUser);

        if(!expenseList.isEmpty()) {
            mEmptyExpensesTextView.setVisibility(View.GONE);

            mExpensesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            mExpensesRecyclerView.setAdapter(new ExpenseAdapter(getApplicationContext(), expenseList));
            mMonthsExpenses.setText("$" + Double.toString(loggedInUserTotalExpenses));
        } else {
            mMonthsExpenses.setText("$0");
        }

        mCurrentDate.setText(currDate.format(formatter));
    }

    private void setupMenu() {
        // Initialize menu items after setContentView() is called
        Menu menu = mBottomNavigation.getMenu();
        mExpensesActivity = menu.findItem(R.id.expensesActivity);
        mWalletActivity = menu.findItem(R.id.walletActivity);
        mAccountActivity = menu.findItem(R.id.accountActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.users_bottom_nav, menu); // Replace with your actual menu resource file
        return true;
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, LandingPageActivity.class);
    }
}