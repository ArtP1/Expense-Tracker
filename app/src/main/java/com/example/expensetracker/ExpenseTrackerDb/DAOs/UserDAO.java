package com.example.expensetracker.ExpenseTrackerDb.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.expensetracker.ExpenseTrackerDb.Entities.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    void insertUser(User... users);

    @Insert
    long insertUserAndReturnId(User user);

    @Insert
    void insertAllUsers(User[] users);

    @Update
    void updateUser(User... users);

    @Delete
    void deleteUser(User... users);

    @Query("SELECT COUNT(*) AS NumberOfUsers FROM user_table")
    Integer numberOfUsers();

    @Query("SELECT * FROM user_table")
    List<User> getAllUsers();

    @Query("SELECT * FROM user_table WHERE id==:userId")
    User getUserById(long userId);

    @Query("SELECT * FROM user_table WHERE username==:username")
    User getUserByUsername(String username);

    @Query("SELECT EXISTS (SELECT 1 FROM user_table WHERE username==:username)")
    Boolean userExists(String username);

}
