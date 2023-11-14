package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.expensetracker.ExpenseTrackerDb.DAOs.UserDAO;
import com.example.expensetracker.ExpenseTrackerDb.Entities.User;
import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;
import com.example.expensetracker.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences preferences;
    ActivityLoginBinding mLoginBinding;

    EditText mUsername;
    EditText mPassword;

    Button mLoginBtn;

    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("ExpenseTrackerPrefs", Context.MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

        Log.e("IsLoggedIn:", isLoggedIn + "");

        if(isLoggedIn) {
            Intent intent = new Intent(LoginActivity.this, LandingPageActivity.class);
            startActivity(intent);
            finish();
        }

        userDAO = Room.databaseBuilder(this, ExpenseTrackerDatabase.class, ExpenseTrackerDatabase.DATABASE_NAME).allowMainThreadQueries().build().userDAO();

        setContentView(R.layout.activity_login);

        mLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(mLoginBinding.getRoot());

        mUsername = mLoginBinding.usernameLogin;
        mPassword = mLoginBinding.passwordLogin;

        mLoginBtn = mLoginBinding.loginBtn;


        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();

                User foundUser = userDAO.getUserByUsername(username);

                if(foundUser != null && foundUser.getPassword().equals(password)) {
                    Toast.makeText(LoginActivity.this, "Welcome back " + foundUser.getFirstName() + "!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, LandingPageActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    mUsername.setText("");
                    mPassword.setText("");
                }
            }
        });

    }

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }
}