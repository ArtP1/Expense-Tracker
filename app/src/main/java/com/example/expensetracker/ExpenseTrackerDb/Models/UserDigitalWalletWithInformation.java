package com.example.expensetracker.ExpenseTrackerDb.Models;

public class UserDigitalWalletWithInformation {
    private int id;
    private int user_id;
    private String wallet_type;
    private String card_number_or_token;
    private boolean isDefault;
    private String name;
    private String img;
    private String type;

    public UserDigitalWalletWithInformation(int id, int user_id, String wallet_type, String card_number_or_token, boolean isDefault, String name, String img, String type) {
        this.id = id;
        this.user_id = user_id;
        this.wallet_type = wallet_type;
        this.card_number_or_token = card_number_or_token;
        this.isDefault = isDefault;
        this.name = name;
        this.img = img;
        this.type = type;
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

    public String getWallet_type() {
        return wallet_type;
    }

    public void setWallet_type(String wallet_type) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UserDigitalWalletWithInformation{}";
    }
}
