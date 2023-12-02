package com.example.expensetracker.ExpenseTrackerDb.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.expensetracker.ExpenseTrackerDb.Entities.UserDigitalWallet;
import com.example.expensetracker.ExpenseTrackerDb.Models.UserDigitalWalletWithInformation;

import java.util.List;

@Dao
public interface UserDigitalWalletDAO {
    @Insert
    void insertUserDigitalWallet(UserDigitalWallet... userDigitalWallets);

    @Insert
    void insertAllUserDigitalWallets(UserDigitalWallet[] userDigitalWallets);

    @Update
    void updateUserDigitalWallet(UserDigitalWallet... userDigitalWallets);

    @Delete
    void deleteUserDigitalWallet(UserDigitalWallet... userDigitalWallets);

    @Query("SELECT * FROM user_digital_wallet_table WHERE user_id = :user_id")
    List<UserDigitalWallet> getUserDigitalWalletsByUserID(long user_id);

    @Query("SELECT * FROM user_digital_wallet_table WHERE user_id = :user_id AND isDefault = 1")
    UserDigitalWallet getDefaultUserDigitalWalletByUserID(long user_id);

    @Query("SELECT * FROM user_digital_wallet_table ud " +
            "INNER JOIN digital_wallet_table d ON ud.wallet_type = d.name " +
            "WHERE user_id = :user_id AND isDefault = 1")
    UserDigitalWalletWithInformation getDefaultUserDigitalWalletAndInformationByUserID(long user_id);

    @Query("SELECT d.img FROM user_digital_wallet_table ud INNER JOIN digital_wallet_table d ON ud.wallet_type = d.name WHERE user_id = :user_id AND isDefault = 1")
    String getUserDefaultDigitalWalletImg(long user_id);

}
