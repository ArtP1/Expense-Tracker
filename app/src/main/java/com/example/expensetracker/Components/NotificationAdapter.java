package com.example.expensetracker.Components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.expensetracker.ExpenseTrackerDb.DAOs.NotificationDAO;
import com.example.expensetracker.ExpenseTrackerDb.Entities.Notification;
import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;
import com.example.expensetracker.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {
    NotificationDAO notificationDAO;

    Context context;
    List<Notification> notificationList;

    public NotificationAdapter(Context context, List<Notification> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
        this.notificationDAO = Room.databaseBuilder(context, ExpenseTrackerDatabase.class, ExpenseTrackerDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .notificationDAO();
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationViewHolder(LayoutInflater.from(context).inflate(R.layout.notification_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {

        Notification notification = notificationList.get(position);

        Notification.Type notificationType = notification.getNotiType();
        String title = notification.getTitle();
        String message = notification.getMessage();
        LocalDateTime dateTime = notification.getDateTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d - h:mm a");
        String formattedDateTime = dateTime.format(formatter);


        // Set values to the ViewHolder views
        if (notificationType.equals(Notification.Type.ALERT)) {
            holder.mNotificationImage.setImageResource(R.drawable.notification_alert_icon);
        } else if (notificationType.equals(Notification.Type.REMINDER)) {
            holder.mNotificationImage.setImageResource(R.drawable.notifications_reminder_icon);

        } else if (notificationType.equals(Notification.Type.ACHIEVEMENT)) {
            holder.mNotificationImage.setImageResource(R.drawable.notification_achievement_icon);

        } else if (notificationType.equals(Notification.Type.WEEKLY_INSIGHT)) {
            holder.mNotificationImage.setImageResource(R.drawable.notification_weekyl_insight_icon);
        }

        holder.mNotificationTitle.setText(title);
        holder.mNotificationMessage.setText(message);
        holder.mNotificationDateTime.setText(String.valueOf(formattedDateTime));
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }
}
