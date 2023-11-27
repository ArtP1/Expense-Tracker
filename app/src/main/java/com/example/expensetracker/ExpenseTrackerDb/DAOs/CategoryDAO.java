package com.example.expensetracker.ExpenseTrackerDb.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.expensetracker.ExpenseTrackerDb.Entities.Category;

import java.util.List;

@Dao
public interface CategoryDAO {
    @Insert
    void insertCategory(Category... categories);

    @Insert
    void insertAllCategories(Category[] categories);

    @Update
    void updateCategory(Category... categories);

    @Delete
    void deleteCategory(Category... categories);

    @Query("SELECT COUNT(*) AS NumberOfCategories FROM category_table")
    Integer numberOfCategories();

    @Query("SELECT * FROM category_table WHERE name=:category_name")
    Category getCategoryByName(String category_name);

    @Query("SELECT * FROM category_table")
    List<Category> getAllCategories();

}
