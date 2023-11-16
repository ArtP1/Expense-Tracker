package com.example.expensetracker;

import static com.example.expensetracker.Preferences.EXPENSE_TRACKER_PREFERENCES;
import static com.example.expensetracker.Preferences.USER_FIRST_NAME_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.expensetracker.ExpenseTrackerDb.DAOs.CategoryDAO;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.CurrencyDAO;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.PaymentMethodDAO;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.UserDAO;
import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;
import com.example.expensetracker.databinding.ActivityAdminsLandingPageBinding;

public class AdminsLandingPageActivity extends AppCompatActivity {
    ActivityAdminsLandingPageBinding  mAdminLandingPageBinding;

    SharedPreferences sharedPreferences;

    CardView mActiveUsersCard;
    TextView mUserQuantity;
    TextView mCurrenciesQuantity;
    TextView mCategoryQuantity;
    TextView mPaymentMethodsQuantity;
    TextView mAdminName;

    UserDAO userDAO;
    CurrencyDAO currencyDAO;
    CategoryDAO categoryDAO;
    PaymentMethodDAO paymentMethodDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(EXPENSE_TRACKER_PREFERENCES, MODE_PRIVATE);

        initializeDatabase();
        initializeViews();
        displayData();

        mActiveUsersCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = EntityTableActivity.intentFactory(AdminsLandingPageActivity.this, "ActiveUsers");
                startActivity(intent);
            }
        });
    }

    public void initializeDatabase() {
        ExpenseTrackerDatabase expenseTrackerDatabase = Room.databaseBuilder(
                this, ExpenseTrackerDatabase.class, ExpenseTrackerDatabase.DATABASE_NAME).allowMainThreadQueries().build();

        userDAO = expenseTrackerDatabase.userDAO();
        currencyDAO = expenseTrackerDatabase.currencyDAO();
        categoryDAO = expenseTrackerDatabase.categoryDAO();
        paymentMethodDAO = expenseTrackerDatabase.paymentMethodDAO();
    }

    public void initializeViews() {
        mAdminLandingPageBinding = ActivityAdminsLandingPageBinding.inflate(getLayoutInflater());
        setContentView(mAdminLandingPageBinding.getRoot());
        mActiveUsersCard = mAdminLandingPageBinding.activeUsersCard;
        mUserQuantity = mAdminLandingPageBinding.userQuantity;
        mCurrenciesQuantity = mAdminLandingPageBinding.currenciesQuantity;
        mCategoryQuantity = mAdminLandingPageBinding.categoryQuantity;
        mPaymentMethodsQuantity = mAdminLandingPageBinding.paymentMethodsQuantity;
        mAdminName = mAdminLandingPageBinding.adminName;
    }

    private void displayData() {
        mUserQuantity.setText(String.valueOf(userDAO.numberOfUsers()));
        mCurrenciesQuantity.setText(String.valueOf(currencyDAO.numberOfCurrencies()));
        mCategoryQuantity.setText(String.valueOf(categoryDAO.numberOfCategories()));
        mPaymentMethodsQuantity.setText(String.valueOf(paymentMethodDAO.numberOfPaymentMethods()));

        mAdminName.setText(sharedPreferences.getString(USER_FIRST_NAME_KEY, "Anonymous"));
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, AdminsLandingPageActivity.class);
    }
}