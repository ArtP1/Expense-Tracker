package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.expensetracker.databinding.ActivityEntityTableBinding;

public class EntityTableActivity extends AppCompatActivity {
    private static final String DASHBOARD_TABLE_SELECTED = "com.example.expensetracker_DASHBOARD_TABLE_SELECTED";
    String tableType;

    ActivityEntityTableBinding mEntityTableBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mEntityTableBinding = ActivityEntityTableBinding.inflate(getLayoutInflater());
        setContentView(mEntityTableBinding.getRoot());



    }

    public static Intent intentFactory(Context packageContext, String tableType) {
        Intent intent = new Intent(packageContext, AdminsLandingPageActivity.class);
        intent.putExtra(DASHBOARD_TABLE_SELECTED, tableType);

        return intent;
    }
}