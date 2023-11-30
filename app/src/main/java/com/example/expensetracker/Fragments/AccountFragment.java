package com.example.expensetracker.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.expensetracker.Components.CategoryTransactionAdapter;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.PhysicalTransactionDAO;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.UserDAO;
import com.example.expensetracker.ExpenseTrackerDb.Entities.User;
import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;
import com.example.expensetracker.ExpenseTrackerDb.Models.TransactionCategoryWithAmount;
import com.example.expensetracker.MainActivity;
import com.example.expensetracker.Preferences;
import com.example.expensetracker.R;
import com.example.expensetracker.databinding.FragmentAccountBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {
    private SharedPreferences sharedPreferences;

    private PieChart mExpensesPieChart;

    private TextView mWelcomeMsg;

    private TextView mBudgetAmount;
    private TextView mEmptyExpensesTextView;

    private Button mDeletePermanentlyBtn;
    private TextView mExpensesAmount;

    private ProgressBar mTransactionsProgressBar;

    private RecyclerView mCategoryTransactionsRecyclerView;

    private PhysicalTransactionDAO physicalTransactionDAO;
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
        requireActivity().getWindow().setStatusBarColor(statusBarColor);
        requireActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        this.sharedPreferences = requireActivity().getSharedPreferences(Preferences.EXPENSE_TRACKER_PREFERENCES, MODE_PRIVATE);

        FragmentAccountBinding mAccountFragmentBinding = FragmentAccountBinding.inflate(inflater, container, false);
        View view = mAccountFragmentBinding.getRoot();

        initializeDatabase();

        mExpensesPieChart = mAccountFragmentBinding.transactionsPieChart;
        mWelcomeMsg = mAccountFragmentBinding.welcomeMsg;
        mBudgetAmount = mAccountFragmentBinding.budgetAmount;
        mExpensesAmount = mAccountFragmentBinding.expensesAmount;
        mTransactionsProgressBar = mAccountFragmentBinding.transactionsProgressBar;
        mCategoryTransactionsRecyclerView = mAccountFragmentBinding.transactionsCategoriesRecyclerView;
        mEmptyExpensesTextView = mAccountFragmentBinding.emptyExpensesTextView;
        mDeletePermanentlyBtn = mAccountFragmentBinding.deletePermanentlyBtn;

        displayData();

        mDeletePermanentlyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog();
            }
        });
        return view;
    }

    private void initializeDatabase() {
        ExpenseTrackerDatabase expenseTrackerDatabase = Room.databaseBuilder(
                requireContext(), ExpenseTrackerDatabase.class, ExpenseTrackerDatabase.DATABASE_NAME).allowMainThreadQueries().build();

        this.userDAO = expenseTrackerDatabase.userDAO();
        this.physicalTransactionDAO = expenseTrackerDatabase.physicalTransactionDAO();
    }

    private void displayData() {
        long currUserID = sharedPreferences.getLong(Preferences.USER_ID_KEY, -1);

        User currUser = userDAO.getUserById(currUserID);

        Double loggedInUserTotalExpenses = physicalTransactionDAO.getTotalMonthlyExpensesByUserID(currUserID);

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

        try {
            LiveData<List<TransactionCategoryWithAmount>> categoryWithAmountLiveData = physicalTransactionDAO.getExpenseCategoriesWithAmount(currUserID);

            categoryWithAmountLiveData.observe(getViewLifecycleOwner(), transactionCategoryWithAmountList -> {
                if (transactionCategoryWithAmountList != null && !transactionCategoryWithAmountList.isEmpty()) {
                    mEmptyExpensesTextView.setVisibility(View.GONE);

                    setPieChart(transactionCategoryWithAmountList);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext()) {
                        @Override
                        public boolean canScrollVertically() {
                            return false;
                        }
                    };

                    mCategoryTransactionsRecyclerView.setLayoutManager(layoutManager);

                    mCategoryTransactionsRecyclerView.setAdapter(new CategoryTransactionAdapter(requireContext(), transactionCategoryWithAmountList));

                } else {
                    mCategoryTransactionsRecyclerView.setVisibility(View.GONE);
                }
            });
        } catch (Exception e) {
            mEmptyExpensesTextView.setVisibility(View.GONE);
            e.printStackTrace();
        }
    }

    private void setPieChart(List<TransactionCategoryWithAmount> transactionCategoryWithAmountList) {
        ArrayList<PieEntry> expensesPieChartEntries = new ArrayList<>();

        // Clear previous entries
        expensesPieChartEntries.clear();

        double totalAmount = 0.0;
        for (TransactionCategoryWithAmount transaction : transactionCategoryWithAmountList) {
            totalAmount += transaction.getTotal_amount();
        }

        // Populate entries with normalized values
        for (TransactionCategoryWithAmount transaction : transactionCategoryWithAmountList) {
            // Calculate the percentage of each category relative to the total
            float percentage = (float) ((transaction.getTotal_amount() / totalAmount) * 100);
            expensesPieChartEntries.add(new PieEntry(percentage, transaction.getCategory_name()));
        }

        // Set up PieChart with new data
        PieDataSet expensesPieDataSet = new PieDataSet(expensesPieChartEntries, "");
        expensesPieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        expensesPieDataSet.setValueTextSize(12f);
        expensesPieDataSet.setValueTextColor(Color.BLACK); // Ensure values are visible

        PieData expensesPieData = new PieData(expensesPieDataSet);
        expensesPieData.setValueFormatter(new PercentFormatter()); // Show percentage values
        expensesPieData.setValueTextColor(Color.WHITE); // Set value text color

        mExpensesPieChart.setData(expensesPieData);

        // Customize chart appearance and behavior
        Description description = new Description();
        description.setText(""); // Clear description
        mExpensesPieChart.setDescription(description);
        mExpensesPieChart.setDrawEntryLabels(false); // Hide entry labels

        Legend legend = mExpensesPieChart.getLegend();
        legend.setEnabled(true);
        legend.setTextSize(10f);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        mExpensesPieChart.setRotationEnabled(false);
        mExpensesPieChart.setHoleRadius(60f); // Set hole radius as needed
        mExpensesPieChart.setTransparentCircleRadius(35f); // Set transparent circle radius

        mExpensesPieChart.invalidate();
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Confirm Deletion")
                .setMessage("Are you sure you want to delete your data permanently?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // User confirmed, proceed with deletion
                        deleteUserData();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteUserData() {
        long currUserID = sharedPreferences.getLong(Preferences.USER_ID_KEY, -1);

        sharedPreferences.edit().clear().apply();

        userDAO.deleteUser(userDAO.getUserById(currUserID));

        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        getActivity().finish();
    }

}