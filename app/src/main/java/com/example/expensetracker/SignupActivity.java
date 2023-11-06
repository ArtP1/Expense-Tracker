package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.example.expensetracker.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {
    ActivitySignupBinding mSignupBinding;
    EditText mUsername;
    EditText mPassword;
    EditText mFullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mSignupBinding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(mSignupBinding.getRoot());


    }
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, SignupActivity.class);
        return intent;
    }
}