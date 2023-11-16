package com.example.expensetracker;

import static com.example.expensetracker.Preferences.EXPENSE_TRACKER_PREFERENCES;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.expensetracker.databinding.ActivityUserSettingsBinding;

public class UserSettingsActivity extends AppCompatActivity {
    ActivityUserSettingsBinding mUserSettingsBinding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    Button mLogOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences(EXPENSE_TRACKER_PREFERENCES, MODE_PRIVATE);

        initializeViews();

        mLogOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                Toast.makeText(UserSettingsActivity.this, "Successfully logged out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UserSettingsActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void initializeViews() {
        mUserSettingsBinding = ActivityUserSettingsBinding.inflate(getLayoutInflater());
        setContentView(mUserSettingsBinding.getRoot());

        mLogOutBtn = mUserSettingsBinding.logOutBtn;
    }
}