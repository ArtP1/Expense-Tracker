package com.example.expensetracker;

import static com.example.expensetracker.Preferences.EXPENSE_TRACKER_PREFERENCES;
import static com.example.expensetracker.Preferences.IS_LOGGED_IN_KEY;
import static com.example.expensetracker.Preferences.TYPE_OF_USER_KEY;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensetracker.ExpenseTrackerDb.Entities.User;
import com.example.expensetracker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mMainActivityBinding;
    SharedPreferences sharedPreferences;


    Button signUpBtn;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(EXPENSE_TRACKER_PREFERENCES, MODE_PRIVATE);

        checkLoggedInState();

        initializeViews();

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SignupActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LoginActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    private void checkLoggedInState() {
        if (sharedPreferences.getBoolean(IS_LOGGED_IN_KEY, false)) {
            redirectToAppropriateActivity();
        }
    }

    private void redirectToAppropriateActivity() {
        String userType = sharedPreferences.getString(TYPE_OF_USER_KEY, "");

        Intent targetActivity = null;
        if (User.UserRole.USER.toString().equals(userType)) {
            targetActivity = new Intent(MainActivity.this,FragmentContainerActivity.class);
        } else if (User.UserRole.ADMIN.toString().equals(userType)) {
            targetActivity = new Intent(MainActivity.this,AdminsLandingPageActivity.class);
        } else if (User.UserRole.SUPER_ADMIN.toString().equals(userType)) {
            targetActivity = new Intent(MainActivity.this,SuperAdminsLandingPageActivity.class);
        }

        startActivity(targetActivity);

        targetActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(targetActivity);
        finish();
    }

    private void initializeViews() {
        mMainActivityBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mMainActivityBinding.getRoot());

        signUpBtn = mMainActivityBinding.signUpBtn;
        loginBtn = mMainActivityBinding.loginBtn;
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }
}