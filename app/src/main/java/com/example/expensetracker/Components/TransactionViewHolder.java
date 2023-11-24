package com.example.expensetracker.Components;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.R;


public class TransactionViewHolder extends RecyclerView.ViewHolder {

    ImageView mTransactionImage;
    TextView mTransactionTitle;
    TextView mTransactionDate;
    TextView mTransactionAmount;

    public TransactionViewHolder(@NonNull View itemView) {
        super(itemView);

        mTransactionImage = itemView.findViewById(R.id.transactionImage);
        mTransactionTitle = itemView.findViewById(R.id.transactionTitle);
        mTransactionDate = itemView.findViewById(R.id.transactionDate);
        mTransactionAmount = itemView.findViewById(R.id.transactionAmount);
    }
}
