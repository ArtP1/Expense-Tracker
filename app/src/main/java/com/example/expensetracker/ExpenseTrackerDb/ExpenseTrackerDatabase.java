package com.example.expensetracker.ExpenseTrackerDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.expensetracker.ExpenseTrackerDb.DAOs.CategoryDAO;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.CurrencyDAO;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.DigitalWalletDAO;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.DigitalWalletTransactionDAO;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.NotificationDAO;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.PaymentMethodDAO;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.PhysicalTransactionDAO;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.UserDAO;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.UserDigitalWalletDAO;
import com.example.expensetracker.ExpenseTrackerDb.Entities.Category;
import com.example.expensetracker.ExpenseTrackerDb.Entities.Currency;
import com.example.expensetracker.ExpenseTrackerDb.Entities.DigitalWallet;
import com.example.expensetracker.ExpenseTrackerDb.Entities.DigitalWalletTransaction;
import com.example.expensetracker.ExpenseTrackerDb.Entities.Notification;
import com.example.expensetracker.ExpenseTrackerDb.Entities.PaymentMethod;
import com.example.expensetracker.ExpenseTrackerDb.Entities.PhysicalTransaction;
import com.example.expensetracker.ExpenseTrackerDb.Entities.User;
import com.example.expensetracker.ExpenseTrackerDb.Entities.UserDigitalWallet;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {PhysicalTransaction.class, DigitalWalletTransaction.class, User.class, Category.class, PaymentMethod.class, Currency.class, Notification.class, DigitalWallet.class, UserDigitalWallet.class}, version = 1, exportSchema = false)
public abstract class ExpenseTrackerDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "expense_tracker_db";
    public static final String TRANSACTION_TABLE = "transaction_table";
    public static final String DIGITAL_TRANSACTION_TABLE = "digital_transaction_table";
    public static final String PHYSICAL_TRANSACTION_TABLE = "physical_transaction_table";
    public static final String USER_TABLE = "user_table";
    public static final String CATEGORY_TABLE = "category_table";
    public static final String PAYMENT_METHOD_TABLE = "payment_method_table";
    public static final String CURRENCY_TABLE = "currency_table";
    public static final String NOTIFICATION_TABLE = "notification_table";
    public static final String DIGITAL_WALLET_TABLE = "digital_wallet_table";
    public static final String USER_DIGITAL_WALLET_TABLE = "user_digital_wallet_table";

    private static volatile ExpenseTrackerDatabase dbInstance; // Singleton instance var
    private static final Object LOCK = new Object();

    public abstract UserDAO userDAO();

    public abstract PhysicalTransactionDAO physicalTransactionDAO();
    public abstract DigitalWalletTransactionDAO digitalWalletTransactionDAO();

    public abstract PaymentMethodDAO paymentMethodDAO();

    public abstract CategoryDAO categoryDAO();

    public abstract CurrencyDAO currencyDAO();

    public abstract NotificationDAO notificationDAO();

    public abstract DigitalWalletDAO digitalWalletDAO();

    public abstract UserDigitalWalletDAO userDigitalWalletDAO();


    public static ExpenseTrackerDatabase getInstance(Context context) {
        if (dbInstance == null) {
            synchronized (LOCK) {
                if (dbInstance == null) {
                    dbInstance = Room.databaseBuilder(context.getApplicationContext(), ExpenseTrackerDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return dbInstance;
    }

    public static void initializeDatabase(final Context context) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                // Get database instance
                ExpenseTrackerDatabase db = getInstance(context);

                // Populate tables with predefined data
                db.runInTransaction(() -> db.currencyDAO().insertAllCurrencies(PrepopulateDb.populateCurrencyData()));
                db.runInTransaction(() -> db.userDAO().insertAllUsers(PrepopulateDb.populateUsersData()));
                db.runInTransaction(() -> db.notificationDAO().insertAllNotifications(PrepopulateDb.populateNotificationData()));
                db.runInTransaction(() -> db.paymentMethodDAO().insertAllPaymentMethods(PrepopulateDb.populatePaymentMethodData()));
                db.runInTransaction(() -> db.categoryDAO().insertAllCategories(PrepopulateDb.populateCategoryData()));
                db.runInTransaction(() -> db.digitalWalletDAO().insertAllDigitalWallets(PrepopulateDb.populateDigitalWalletData()));
                db.runInTransaction(() -> db.userDigitalWalletDAO().insertAllUserDigitalWallets(PrepopulateDb.populateUserDigitalWalletData()));
                db.runInTransaction(() -> db.physicalTransactionDAO().insertAllPhysicalTransactions(PrepopulateDb.populatePhysicalTransactionData()));
                db.runInTransaction(() -> db.digitalWalletTransactionDAO().insertAllDigitalWalletTransactions(PrepopulateDb.populateDigitalWalletTransactionData()));

                executor.shutdown();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                executor.shutdown();
            }
        });
    }
}
