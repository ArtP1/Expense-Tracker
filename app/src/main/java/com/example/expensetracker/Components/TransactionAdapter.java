package com.example.expensetracker.Components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.CategoryDAO;
import com.example.expensetracker.ExpenseTrackerDb.Entities.Category;
import com.example.expensetracker.ExpenseTrackerDb.Entities.Transaction;
import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;
import com.example.expensetracker.R;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionViewHolder> {
    CategoryDAO categoryDAO;

    Context context;
    List<Transaction> transactionList;

    public TransactionAdapter(Context context, List<Transaction> transactionList) {
        this.context = context;
        this.transactionList = transactionList;
        this.categoryDAO = Room.databaseBuilder(context, ExpenseTrackerDatabase.class, ExpenseTrackerDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .categoryDAO();
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionViewHolder(LayoutInflater.from(context).inflate(R.layout.transaction_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH);

        String categoryName = transactionList.get(position).getCategory_name();
        Transaction.Type transType = transactionList.get(position).getTransType();

        Category category = categoryDAO.getCategoryByName(categoryName);

        String iconUrl  = category.getIcon();
        if (iconUrl != null && !iconUrl.isEmpty()) {
            // Load image using Glide
            Glide.with(context)
                    .load(iconUrl) // Load image from the URL string
                    .placeholder(R.drawable.default_expense_icon) // Placeholder while loading
                    .error(R.drawable.error_icon) // Error placeholder if loading fails
                    .into(holder.mTransactionImage); // Set the loaded image to ImageView
        } else {
            holder.mTransactionImage.setImageResource(R.drawable.default_expense_icon);
        }

        holder.mTransactionTitle.setText(transactionList.get(position).getTitle());
        holder.mTransactionDate.setText(transactionList.get(position).getDateSubmitted().format(formatter));
        Locale locale = Locale.US;
        String formattedAmount = String.format(locale, "$%.2f", transactionList.get(position).getAmount());

        if (transType.equals(Transaction.Type.EXPENSE)) {
//            holder.mTransactionAmount.setTextColor(ContextCompat.getColor(context, R.color.expense_color));
            holder.mTransactionAmount.setText(context.getString(R.string.expense_amount_format, formattedAmount));
        } else if (transType.equals(Transaction.Type.EARNING)) {
//            holder.mTransactionAmount.setTextColor(ContextCompat.getColor(context, R.color.earning_color));
            holder.mTransactionAmount.setText(context.getString(R.string.earning_amount_format, formattedAmount));
        }
    }

    public Transaction getTransaction(int position) {
        return transactionList.get(position);
    }

    public void removeTransaction(int position) {
        if (position < transactionList.size() && position >= 0) {
            transactionList.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }
}
