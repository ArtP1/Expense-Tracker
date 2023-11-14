package com.example.expensetracker.ExpenseTrackerDb.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.expensetracker.ExpenseTrackerDb.Entities.Expense;
import com.example.expensetracker.ExpenseTrackerDb.Entities.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insertUser(User... users);
    @Update
    void updateUser(User... users);
    @Delete
    void deleteUser(User... users);

    // These will most often be used by admins ---------------
    @Query("SELECT * FROM user_table")
    List<User> getAllUsers();

    @Query("SELECT * FROM user_table WHERE id==:userId")
    Expense getUserById(int userId);


}