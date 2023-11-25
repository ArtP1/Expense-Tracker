package com.example.expensetracker;

import static com.example.expensetracker.Preferences.EXPENSE_TRACKER_PREFERENCES;
import static com.example.expensetracker.Preferences.IS_LOGGED_IN_KEY;
import static com.example.expensetracker.Preferences.TYPE_OF_USER_KEY;
import static com.example.expensetracker.Preferences.USER_ID_KEY;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.expensetracker.ExpenseTrackerDb.DAOs.UserDAO;
import com.example.expensetracker.ExpenseTrackerDb.Entities.User;
import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;
import com.example.expensetracker.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ActivitySignupBinding mSignupBinding;

    UserDAO userDAO;

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
        sharedPreferences = getSharedPreferences(EXPENSE_TRACKER_PREFERENCES, MODE_PRIVATE);

        checkLoggedInState();

        editor = sharedPreferences.edit();

        userDAO = Room.databaseBuilder(this, ExpenseTrackerDatabase.class, ExpenseTrackerDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .userDAO();

        initializeViews();

        mSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsername.getText().toString();
                String firstName = mFirstname.getText().toString();
                String password = mPassword.getText().toString();

                if (!username.isEmpty() && !password.isEmpty()) {
                    Boolean userExists = userDAO.userExists(username);

                    if (!userExists) { // User with username doesn't exists
                        long insertedUserId;
                        Intent intent = null;

                        if (!isAdmin) {
                            editor.putString(TYPE_OF_USER_KEY, User.UserRole.USER.toString());

                            insertedUserId = userDAO.insertUserAndReturnId(new User(username, password, firstName, User.UserRole.USER));

                            editor.putLong(USER_ID_KEY, insertedUserId);

                            intent = new Intent(SignupActivity.this, FragmentContainerActivity.class);
                        } else {
                            editor.putString(TYPE_OF_USER_KEY, User.UserRole.ADMIN.toString());

                            userDAO.insertUser(new User(username, password, firstName, User.UserRole.ADMIN));
                            intent = new Intent(SignupActivity.this, AdminsLandingPageActivity.class);
                        }

                        editor.putBoolean(IS_LOGGED_IN_KEY, true);
                        editor.apply();

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SignupActivity.this, "Username is taken!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignupActivity.this, "Enter username and password!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mSignupImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgViewClicks += 1;
                if (imgViewClicks >= 5) {
                    isAdmin = true;
                    Toast.makeText(SignupActivity.this, "You've found the secret tunnel!", Toast.LENGTH_SHORT).show();
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
            targetActivity = FragmentContainerActivity.class;
        } else if (User.UserRole.ADMIN.toString().equals(userType)) {
            targetActivity = AdminsLandingPageActivity.class;
        } else if (User.UserRole.SUPER_ADMIN.toString().equals(userType)) {
            targetActivity = SuperAdminsLandingPageActivity.class;
        }

        startActivity(new Intent(SignupActivity.this, targetActivity));
    }

    private void initializeViews() {
        mSignupBinding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(mSignupBinding.getRoot());

        mUsername = mSignupBinding.usernameSignup;
        mFirstname = mSignupBinding.firstnameSignup;
        mPassword = mSignupBinding.passwordSignup;

        mSignupBtn = mSignupBinding.signUpBtn;

        mSignupImg = mSignupBinding.signupImg;
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, SignupActivity.class);
    }
}