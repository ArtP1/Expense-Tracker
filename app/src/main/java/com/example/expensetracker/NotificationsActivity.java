package com.example.expensetracker;

import static com.example.expensetracker.Preferences.EXPENSE_TRACKER_PREFERENCES;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.expensetracker.Components.NotificationAdapter;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.NotificationDAO;
import com.example.expensetracker.ExpenseTrackerDb.Entities.Notification;
import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;
import com.example.expensetracker.databinding.ActivityNotificationsBinding;

import java.util.List;

public class NotificationsActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    ActivityNotificationsBinding mNotificationsBinding;
    RecyclerView mNotificationsRecyclerView;
    ImageView mBackBtn;

    TextView mEmptyNotificationTextView;
    NotificationDAO notificationDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNotificationsBinding = ActivityNotificationsBinding.inflate(getLayoutInflater());
        setContentView(mNotificationsBinding.getRoot());

        sharedPreferences = getSharedPreferences(EXPENSE_TRACKER_PREFERENCES, MODE_PRIVATE);

        notificationDAO = Room.databaseBuilder(this, ExpenseTrackerDatabase.class, ExpenseTrackerDatabase.DATABASE_NAME).allowMainThreadQueries().build().notificationDAO();

        mBackBtn = mNotificationsBinding.backBtn;
        mNotificationsRecyclerView = mNotificationsBinding.notificationsRecyclerView;
        mEmptyNotificationTextView = mNotificationsBinding.emptyNotificationTextView;


        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NotificationsActivity.this, FragmentContainerActivity.class));
                finish();
            }
        });

        long currUserID = sharedPreferences.getLong(Preferences.USER_ID_KEY, -1);


        try {
            LiveData<List<Notification>> allUserNotificationLiveData = notificationDAO.allNotificationsByUserID(currUserID);

            allUserNotificationLiveData.observe(this, userNotificationList -> {
                if (userNotificationList != null && !userNotificationList.isEmpty()) {
                    mEmptyNotificationTextView.setVisibility(View.GONE);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
                        @Override
                        public boolean canScrollVertically() {
                            return false;
                        }
                    };

                    mNotificationsRecyclerView.setLayoutManager(layoutManager);
                    NotificationAdapter notificationAdapter = new NotificationAdapter(this, userNotificationList);
                    mNotificationsRecyclerView.setAdapter(notificationAdapter);
                } else {
                    mEmptyNotificationTextView.setVisibility(View.VISIBLE);
                    mNotificationsRecyclerView.setVisibility(View.GONE);
                }
            });
        } catch (Exception e) {
            mEmptyNotificationTextView.setVisibility(View.VISIBLE);
            mNotificationsRecyclerView.setVisibility(View.GONE);
        }
    }
}