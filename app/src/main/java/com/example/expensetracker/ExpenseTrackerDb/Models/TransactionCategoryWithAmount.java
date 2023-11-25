package com.example.expensetracker.ExpenseTrackerDb.Models;

public class TransactionCategoryWithAmount {
    public String category_name;
    public double total_amount;

    public String icon;

    public TransactionCategoryWithAmount(String category_name, double total_amount, String icon) {
        this.category_name = category_name;
        this.total_amount = total_amount;
        this.icon = icon;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "TransactionCategoryWithAmount{" +
                "category_name='" + category_name + '\'' +
                ", total_amount=" + total_amount +
                ", icon='" + icon + '\'' +
                '}';
    }
}
