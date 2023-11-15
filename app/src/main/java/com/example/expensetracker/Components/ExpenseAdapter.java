package com.example.expensetracker.Components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.expensetracker.ExpenseTrackerDb.DAOs.CategoryDAO;
import com.example.expensetracker.ExpenseTrackerDb.Entities.Category;
import com.example.expensetracker.ExpenseTrackerDb.Entities.Expense;
import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;
import com.example.expensetracker.R;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseViewHolder> {
    CategoryDAO categoryDAO;

    Context context;
    List<Expense> expenseList;

    public ExpenseAdapter(Context context, List<Expense> expenseList) {
        this.context = context;
        this.expenseList = expenseList;
        this.categoryDAO = Room.databaseBuilder(context, ExpenseTrackerDatabase .class, ExpenseTrackerDatabase.DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build()
                            .categoryDAO();
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExpenseViewHolder(LayoutInflater.from(context).inflate(R.layout.expense_view,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH );

        int categoryId = expenseList.get(position).getCategory_id();
        Category category = categoryDAO.getCategoryById(categoryId);

        if(category != null) {
//            holder.mExpenseImage.setImageResource((category.getIcon()));
            String resourceName = category.getIcon();
            int resourceId = context.getResources().getIdentifier(resourceName, "drawable", context.getPackageName());

            if(resourceId != 0) {
                holder.mExpenseImage.setImageResource(resourceId);
            } else {
                holder.mExpenseImage.setImageResource(R.drawable.default_expense_icon);
            }
        } else {
            holder.mExpenseImage.setImageResource(R.drawable.default_expense_icon);
        }

        holder.mExpenseTitle.setText(expenseList.get(position).getTitle());
        holder.mExpenseDate.setText(expenseList.get(position).getDateSubmitted().format(formatter));
        Locale locale = Locale.US;
        String formattedAmount = String.format(locale, "$%.2f", expenseList.get(position).getAmount());
        holder.mExpenseAmount.setText(formattedAmount);
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }
}
