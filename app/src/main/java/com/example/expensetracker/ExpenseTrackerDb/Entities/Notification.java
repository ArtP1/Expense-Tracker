package com.example.expensetracker.ExpenseTrackerDb.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.expensetracker.ExpenseTrackerDb.DateTimeConverter;
import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;

import java.time.LocalDateTime;

@Entity(tableName = ExpenseTrackerDatabase.NOTIFICATION_TABLE,
        foreignKeys = @ForeignKey(entity = User.class,
                parentColumns = "id",
                childColumns = "user_id",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE))
public class Notification {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int user_id;
    private String title;
    private String message;
    private boolean is_read;
    private Integer from;

    public enum Type {
        ALERT,
        ACHIEVEMENT,
        REMINDER,
        WEEKLY_INSIGHT
    }

    @NonNull
    @ColumnInfo(collate = ColumnInfo.NOCASE) // Case-Insensitive Collation
    private Notification.Type notiType;

    @TypeConverters(DateTimeConverter.class)
    private LocalDateTime dateTime;

    @Ignore
    public Notification(int user_id, String title, String message, Integer from, @NonNull Type notiType) {
        LocalDateTime currDateTime = LocalDateTime.now();
        this.user_id = user_id;
        this.title = title;
        this.message = message;
        this.is_read = false;
        this.from = from;
        this.notiType = notiType;
        this.dateTime = currDateTime;
    }

    public Notification(int user_id, String title, String message, @NonNull Type notiType) {
        LocalDateTime currDateTime = LocalDateTime.now();
        this.user_id = user_id;
        this.title = title;
        this.message = message;
        this.is_read = false;
        this.from = null;
        this.notiType = notiType;
        this.dateTime = currDateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isIs_read() {
        return is_read;
    }

    public void setIs_read(boolean is_read) {
        this.is_read = is_read;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NonNull
    public Type getNotiType() {
        return notiType;
    }

    public void setNotiType(@NonNull Type notiType) {
        this.notiType = notiType;
    }
}
