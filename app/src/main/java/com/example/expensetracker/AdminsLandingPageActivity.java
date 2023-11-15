package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.room.Room;

import android.content.Intent;
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
        userDAO = Room.databaseBuilder(this, ExpenseTrackerDatabase.class, ExpenseTrackerDatabase.DATABASE_NAME).allowMainThreadQueries().build().userDAO();
        currencyDAO = Room.databaseBuilder(this, ExpenseTrackerDatabase.class, ExpenseTrackerDatabase.DATABASE_NAME).allowMainThreadQueries().build().currencyDAO();
        categoryDAO = Room.databaseBuilder(this, ExpenseTrackerDatabase.class, ExpenseTrackerDatabase.DATABASE_NAME).allowMainThreadQueries().build().categoryDAO();
        paymentMethodDAO = Room.databaseBuilder(this, ExpenseTrackerDatabase.class, ExpenseTrackerDatabase.DATABASE_NAME).allowMainThreadQueries().build().paymentMethodDAO();

        mAdminLandingPageBinding = ActivityAdminsLandingPageBinding.inflate(getLayoutInflater());
        setContentView(mAdminLandingPageBinding.getRoot());

        mActiveUsersCard = mAdminLandingPageBinding.activeUsersCard;
        mUserQuantity = mAdminLandingPageBinding.userQuantity;
        mCurrenciesQuantity = mAdminLandingPageBinding.currenciesQuantity;
        mCategoryQuantity = mAdminLandingPageBinding.categoryQuantity;
        mPaymentMethodsQuantity = mAdminLandingPageBinding.paymentMethodsQuantity;
        mAdminName = mAdminLandingPageBinding.adminName;

        mUserQuantity.setText(userDAO.numberOfUsers() + "");
        mCurrenciesQuantity.setText(currencyDAO.numberOfCurrencies() + "");
        mCategoryQuantity.setText(categoryDAO.numberOfCategories() + "");
        mPaymentMethodsQuantity.setText(paymentMethodDAO.numberOfPaymentMethods() + "");
//        mAdminName.setText();

        mActiveUsersCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = EntityTableActivity.intentFactory(AdminsLandingPageActivity.this, "ActiveUsers");
                startActivity(intent);
            }
        });
    }
}