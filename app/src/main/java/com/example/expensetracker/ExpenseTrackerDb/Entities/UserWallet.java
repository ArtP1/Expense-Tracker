package com.example.expensetracker.ExpenseTrackerDb.Entities;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;
import com.example.expensetracker.ExpenseTrackerDb.ImageConverter;
import com.example.expensetracker.ExpenseTrackerDb.Utilities.Enums.WalletSubtype;
import com.example.expensetracker.ExpenseTrackerDb.Utilities.Enums.WalletType;

@Entity(tableName = ExpenseTrackerDatabase.USER_WALLET_TABLE,
        foreignKeys = @ForeignKey(entity = User.class,
                parentColumns = "id",
                childColumns = "user_id",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE)
)
public class UserWallet {
    // PRIMARY KEY(S)
    @PrimaryKey(autoGenerate = true)
    private int id;

    // FOREIGN KEY(S)
    private int user_id;

    // COLUMN(S)
    @NonNull
    @ColumnInfo(collate = ColumnInfo.NOCASE)
    private WalletType type;

    @NonNull
    @ColumnInfo(collate = ColumnInfo.NOCASE)
    private WalletSubtype walletSubtype;

    private String card_number_or_token;
    private boolean isDefault;

    @NonNull
    @TypeConverters(ImageConverter.class)
    private Bitmap walletImg;

    public UserWallet(int id, int user_id, @NonNull WalletType type, @NonNull WalletSubtype walletSubtype, String card_number_or_token, boolean isDefault, @NonNull Bitmap walletImg) {
        this.id = id;
        this.user_id = user_id;
        this.type = type;
        this.walletSubtype = walletSubtype;
        this.card_number_or_token = card_number_or_token;
        this.isDefault = isDefault;
        this.walletImg = walletImg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @NonNull
    public WalletType getType() {
        return type;
    }

    public void setType(@NonNull WalletType type) {
        this.type = type;
    }

    @NonNull
    public WalletSubtype getWalletSubtype() {
        return walletSubtype;
    }

    public void setWalletSubtype(@NonNull WalletSubtype walletSubtype) {
        this.walletSubtype = walletSubtype;
    }

    public String getCard_number_or_token() {
        return card_number_or_token;
    }

    public void setCard_number_or_token(String card_number_or_token) {
        this.card_number_or_token = card_number_or_token;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    @NonNull
    public Bitmap getWalletImg() {
        return walletImg;
    }

    public void setWalletImg(@NonNull Bitmap walletImg) {
        this.walletImg = walletImg;
    }
}
