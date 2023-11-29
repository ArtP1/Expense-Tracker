package com.example.expensetracker.ExpenseTrackerDb.Utilities.Enums;

public enum WalletSubtype {

    // Digital Wallets
    APPLE_PAY(WalletType.DIGITAL),
    GOOGLE_PAY(WalletType.DIGITAL),
    SAMSUNG_PAY(WalletType.DIGITAL),
    PAYPAL(WalletType.DIGITAL),
    VENMO(WalletType.DIGITAL),
    ALIPAY(WalletType.DIGITAL),

    // Crypto Wallets
    HARDWARE_WALLET(WalletType.CRYPTO),
    SOFTWARE_WALLET(WalletType.CRYPTO),
    MOBILE_WALLET(WalletType.CRYPTO),
    WEB_WALLET(WalletType.CRYPTO),

    // Bank Wallets
    CHECKING_ACCOUNT(WalletType.BANK),
    SAVINGS_ACCOUNT(WalletType.BANK),
    MONEY_MARKET_ACCOUNT(WalletType.BANK),
    CERTIFICATE_OF_DEPOSIT(WalletType.BANK),

    // Credit Wallets
    CREDIT_CARD(WalletType.CREDIT),
    DEBIT_CARD(WalletType.CREDIT),
    REWARDS_CREDIT_CARD(WalletType.CREDIT),
    STUDENT_LOAN(WalletType.CREDIT),
    AUTO_LOAN(WalletType.CREDIT),
    PERSONAL_LOAN(WalletType.CREDIT),


    // Other Wallets
    LOYALTY_POINTS(WalletType.OTHER),
    GIFT_CARDS(WalletType.OTHER),
    MEMBERSHIP_CARDS(WalletType.OTHER),
    PREPAID_CARDS(WalletType.OTHER),
    PHYSICAL_WALLET(WalletType.OTHER);

    private final WalletType walletType;

    WalletSubtype(WalletType walletType) {
        this.walletType = walletType;
    }

    public WalletType getWalletType() {
        return walletType;
    }
}
