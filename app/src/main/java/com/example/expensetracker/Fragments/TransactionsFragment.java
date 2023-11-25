package com.example.expensetracker.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.expensetracker.Components.CategoryTransactionAdapter;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.TransactionDAO;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.UserDAO;
import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;
import com.example.expensetracker.ExpenseTrackerDb.Models.TransactionCategoryWithAmount;
import com.example.expensetracker.FragmentContainerActivity;
import com.example.expensetracker.Preferences;
import com.example.expensetracker.R;
import com.example.expensetracker.databinding.FragmentTransactionsBinding;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionsFragment extends Fragment {

    private TextView mIncomeAmountTextView;
    private TextView mExpenseAmountTextView;

    private TextView mEmptyExpensesTextView;
    private RecyclerView mCategoryTransactionsRecyclerView;

    private TransactionDAO transactionDAO;
    private UserDAO userDAO;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TransactionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExpensesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransactionsFragment newInstance(String param1, String param2) {
        TransactionsFragment fragment = new TransactionsFragment();
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
        int statusBarColor = ContextCompat.getColor(requireContext(), R.color.black);
        ((FragmentContainerActivity) requireActivity()).getWindow().setStatusBarColor(statusBarColor);
        ((FragmentContainerActivity) requireActivity()).getWindow().getDecorView().setSystemUiVisibility(0);

        FragmentTransactionsBinding mTransactionsFragmentBinding = FragmentTransactionsBinding.inflate(inflater, container, false);
        View view = mTransactionsFragmentBinding.getRoot();

        initializeDatabase();

        mIncomeAmountTextView = mTransactionsFragmentBinding.incomeAmountTextView;
        mExpenseAmountTextView = mTransactionsFragmentBinding.expenseAmountTextView;
        mCategoryTransactionsRecyclerView = mTransactionsFragmentBinding.transactionsCategoriesRecyclerView;
        mEmptyExpensesTextView = mTransactionsFragmentBinding.emptyExpensesTextView;

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

        double userMonthTotalExpensesAmount = transactionDAO.getTotalMonthlyExpensesByUserID(currUserID);
        double userMonthTotalEarningsAmount = transactionDAO.getTotalMonthlyEarningsByUserID(currUserID);

        mIncomeAmountTextView.setText("$" + userMonthTotalEarningsAmount);
        mExpenseAmountTextView.setText("$" + userMonthTotalExpensesAmount);

        try {
            LiveData<List<TransactionCategoryWithAmount>> categoryWithAmountLiveData = transactionDAO.getExpenseCategoriesWithAmount(currUserID);

            categoryWithAmountLiveData.observe(getViewLifecycleOwner(), transactionCategoryWithAmountList -> {
                if(transactionCategoryWithAmountList != null && !transactionCategoryWithAmountList.isEmpty()) {
                    mEmptyExpensesTextView.setVisibility(View.GONE);

                    mCategoryTransactionsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

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
}

//new Transaction(1, "Groceries", "Credit Card", 55.0, "Local Supermarket", pastDate4, "Weekly grocery shopping", "123 Main Street", Transaction.Type.EXPENSE)
