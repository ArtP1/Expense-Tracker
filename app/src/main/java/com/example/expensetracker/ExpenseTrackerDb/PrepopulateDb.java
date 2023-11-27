package com.example.expensetracker.ExpenseTrackerDb;


import com.example.expensetracker.ExpenseTrackerDb.Entities.Category;
import com.example.expensetracker.ExpenseTrackerDb.Entities.Currency;
import com.example.expensetracker.ExpenseTrackerDb.Entities.Notification;
import com.example.expensetracker.ExpenseTrackerDb.Entities.PaymentMethod;
import com.example.expensetracker.ExpenseTrackerDb.Entities.Transaction;
import com.example.expensetracker.ExpenseTrackerDb.Entities.User;

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
                new PaymentMethod("Bank Transfer", "https://drive.google.com/file/d/1uwsOItTkjbLPx4Ul9NznLKldok7xHIE_/view?usp=sharing"),
                new PaymentMethod("Gift Card", "https://drive.google.com/file/d/1WePETXcU2MgqBR5GInO5gAMBgag6F1qr/view?usp=sharing"),
                new PaymentMethod("Mobile Wallet", "https://drive.google.com/file/d/1GWtajj34gEhCaQtqKRdyofzXE4KanQc3/view?usp=sharing"),
                new PaymentMethod("Crypto", "https://drive.google.com/file/d/1k-6fhJ3HUZWXMOfQMng1uZaZLqHTbT5u/view?usp=sharing"),
                new PaymentMethod("Check", "https://drive.google.com/file/d/1IsRFFKQNGfrOQsU0UWi-pyz-GGV4DrZu/view?usp=sharing"),
                new PaymentMethod("Direct Deposit", "https://drive.google.com/file/d/1xxXj3xPASBvUB9qqgMt71FF2Aw6SG7Xi/view?usp=sharing"),
                new PaymentMethod("PayPal", "https://drive.google.com/file/d/14F8Jm2xt28U4yFqhmc2MlLrJ27pxtgNC/view?usp=sharing"),
                new PaymentMethod("Credit Card", "https://drive.google.com/file/d/19uFFR3sY1i7qnJK8gmEDsFoRtvP24Y6h/view?usp=sharing"),
                new PaymentMethod("Debit Card", "https://drive.google.com/file/d/19dcubR0MLr-0uceZSd-HyNQ9Wi9YJskR/view?usp=sharing"),
                new PaymentMethod("Cash", "https://drive.google.com/file/d/1k82u7wdEzt7KKuWdQfZ-Iwt3Re737Iww/view?usp=sharing")
        };
    }

    public static Category[] populateCategoryData() {
        return new Category[]{
                new Category("Housing", "#FF0000", "https://drive.google.com/file/d/1v7OP-_817E4oUsjDqeoRJmd5E0TVj728/view?usp=sharing"),
                new Category("Transportation", "#FF0000", "https://drive.google.com/file/d/1SNMYzMbbOB7ICLcpV4HsP2rHGJtLhI3E/view?usp=sharing"),
                new Category("Dining", "#FF0000", "https://drive.google.com/file/d/1YLz_vCQUTpi9oUzUKWb6cTZApbEpahnh/view?usp=sharing"),
                new Category("Healthcare", "#FF0000", "https://drive.google.com/file/d/18Jq6zC36fuhY-4sBTkJBobbNW2ltpMNJ/view?usp=sharing"),
                new Category("Entertainment", "#FF0000", "https://drive.google.com/file/d/1O309Dh3gMegp22QAmkKDkcXEb_I1APpc/view?usp=sharing"),
                new Category("Personal Care", "#FF0000", "https://drive.google.com/file/d/1o57aQBGGynUXRDwlZbCE_uD5mh18mCiv/view?usp=sharing"),
                new Category("Shopping", "#FF0000", "https://drive.google.com/file/d/1B-l6ihbEfcEo96N_0AZdSAlDrIfu5geN/view?usp=sharing"),
                new Category("Travel", "#FF0000", "https://drive.google.com/file/d/16snpOtXCuhyFtf9YXNKMdUUnW1pjVH7c/view?usp=sharing"),
                new Category("Education", "#FF0000", "https://drive.google.com/file/d/1lcIVh3nKrxWDRJMkecmncTDBS1AqLCmn/view?usp=sharing"),
                new Category("Taxes", "#FF0000", "https://drive.google.com/file/d/1klXRiB2B5JJ2B0M3kA--7MoYo5EaD10b/view?usp=sharing"),
                new Category("Debt", "#FF0000", "https://drive.google.com/file/d/1ycfztcRgcevLigPr_9Qv9ajpjpF9DD1r/view?usp=sharing"),
                new Category("Investment", "#FF0000", "https://drive.google.com/file/d/1uAfUs4poJPPsJW-W_xR2VgWBpIUfNPU-/view?usp=sharing"),
                new Category("Freelancing", "#FF0000", "https://drive.google.com/file/d/1azxdhqTRo3zN1bE9CQLQCOTzeTOT2o5D/view?usp=sharing"),
                new Category("Salary", "#FF0000", "https://drive.google.com/file/d/1o2_HK_TGGqjubAp0gghWWLrPaijFgmQ3/view?usp=sharing"),
                new Category("Utilities", "#FF0000", "")
        };
    }

    public static Transaction[] populateTransactionData() {
        LocalDate pastDate1 = LocalDate.of(2023, 10, 15);
        LocalDate pastDate2 = LocalDate.of(2023, 9, 28);
        LocalDate pastDate3 = LocalDate.of(2023, 9, 30);

        return new Transaction[]{
                new Transaction(1, "Dining", "Crypto", 16.75, "Popeyes", pastDate1, "Purchased lunch for the day", "590 Auto Center Dr Unit 1A", Transaction.Type.EXPENSE),
                new Transaction(1, "Salary", "Direct Deposit", 3000.0, "Monthly Salary", pastDate3, "November salary", "Your Company Address", Transaction.Type.EARNING),
                new Transaction(1, "Healthcare", "Check", 70.0, "Fuel", "Refilled gas for the car", "853 Abbott St", Transaction.Type.EXPENSE),
                new Transaction(1, "Debt", "Crypto", 10000.0, "Student Loans", "", "", Transaction.Type.EXPENSE),
                new Transaction(1, "Freelancing", "Bank Transfer", 500.0, "Freelance Work", "Web design project", "Client Address", Transaction.Type.EARNING),
                new Transaction(1, "Personal Care", "Credit Card", 25.0, "Movie Night", pastDate2, "Tickets for movie night", "350 Northridge Shopping Ctr", Transaction.Type.EXPENSE),
                new Transaction(1, "Investment", "Direct Deposit", 200.0, "Stock Dividend", "Quarterly dividend payment", "Investment Firm Address", Transaction.Type.EARNING),
                new Transaction(1, "Housing", "Credit Card", 80.0, "Supermarket", "Weekly grocery shopping", "123 Main St", Transaction.Type.EXPENSE),
                new Transaction(1, "Utilities", "Bank Transfer", 150.0, "Electricity, Water, Gas", "Monthly utility bills", "456 Elm St", Transaction.Type.EXPENSE),
                new Transaction(1, "Entertainment", "Cash", 40.0, "Concert Tickets", "Concert tickets for the weekend", "789 Oak St", Transaction.Type.EXPENSE),
                new Transaction(1, "Transportation", "Debit Card", 30.0, "Public Transit", "Public transportation fare", "101 Pine St", Transaction.Type.EXPENSE),
                new Transaction(1, "Dining", "Cash", 50.0, "Restaurant Dinner", "Dinner with friends", "246 Maple St", Transaction.Type.EXPENSE)
        };
    }

    public static Notification[] populateNotificationData() {
        return new Notification[]{
                new Notification(1, "Expense Alert", "You've exceeded your allocated budget for this month. Consider adjusting your spending habits to stay on track", Notification.Type.ALERT),
                new Notification(1, "Categorization Reminder", "You have unclassified expenses from last week. Categorize them to gain better insights into your spending patterns", Notification.Type.REMINDER),
                new Notification(2, "Week Insights", "Income: $N\nExpenses: $N\nStay mindful of your spending to maintain a balanced budget.", Notification.Type.WEEKLY_INSIGHT)
        };
    }
}
