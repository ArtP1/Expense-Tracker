package com.example.expensetracker;

import static com.example.expensetracker.Preferences.EXPENSE_TRACKER_PREFERENCES;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.expensetracker.ExpenseTrackerDb.DAOs.CurrencyDAO;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.UserDAO;
import com.example.expensetracker.ExpenseTrackerDb.Entities.Currency;
import com.example.expensetracker.ExpenseTrackerDb.Entities.User;
import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;
import com.example.expensetracker.databinding.ActivityUserSettingsBinding;

import java.util.List;

public class UserSettingsActivity extends AppCompatActivity {
    ActivityUserSettingsBinding mUserSettingsBinding;
    private SharedPreferences sharedPreferences;
    Button mSaveBtn;
    Button mLogoutBtn;
    TextView mEditTextOldUsername;
    TextView mEditTextOldPassword;
    TextView mEditTextFirstName;
    Spinner mSpinnerCurrency;
    TextView mEditTextBudget;
    CheckBox mCheckBoxNotifications;

    CurrencyDAO currencyDAO;
    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeDatabase();

        initializeViews();

        sharedPreferences = getSharedPreferences(EXPENSE_TRACKER_PREFERENCES, MODE_PRIVATE);
        long currUserID = sharedPreferences.getLong(Preferences.USER_ID_KEY, -1);
        User currUser = userDAO.getUserById(currUserID);

        displayData(currUser);
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUsername = mEditTextOldUsername.getText().toString();
                String newPassword = mEditTextOldPassword.getText().toString();
                String newFirstName = mEditTextFirstName.getText().toString();

                Currency selectedCurrency = (Currency) mSpinnerCurrency.getSelectedItem();
                String newCurrencyISO = selectedCurrency.getISO();

                Double newBudget;
                if (!mEditTextBudget.getText().toString().isEmpty()) {
                    newBudget = Double.parseDouble(mEditTextBudget.getText().toString());
                } else {
                    newBudget = 0.0;
                }

                boolean enabledNotifications = mCheckBoxNotifications.isChecked();


                boolean usernameChanged = !newUsername.equals(currUser.getUsername()) && !newUsername.isEmpty();
                boolean passwordChanged = !newPassword.equals(currUser.getPassword()) && !newPassword.isEmpty();
                boolean firstNameChanged = !newFirstName.equals(currUser.getFirstName()) && !newFirstName.isEmpty();
                boolean budgetChanged = !newBudget.equals(currUser.getBudget()) && !newBudget.equals(0.0);
                boolean notificationsChanged = enabledNotifications != currUser.hasNotifications();
                boolean currencyChanged = !newCurrencyISO.equals(currUser.getCurrency()) && !newCurrencyISO.equals("N/A");

                if (usernameChanged || passwordChanged || firstNameChanged || budgetChanged || notificationsChanged || currencyChanged) {

                    if (usernameChanged) {
                        currUser.setUsername(newUsername);
                    }
                    if (passwordChanged) {
                        currUser.setPassword(newPassword);
                    }
                    if (firstNameChanged) {
                        currUser.setFirstName(newFirstName);
                    }

                    if (budgetChanged) {
                        currUser.setBudget(newBudget);
                    }

                    if (notificationsChanged) {
                        currUser.setHasNotifications(enabledNotifications);
                    }

                    if (currencyChanged) {
                        currUser.setCurrency(newCurrencyISO);
                    }

                    userDAO.updateUser(currUser);

                    Toast.makeText(UserSettingsActivity.this, "Settings updated successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UserSettingsActivity.this, FragmentContainerActivity.class));
                    finish();
                } else {
                    Toast.makeText(UserSettingsActivity.this, "No changes detected!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().clear().apply();

                Intent intent = new Intent(UserSettingsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initializeDatabase() {
        ExpenseTrackerDatabase expenseTrackerDatabase = Room.databaseBuilder(getApplicationContext(), ExpenseTrackerDatabase.class, ExpenseTrackerDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build();

        userDAO = expenseTrackerDatabase.userDAO();
        currencyDAO = expenseTrackerDatabase.currencyDAO();
    }

    private void initializeViews() {
        mUserSettingsBinding = ActivityUserSettingsBinding.inflate(getLayoutInflater());
        setContentView(mUserSettingsBinding.getRoot());

        mSaveBtn = mUserSettingsBinding.saveSettingsBtn;
        mLogoutBtn = mUserSettingsBinding.logoutBtn;
        mEditTextOldUsername = mUserSettingsBinding.editTextOldUsername;
        mEditTextOldPassword = mUserSettingsBinding.editTextOldPassword;
        mEditTextFirstName = mUserSettingsBinding.editTextFirstName;
        mSpinnerCurrency = mUserSettingsBinding.spinnerCurrency;
        mEditTextBudget = mUserSettingsBinding.editTextBudget;
        mCheckBoxNotifications = mUserSettingsBinding.checkBoxNotifications;
    }

    private void displayData(User user) {
        mEditTextOldUsername.setText(user.getUsername());
        mEditTextOldPassword.setText(user.getPassword());
        mEditTextFirstName.setText(user.getFirstName());

        List<Currency> currencyList = currencyDAO.getAllCurrencies();

        Currency defaultCurrency = new Currency("N/A", "", ""); // Assuming Currency class has a constructor
        currencyList.add(0, defaultCurrency);

        ArrayAdapter<Currency> currencyArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currencyList);

        currencyArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinnerCurrency.setAdapter(currencyArrayAdapter);

        int predefinedCurrencyPosition = getCurrencyPosition(currencyList, user.getCurrency());

        if (predefinedCurrencyPosition != -1) {
            mSpinnerCurrency.setSelection(predefinedCurrencyPosition);
        } else {
            mSpinnerCurrency.setSelection(0);
        }

        mEditTextBudget.setText(String.valueOf(user.getBudget()));
        mCheckBoxNotifications.setChecked(user.hasNotifications());
    }

    private int getCurrencyPosition(List<Currency> currencies, String userCurrencyISO) {
        for (int i = 0; i < currencies.size(); i++) {
            if (currencies.get(i).getISO().equals(userCurrencyISO)) {
                return i;
            }
        }
        return -1;
    }
}