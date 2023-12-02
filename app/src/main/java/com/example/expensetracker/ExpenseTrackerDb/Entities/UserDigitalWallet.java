package com.example.expensetracker.ExpenseTrackerDb.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;

@Entity(tableName = ExpenseTrackerDatabase.USER_DIGITAL_WALLET_TABLE,
        foreignKeys = {
            @ForeignKey(
                    entity = User.class,
                    parentColumns = "id",
                    childColumns = "user_id",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE
            ),
            @ForeignKey(
                    entity = DigitalWallet.class,
                    parentColumns = "name",
                    childColumns = "wallet_type",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE
            )
        },
        indices = {
                @Index("user_id"),
                @Index("wallet_type")
        }
)
public class UserDigitalWallet {
    // PRIMARY KEY(S)
    @PrimaryKey(autoGenerate = true)
    private long id;

    // FOREIGN KEY(S)
    private long user_id;

    @NonNull
    private String wallet_type;

    // COLUMN(S)
    private String card_number_or_token;

    private boolean isDefault;

    @Ignore
    public UserDigitalWallet(long user_id, @NonNull String wallet_type, String card_number_or_token, boolean isDefault) {
        this.user_id = user_id;
        this.wallet_type = wallet_type;
        this.card_number_or_token = card_number_or_token;
        this.isDefault = isDefault;
    }

    public UserDigitalWallet(long user_id, @NonNull String wallet_type, String card_number_or_token) {
        this.user_id = user_id;
        this.wallet_type = wallet_type;
        this.card_number_or_token = card_number_or_token;
        this.isDefault = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    @NonNull
    public String getWallet_type() {
        return wallet_type;
    }

    public void setWallet_type(@NonNull String wallet_type) {
        this.wallet_type = wallet_type;
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
    @Override
    public String toString() {
        if(card_number_or_token.length() >= 4) {
            return wallet_type + "      ...." + card_number_or_token.substring(card_number_or_token.length() - 4);
        }

        return wallet_type;
    }
}
