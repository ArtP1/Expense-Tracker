package com.example.expensetracker.ExpenseTrackerDb.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.example.expensetracker.ExpenseTrackerDb.Entities.UserWallet;
import com.example.expensetracker.ExpenseTrackerDb.ImageConverter;

@Dao
@TypeConverters(ImageConverter.class)
public interface UserWalletDAO {
    @Insert
    void insertUserWallet(UserWallet... userWallets);

    @Insert
    void insertAllUserWallets(UserWallet[] userWallets);

    @Update
    void updateUserWallet(UserWallet... userWallets);

    @Delete
    void deleteUserWallet(UserWallet... userWallets);


}
