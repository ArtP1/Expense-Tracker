package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.expensetracker.ExpenseTrackerDb.DAOs.UserDAO;
import com.example.expensetracker.ExpenseTrackerDb.Entities.User;
import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;
import com.example.expensetracker.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {
    ActivitySignupBinding mSignupBinding;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    UserDAO userDAO;

    public static final String SHARED_PREFS = "ExpenseTrackerPrefs";


    EditText mUsername;
    EditText mFirstname;
    EditText mPassword;

    Button mSignupBtn;

    ImageView mSignupImg;
    
    private boolean isAdmin;
    private int imgViewClicks = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userDAO = Room.databaseBuilder(this, ExpenseTrackerDatabase.class, ExpenseTrackerDatabase.DATABASE_NAME).allowMainThreadQueries().build().userDAO();

        mSignupBinding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(mSignupBinding.getRoot());

        mUsername = mSignupBinding.usernameSignup;
        mFirstname = mSignupBinding.firstnameSignup;
        mPassword = mSignupBinding.passwordSignup;

        mSignupBtn = mSignupBinding.signUpBtn;

        mSignupImg = mSignupBinding.signupImg;

        mSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsername.getText().toString();
                String firstName = mFirstname.getText().toString();
                String password = mPassword.getText().toString();

                User userExists = userDAO.getUserByUsername(username);

                if(userExists == null) {
                    if(!isAdmin) {
                        userDAO.insertUser(new User(username, password, firstName, User.UserRole.USER));
                    } else {
                        userDAO.insertUser(new User(username, password, firstName, User.UserRole.ADMIN));
                    }

                    Intent intent = new Intent(SignupActivity.this, LandingPageActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SignupActivity.this, "Username is taken", Toast.LENGTH_SHORT).show();
                    mUsername.setText("");
                    mPassword.setText("");
                    mFirstname.setText("");
                }
            }
        });

       mSignupImg.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               imgViewClicks += 1;
               if(imgViewClicks >= 5) {
                   isAdmin = true;
                   Toast.makeText(SignupActivity.this, "You've found the secret tunnel!", Toast.LENGTH_SHORT).show();
               }
           }
       });

    }
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, SignupActivity.class);
        return intent;
    }
}