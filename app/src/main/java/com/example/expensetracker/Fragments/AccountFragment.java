package com.example.expensetracker.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.expensetracker.ExpenseTrackerDb.DAOs.TransactionDAO;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.UserDAO;
import com.example.expensetracker.ExpenseTrackerDb.Entities.User;
import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;
import com.example.expensetracker.FragmentContainerActivity;
import com.example.expensetracker.Preferences;
import com.example.expensetracker.R;
import com.example.expensetracker.databinding.FragmentAccountBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    private PieChart mExpensesPieChart;

    private TextView mWelcomeMsg;

    private TextView mBudgetAmount;
    private TextView mExpensesAmount;

    private ProgressBar mTransactionsProgressBar;

    private TransactionDAO transactionDAO;
    private UserDAO userDAO;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int statusBarColor = ContextCompat.getColor(requireContext(), R.color.white);
        ((FragmentContainerActivity) requireActivity()).getWindow().setStatusBarColor(statusBarColor);
        ((FragmentContainerActivity) requireActivity()).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        FragmentAccountBinding mAccountFragmentBinding = FragmentAccountBinding.inflate(inflater, container, false);
        View view = mAccountFragmentBinding.getRoot();

        initializeDatabase();

        mExpensesPieChart = mAccountFragmentBinding.transactionsPieChart;
        mWelcomeMsg = mAccountFragmentBinding.welcomeMsg;
        mBudgetAmount = mAccountFragmentBinding.budgetAmount;
        mExpensesAmount = mAccountFragmentBinding.expensesAmount;
        mTransactionsProgressBar = mAccountFragmentBinding.transactionsProgressBar;

        displayData();

        return view;
    }

    private void initializeDatabase() {
        ExpenseTrackerDatabase expenseTrackerDatabase = Room.databaseBuilder(
                requireContext(), ExpenseTrackerDatabase.class, ExpenseTrackerDatabase.DATABASE_NAME).allowMainThreadQueries().build();

        userDAO = expenseTrackerDatabase.userDAO();
        transactionDAO = expenseTrackerDatabase.transactionDAO();
    }

    private void displayData() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(Preferences.EXPENSE_TRACKER_PREFERENCES, MODE_PRIVATE);

        long currUserID = sharedPreferences.getLong(Preferences.USER_ID_KEY, -1);

        User currUser = userDAO.getUserById(currUserID);

        Double loggedInUserTotalExpenses = transactionDAO.getTotalMonthlyExpensesByUserID(currUserID);

        mWelcomeMsg.setText(getString(R.string.welcome_message, currUser.getUsername()));

        if (currUser.getBudget() == 0.0) {
            mBudgetAmount.setText("N/A");
        } else {
            mBudgetAmount.setText(String.valueOf(currUser.getBudget()));
        }

        mExpensesAmount.setText(getString(R.string.money_symbol, String.valueOf(loggedInUserTotalExpenses)));

        double expensesPercentage = (loggedInUserTotalExpenses / currUser.getBudget()) * 100;

        mTransactionsProgressBar.setMax(100);

        if (expensesPercentage <= 100) {
            mTransactionsProgressBar.setProgress((int) expensesPercentage);

            if (expensesPercentage <= 45) {
                mTransactionsProgressBar.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.progressGreen)));
            } else if (expensesPercentage <= 75) {
                mTransactionsProgressBar.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.progressYellow)));
            } else {
                mTransactionsProgressBar.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.progressRed)));
            }
        } else {
            mTransactionsProgressBar.setProgress(100);
            mTransactionsProgressBar.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.progressRed)));
        }

        ArrayList<PieEntry> expensesPieChartEntries = new ArrayList<>();
        expensesPieChartEntries.add(new PieEntry(80f, "Food"));
        expensesPieChartEntries.add(new PieEntry(90f, "Transportation"));
        expensesPieChartEntries.add(new PieEntry(75f, "Housing"));
        expensesPieChartEntries.add(new PieEntry(60f, "Entertainment"));

        float total = 80f + 90f + 75f + 60f;
        for (PieEntry entry : expensesPieChartEntries) {
            entry.setLabel(entry.getLabel() + " " + String.format("%.1f%%", (entry.getValue() / total) * 100));
        }

        PieDataSet expensesPieDataSet = new PieDataSet(expensesPieChartEntries, "Transactions");
        expensesPieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        expensesPieDataSet.setValueTextSize(12f);

        PieData expensesPieData = new PieData(expensesPieDataSet);
        mExpensesPieChart.setData(expensesPieData);

        mExpensesPieChart.getDescription().setEnabled(false);
        mExpensesPieChart.getLegend().setEnabled(false);
        mExpensesPieChart.setEntryLabelColor(R.color.black);
        mExpensesPieChart.setEntryLabelTextSize(10f);
        mExpensesPieChart.setRotationEnabled(false);

        mExpensesPieChart.invalidate();
    }
}