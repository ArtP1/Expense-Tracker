package com.example.expensetracker;


import static com.example.expensetracker.Preferences.EXPENSE_TRACKER_PREFERENCES;
import static com.example.expensetracker.Preferences.IS_LOGGED_IN_KEY;
import static com.example.expensetracker.Preferences.TYPE_OF_USER_KEY;
import static com.example.expensetracker.Preferences.USER_FIRST_NAME_KEY;
import static com.example.expensetracker.Preferences.USER_ID_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.expensetracker.ExpenseTrackerDb.DAOs.UserDAO;
import com.example.expensetracker.ExpenseTrackerDb.Entities.User;
import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;
import com.example.expensetracker.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ActivityLoginBinding mLoginBinding;

    EditText mUsername;
    EditText mPassword;

    Button mLoginBtn;

    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(EXPENSE_TRACKER_PREFERENCES, MODE_PRIVATE);

        checkLoggedInState();

        editor = sharedPreferences.edit();

        userDAO = Room.databaseBuilder(this, ExpenseTrackerDatabase.class, ExpenseTrackerDatabase.DATABASE_NAME).allowMainThreadQueries().build().userDAO();

        initializeViews();

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();

                if(!username.isEmpty() || !password.isEmpty()) {
                    User foundUser = userDAO.getUserByUsername(username);

                    if(foundUser != null && foundUser.getPassword().equals(password)) {
                        editor.putBoolean(IS_LOGGED_IN_KEY, true);

                        if(foundUser.getRole().equals(User.UserRole.USER)) {
                            editor.putLong(USER_ID_KEY, foundUser.getId());
                            editor.putString(TYPE_OF_USER_KEY, User.UserRole.USER.toString());

                            Toast.makeText(LoginActivity.this, "Welcome back " + foundUser.getFirstName() + "!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, LandingPageActivity.class));
                        } else  {
                            editor.putString(USER_FIRST_NAME_KEY, foundUser.getFirstName());

                            if(foundUser.getRole().equals(User.UserRole.ADMIN)) {
                                editor.putString(TYPE_OF_USER_KEY, User.UserRole.ADMIN.toString());

                                startActivity(new Intent(LoginActivity.this, AdminsLandingPageActivity.class));
                            } else if(foundUser.getRole().equals(User.UserRole.SUPER_ADMIN)){
                                editor.putString("isUser", User.UserRole.SUPER_ADMIN.toString());

                                startActivity(new Intent(LoginActivity.this, SuperAdminsLandingPageActivity.class));
                            }
                        }

                        editor.apply();

                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                        mUsername.setText("");
                        mPassword.setText("");
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Enter both credentials!", Toast.LENGTH_SHORT).show();
                    mUsername.setText("");
                    mPassword.setText("");
                }
            }
        });

    }

    private void checkLoggedInState() {
        if (sharedPreferences.getBoolean(IS_LOGGED_IN_KEY, false)) {
            redirectToAppropriateActivity();
            finish();
        }
    }

    private void redirectToAppropriateActivity() {
        String userType = sharedPreferences.getString(TYPE_OF_USER_KEY, "");

        Class<?> targetActivity = null;
        if (User.UserRole.USER.toString().equals(userType)) {
            targetActivity = LandingPageActivity.class;
        } else if (User.UserRole.ADMIN.toString().equals(userType)) {
            targetActivity = AdminsLandingPageActivity.class;
        } else if (User.UserRole.SUPER_ADMIN.toString().equals(userType)) {
            targetActivity = SuperAdminsLandingPageActivity.class;
        }

        startActivity(new Intent(LoginActivity.this, targetActivity));
    }

    private void initializeViews() {
        mLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(mLoginBinding.getRoot());

        mUsername = mLoginBinding.usernameLogin;
        mPassword = mLoginBinding.passwordLogin;

        mLoginBtn = mLoginBinding.loginBtn;
    }

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }
}