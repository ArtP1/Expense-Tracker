package com.example.expensetracker.Components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.expensetracker.ExpenseTrackerDb.DAOs.CategoryDAO;
import com.example.expensetracker.ExpenseTrackerDb.Entities.Category;
import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;
import com.example.expensetracker.ExpenseTrackerDb.Models.TransactionCategoryWithAmount;
import com.example.expensetracker.R;

import java.util.List;
import java.util.Locale;

public class CategoryTransactionAdapter extends RecyclerView.Adapter<CategoryTransactionViewHolder> {
    CategoryDAO categoryDAO;

    Context context;
    List<TransactionCategoryWithAmount> transactionsCategoryWithAmount;

    public CategoryTransactionAdapter(Context context, List<TransactionCategoryWithAmount> transactionList) {
        this.context = context;
        this.transactionsCategoryWithAmount = transactionList;
        this.categoryDAO = Room.databaseBuilder(context, ExpenseTrackerDatabase.class, ExpenseTrackerDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .categoryDAO();
    }

    @NonNull
    @Override
    public CategoryTransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryTransactionViewHolder(LayoutInflater.from(context).inflate(R.layout.category_transaction_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryTransactionViewHolder holder, int position) {

        String categoryName = transactionsCategoryWithAmount.get(position).getCategory_name();

        Category category = categoryDAO.getCategoryByName(categoryName);

        if (category != null) {
//            holder.mExpenseImage.setImageResource((category.getIcon()));
            String resourceName = category.getIcon();
            int resourceId = context.getResources().getIdentifier(resourceName, "drawable", context.getPackageName());

            if (resourceId != 0) {
                holder.mTransactionsCategoryImage.setImageResource(resourceId);
            } else {
                holder.mTransactionsCategoryImage.setImageResource(R.drawable.default_expense_icon);
            }
        } else {
            holder.mTransactionsCategoryImage.setImageResource(R.drawable.default_expense_icon);
        }

        holder.mTransactionsCategoryTitle.setText(transactionsCategoryWithAmount.get(position).getCategory_name());
        Locale locale = Locale.US;
        String formattedAmount = String.format(locale, "$%.2f", transactionsCategoryWithAmount.get(position).getTotal_amount());

//      holder.mTransactionAmount.setTextColor(ContextCompat.getColor(context, R.color.expense_color));
        holder.mTransactionsCategoryAmount.setText(context.getString(R.string.expense_amount_format, formattedAmount));
    }


    @Override
    public int getItemCount() {
        return transactionsCategoryWithAmount.size();
    }
}
