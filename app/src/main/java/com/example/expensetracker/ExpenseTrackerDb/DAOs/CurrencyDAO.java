package com.example.expensetracker.ExpenseTrackerDb.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.expensetracker.ExpenseTrackerDb.Entities.Currency;

import java.util.List;

@Dao
public interface CurrencyDAO {
    @Insert
    void insertCurrency(Currency... currencies);

    @Insert
    void insertAllCurrencies(Currency[] currencies);

    @Update
    void updateCurrency(Currency... currencies);

    @Delete
    void deleteCurrency(Currency... currencies);

    // These will most often be used by admins ---------------
    @Query("SELECT COUNT(*) AS NumberOfCurrencies FROM currency_table")
    Integer numberOfCurrencies();

    @Query("SELECT * FROM currency_table")
    List<Currency> getAllCurrencies();
}
