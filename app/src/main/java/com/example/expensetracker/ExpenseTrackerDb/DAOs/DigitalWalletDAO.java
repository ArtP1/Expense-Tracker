package com.example.expensetracker.ExpenseTrackerDb.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.expensetracker.ExpenseTrackerDb.Entities.DigitalWallet;

import java.util.List;

@Dao
public interface DigitalWalletDAO {
    @Insert
    void insertDigitalWallet(DigitalWallet... digitalWallets);

    @Insert
    void insertAllDigitalWallets(DigitalWallet[] digitalWallets);

    @Update
    void updateDigitalWallet(DigitalWallet... digitalWallets);

    @Delete
    void deleteDigitalWallet(DigitalWallet... digitalWallets);

    @Query("SELECT * FROM digital_wallet_table")
    List<DigitalWallet> getAllDigitalWallets();
}
