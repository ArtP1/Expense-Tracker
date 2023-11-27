package com.example.expensetracker.ExpenseTrackerDb.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.expensetracker.ExpenseTrackerDb.Entities.Notification;

import java.util.List;

@Dao
public interface NotificationDAO {
    @Insert
    void insertNotification(Notification... notifications);

    @Insert
    void insertAllNotifications(Notification[] notifications);

    @Update
    void updateNotification(Notification... notifications);

    @Delete
    void deleteNotification(Notification... notifications);

    @Query("SELECT * FROM notification_table WHERE user_id = :user_id")
    LiveData<List<Notification>> allNotificationsByUserID(long user_id);
}
