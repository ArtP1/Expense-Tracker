package com.example.expensetracker.Components;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.R;


public class CategoryTransactionViewHolder extends RecyclerView.ViewHolder {

    ImageView mTransactionsCategoryImage;
    TextView mTransactionsCategoryTitle;
    TextView mTransactionsMonthComparison;
    TextView mTransactionsCategoryAmount;

    public CategoryTransactionViewHolder(@NonNull View itemView) {
        super(itemView);

        mTransactionsCategoryImage = itemView.findViewById(R.id.transactionsCategoryImage);
        mTransactionsCategoryTitle = itemView.findViewById(R.id.tansactionsCategory);
        mTransactionsMonthComparison = itemView.findViewById(R.id.categoryTransactionsMonthComparison);
        mTransactionsCategoryAmount = itemView.findViewById(R.id.categoryTransactionsAmount);
    }
}
