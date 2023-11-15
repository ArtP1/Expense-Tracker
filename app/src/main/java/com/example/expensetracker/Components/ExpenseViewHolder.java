package com.example.expensetracker.Components;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.R;


public class ExpenseViewHolder extends RecyclerView.ViewHolder {

    ImageView mExpenseImage;
    TextView mExpenseTitle;
    TextView mExpenseDate;
    TextView mExpenseAmount;

    public ExpenseViewHolder(@NonNull View itemView) {
        super(itemView);

        mExpenseImage = itemView.findViewById(R.id.expenseImage);
        mExpenseTitle = itemView.findViewById(R.id.expenseTitle);
        mExpenseDate = itemView.findViewById(R.id.expenseDate);
        mExpenseAmount = itemView.findViewById(R.id.expenseAmount);
    }
}
