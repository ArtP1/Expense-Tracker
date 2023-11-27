package com.example.expensetracker.Components;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.R;


public class NotificationViewHolder extends RecyclerView.ViewHolder {

    ImageView mNotificationImage;
    TextView mNotificationTitle;
    TextView mNotificationMessage;
    TextView mNotificationDateTime;

    public NotificationViewHolder(@NonNull View itemView) {
        super(itemView);

        mNotificationImage = itemView.findViewById(R.id.notificationImage);
        mNotificationTitle = itemView.findViewById(R.id.notificationTitle);
        mNotificationMessage = itemView.findViewById(R.id.notificationMessage);
        mNotificationDateTime = itemView.findViewById(R.id.notificationDateTime);
    }
}
