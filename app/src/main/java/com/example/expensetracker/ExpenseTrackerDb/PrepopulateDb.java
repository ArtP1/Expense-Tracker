package com.example.expensetracker.ExpenseTrackerDb;


import com.example.expensetracker.ExpenseTrackerDb.Entities.Category;
import com.example.expensetracker.ExpenseTrackerDb.Entities.Currency;
import com.example.expensetracker.ExpenseTrackerDb.Entities.Notification;
import com.example.expensetracker.ExpenseTrackerDb.Entities.PaymentMethod;
import com.example.expensetracker.ExpenseTrackerDb.Entities.Transaction;
import com.example.expensetracker.ExpenseTrackerDb.Entities.User;
import com.example.expensetracker.ExpenseTrackerDb.Entities.UserDigitalWallet;
import com.example.expensetracker.ExpenseTrackerDb.Entities.DigitalWallet;

import java.time.LocalDate;

public class PrepopulateDb {
    public static Currency[] populateCurrencyData() {
        return new Currency[]{
                new Currency("AFN", "Afghani", "؋"),
                new Currency("ANG", "Netherlands Antillean guilder", "ƒ"),
                new Currency("ARS", "Argentine peso", "$"),
                new Currency("AUD", "Australian dollar", "$"),
                new Currency("AWG", "Guilder", "ƒ"),
                new Currency("AZN", "Manat", "ман"),
                new Currency("BAM", "Convertible mark", "KM"),
                new Currency("BBD", "Barbadian dollar", "$"),
                new Currency("BGN", "Bulgarian lev", "лв"),
                new Currency("BMD", "Bermudian dollar", "$"),
                new Currency("BWP", "Pula", "P"),
                new Currency("BYR", "Belarus rubel", "p."),
                new Currency("BZD", "Belize dollar", "BZ$"),
                new Currency("CAD", "Canadian dollar", "$"),
                new Currency("CKD", "Cook dollar", "$"),
                new Currency("CLP", "Chilean peso", "$"),
                new Currency("CNY", "Renminbi Yuán", "¥"),
                new Currency("COP", "Colombian peso", "$"),
                new Currency("CRC", "Colon", "₡"),
                new Currency("CUP", "Cuban peso", "₱"),
                new Currency("CZK", "Czech krone", "Kč"),
                new Currency("DKK", "Danish krone", "kr"),
                new Currency("DOP", "Dominican peso", "RD$"),
                new Currency("EGP", "Egypt pound", "£"),
                new Currency("EUR", "Euro", "€"),
                new Currency("FJD", "Fiji ", "$"),
                new Currency("FKP", "Falklands pound", "£"),
                new Currency("FOK", "Faroese krona", "kr"),
                new Currency("GBP", "Sterling pound", "£"),
                new Currency("GIP", "Gibraltar pound", "£"),
                new Currency("GTQ", "Quetzal", "Q"),
                new Currency("GYD", "Guyana dollar", "$"),
                new Currency("HKD", "Hong Kong dollar", "$"),
                new Currency("HUF", "Hungarian forint", "Ft"),
                new Currency("IDR", "Indonesian rupiah", "Rp"),
                new Currency("ILS", "Israeli sheqel", "₪"),
                new Currency("INR", "Indian rupee", "₹"),
                new Currency("IRR", "Iranian rial", "﷼"),
                new Currency("ISK", "Icelandic krone", "kr"),
                new Currency("JMD", "Jamaica dollar", "J$"),
                new Currency("JPY", "Japanese yen", "¥"),
                new Currency("KGS", "Som", "лв"),
                new Currency("KHR", "Cambodian riel", "៛"),
                new Currency("KID", "Kiribati dollar", "$"),
                new Currency("KRW", "South Korean won", "₩"),
                new Currency("KYD", "Cayman dollar", "$"),
                new Currency("KZT", "Tenge", "лв"),
                new Currency("LAK", "Kip", "₭"),
                new Currency("LBP", "Lebanese pound", "£"),
                new Currency("LKR", "Lanka rupee", "₨"),
                new Currency("LRD", "Liberian dollar", "$"),
                new Currency("LSL", "Lesotho loti", "L"),
                new Currency("MKD", "Denar", "ден"),
                new Currency("MNT", "Tugrik", "₮"),
                new Currency("MUR", "Mauritian rupee", "₨"),
                new Currency("MXN", "Mexican peso", "$"),
                new Currency("MYR", "Ringgit", "RM"),
                new Currency("NGN", "Naira", "₦"),
                new Currency("NIO", "Córdoba oro", "C$"),
                new Currency("NOK", "Norwegian krone", "kr"),
                new Currency("NPR", "Nepalese rupee", "₨"),
                new Currency("NZD", "Zealand dollar", "$"),
                new Currency("OMR", "Omani rial", "﷼"),
                new Currency("PAB", "Panamanian balboa", "B/."),
                new Currency("PEN", "Nuevo sol", "S/."),
                new Currency("PKR", "Pakistanian rupee", "₨"),
                new Currency("PLN", "Zloty", "zł"),
                new Currency("PYG", "Guarani", "Gs"),
                new Currency("QAR", "Qatari rial", "﷼"),
                new Currency("RON", "Romanian leu", "lei"),
                new Currency("RSD", "Serbian dinar", "Дин."),
                new Currency("RUB", "Russian rubel", "руб"),
                new Currency("SAR", "Saudi rial", "﷼"),
                new Currency("SBD", "Salomon dollar", "$"),
                new Currency("SCR", "Seychelles rupee", "Rs"),
                new Currency("SDG", "Sudanese pound", "£"),
                new Currency("SEK", "Swedish krone", "kr"),
                new Currency("SGD", "Singapore dollar", "$"),
                new Currency("SHP", "St.-Helena pound", "£"),
                new Currency("SOS", "Somalian shilling", "S"),
                new Currency("SRD", "Surinam dollar", "$"),
                new Currency("SSP", "Sudanese pound", "£"),
                new Currency("SYP", "Syrian pound", "£"),
                new Currency("THB", "Thai baht", "฿"),
                new Currency("TRY", "Turkish lira", "TL"),
                new Currency("TTD", "Trinidad and Tobago dollar", "TT$"),
                new Currency("TVD", "Tuvaluan dollar", "$"),
                new Currency("TWD", "New Taiwan dollar", "NT$"),
                new Currency("UAH", "Hrywnja", "₴"),
                new Currency("USD", "American dollar", "$"),
                new Currency("UYU", "Uruguay peso", "$U"),
                new Currency("UZS", "Uzbekistan sum", "лв"),
                new Currency("VED", "Bolivar digital", "Bs.D"),
                new Currency("VND", "Dong", "₫"),
                new Currency("XCD", "East Caribbean dollar", "$"),
                new Currency("YER", "Jemen rial", "﷼"),
                new Currency("ZAR", "South African rand", "R"),
                new Currency("ZMW", "Zambian kwacha", "K")
        };
    }

    public static User[] populateUsersData() {
        return new User[]{
                new User("user123", "123", "John", User.UserRole.USER),
                new User("user", "123", "Jaime", User.UserRole.USER),
                new User("admin123", "secret", "Admin", User.UserRole.ADMIN),
                new User("superadmin", "s3cr3t", "SuperJaime", User.UserRole.SUPER_ADMIN)
        };
    }

    public static PaymentMethod[] populatePaymentMethodData() {
        return new PaymentMethod[]{
                new PaymentMethod("Bank Transfer", "https://i.postimg.cc/3wMTDvjx/bank-transfer-icon.png"),
                new PaymentMethod("Gift Card", "https://i.postimg.cc/VvHysrL1/gift-card-icon.png"),
                new PaymentMethod("Digital Wallet", "https://i.postimg.cc/j56XsGch/digital-wallet.png"),
                new PaymentMethod("Check", "https://i.postimg.cc/5NR1bG6v/bank-check-icon.png"),
                new PaymentMethod("Direct Deposit", "https://i.postimg.cc/50gdGKhX/direc-deposit-icon.png"),
                new PaymentMethod("Cash", "https://i.postimg.cc/kgRC4cbH/cash-icon.png"),
                new PaymentMethod("Crypto", "https://i.postimg.cc/vTmFdLy3/crypto-icon.png")
        };
    }

    public static Category[] populateCategoryData() {
        return new Category[]{
                new Category("Housing", "https://i.postimg.cc/xTmT4tFR/housing-icon.png"),
                new Category("Transportation", "https://i.postimg.cc/nLp5qYWT/transportation-icon.png"),
                new Category("Dining", "https://i.postimg.cc/Px6BXp2y/dining-icon.png"),
                new Category("Healthcare", "https://i.postimg.cc/rpT6QLD1/health-icon.png"),
                new Category("Entertainment", "https://i.postimg.cc/pTVwMYYy/entertainment-icon.png"),
                new Category("Personal Care", "https://i.postimg.cc/7hdd7vvT/personal-care-icon.png"),
                new Category("Shopping", "https://i.postimg.cc/02KLvNmG/shopping-icon.png"),
                new Category("Travel", "https://i.postimg.cc/dVMgVKmb/travel-icon.png"),
                new Category("Education", "https://i.postimg.cc/Z5pt8q5Y/education-icon.png"),
                new Category("Taxes", "https://i.postimg.cc/rwS3dYfK/taxes-icon.png"),
                new Category("Debt", "https://i.postimg.cc/28PNLWmX/debt-icon.png"),
                new Category("Investment", "https://i.postimg.cc/fyJ1JzPN/investment-icon.png"),
                new Category("Freelancing", "https://i.postimg.cc/N0k3nPXW/freelancing-icon.png"),
                new Category("Salary", "https://i.postimg.cc/gcTf1hwk/salary-icon.png"),
                new Category("Utilities", "https://i.postimg.cc/s2mLmGdz/utilities-icon.png")
        };
    }

    public static Transaction[] insertAllUserTransactions() {
        LocalDate pastDate1 = LocalDate.of(2023, 10, 15);
        LocalDate pastDate2 = LocalDate.of(2023, 12, 28);
        LocalDate pastDate3 = LocalDate.of(2023, 12, 30);

        return new Transaction[]{
                new Transaction(1, "Salary", "Direct Deposit", 3000.0, "Monthly Salary", pastDate3, "November Income", "Your Company Address", Transaction.Type.EARNING),
                new Transaction(1, "Transportation", "Check", 70.0, "Fuel", "Refilled gas for the car", "853 Abbott St", Transaction.Type.EXPENSE),
                new Transaction(1, "Freelancing", "Bank Transfer", 500.0, "Freelance Work", "Web design project", "Client Address", Transaction.Type.EARNING),
                new Transaction(1, "Investment", "Direct Deposit", 200.0, "Stock Dividend", pastDate2, "Quarterly dividend payment", "Investment Firm Address", Transaction.Type.EARNING),
                new Transaction(1, "Utilities", "Bank Transfer", 150.0, "Electricity, Water, Gas", "Monthly utility bills", "456 Elm St", Transaction.Type.EXPENSE),
                new Transaction(1, "Entertainment", "Cash", 40.0, "Concert Tickets", "Concert tickets for the weekend", "789 Oak St", Transaction.Type.EXPENSE),
                new Transaction(1, "Dining", "Cash", 50.0, "Restaurant Dinner", pastDate1, "Dinner with friends", "246 Maple St", Transaction.Type.EXPENSE),
                new Transaction(1, "Personal Care", "Digital Wallet", 1,  25.0,"Movie Night", pastDate2, "Tickets for movie night", "350 Northridge Shopping Ctr", Transaction.Type.EXPENSE),
                new Transaction(1, "Housing", "Digital Wallet", 5, 80.0, "Supermarket", "Weekly grocery shopping", "123 Main St", Transaction.Type.EXPENSE),
                new Transaction(1, "Transportation", "Digital Wallet", 2, 30.0, "Public Transit", "Public transportation fare", "101 Pine St", Transaction.Type.EXPENSE),
                new Transaction(1, "Dining", "Crypto", 1, 16.75, "Popeyes", pastDate3, "Purchased lunch for the day", "590 Auto Center Dr Unit 1A", Transaction.Type.EXPENSE),
                new Transaction(1, "Debt", "Crypto", 2, 10000.0, "Student Loans", "", "", Transaction.Type.EXPENSE)
        };
    }

    public static Notification[] populateNotificationData() {
        return new Notification[]{
                new Notification(1, "Expense Alert", "You've exceeded your allocated budget for this month. Consider adjusting your spending habits to stay on track", Notification.Type.ALERT),
                new Notification(1, "Categorization Reminder", "You have unclassified expenses from last week. Categorize them to gain better insights into your spending patterns", Notification.Type.REMINDER),
                new Notification(2, "Week Insights", "Income: $NExpenses: $NStay mindful of your spending to maintain a balanced budget.", Notification.Type.WEEKLY_INSIGHT)
        };
    }

    public static DigitalWallet[] populateDigitalWalletData() {
        return new DigitalWallet[]{
                new DigitalWallet("Crypto Debit Card", "https://i.postimg.cc/Z57YQnh8/crypto-card.png", DigitalWallet.Type.CRYPTO),
                new DigitalWallet("Hardware Wallet", "https://i.postimg.cc/YqtpqYrN/hardware-wallet.png", DigitalWallet.Type.CRYPTO),
                new DigitalWallet("Software Wallet", "https://i.postimg.cc/Pq0Xt7JN/software-wallet.png", DigitalWallet.Type.CRYPTO),
                new DigitalWallet("Google Wallet", "https://i.postimg.cc/7L5Pr4xp/google-wallet.png", DigitalWallet.Type.DIGITAL),
                new DigitalWallet("Paypal", "https://i.postimg.cc/0Qm8rCKn/paypal-debit-card.png", DigitalWallet.Type.DIGITAL),
                new DigitalWallet("Venmo Debit Card", "https://i.postimg.cc/VvWmWLRb/venmo-debit-card.png", DigitalWallet.Type.DIGITAL),
                new DigitalWallet("Samsung Wallet","https://i.postimg.cc/yxH7RSFH/samsumg-wallet.png", DigitalWallet.Type.DIGITAL),
                new DigitalWallet("Credit Card","https://i.postimg.cc/T2gwPTrx/credit-card.png", DigitalWallet.Type.BANK),
                new DigitalWallet("Debit Card","https://i.postimg.cc/FFyr4D4s/debit-card.png", DigitalWallet.Type.BANK)
        };
    }

    public static UserDigitalWallet[] populateUserDigitalWalletData() {
        return new UserDigitalWallet[] {
                new UserDigitalWallet(1, "Crypto Debit Card", "CT8752-ABD36-PLK98-TGY21"),
                new UserDigitalWallet(1, "Hardware Wallet", ""),
                new UserDigitalWallet(2, "Venmo Debit Card", "4485 7298 1037 6621"),
                new UserDigitalWallet(2, "Credit Card", "5271 8943 2156 7798"),
                new UserDigitalWallet(1, "Software Wallet", "7a1b2c3d4e5f6a7b8c9d0e1f2a3b4c5d6")
        };
    }
}
