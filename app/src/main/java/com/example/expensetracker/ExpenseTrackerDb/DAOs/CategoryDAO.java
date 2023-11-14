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
    // Batch Operations
    @Insert
    void insertCategory(Category... categories);
    @Update
    void updateCategory(Category... categories);
    @Delete
    void deleteCategory(Category... categories);

    // Retrieve methods
    @Query("SELECT * FROM category_table WHERE id = :categoryId")
    Category getCategoryById(int categoryId);

    @Query("SELECT * FROM category_table")
    List<Category> getAllCategories();

//    @Query("DELETE FROM category_table")
//    void deleteAllCategories();

}
