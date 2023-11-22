package com.example.expensetracker.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.expensetracker.Components.ExpenseAdapter;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.ExpenseDAO;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.UserDAO;
import com.example.expensetracker.ExpenseTrackerDb.Entities.Expense;
import com.example.expensetracker.ExpenseTrackerDb.Entities.User;
import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;
import com.example.expensetracker.Preferences;
import com.example.expensetracker.UserSettingsActivity;
import com.example.expensetracker.databinding.FragmentHomeBinding;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private TextView mMonthsExpenses;
    private TextView mCurrentDate;
    private TextView mEmptyExpensesTextView;
    private TextView mBudget;
    private RecyclerView mExpensesRecyclerView;
    private ImageView mSettingsImg;
    private ExpenseDAO expenseDAO;
    private UserDAO userDAO;

    private final LocalDate currDate = LocalDate.now();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, d MMM", Locale.ENGLISH);

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        FragmentHomeBinding mHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = mHomeBinding.getRoot();

        initializeDatabase();

        mMonthsExpenses = mHomeBinding.monthsExpenses;
        mCurrentDate = mHomeBinding.currentDate;
        mSettingsImg = mHomeBinding.settingsImg;
        mBudget = mHomeBinding.budget;
        mExpensesRecyclerView = mHomeBinding.expensesRecyclerView;

        mEmptyExpensesTextView = mHomeBinding.emptyExpensesTextView;

        mSettingsImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), UserSettingsActivity.class));
            }
        });

        displayData();

        return view;
    }

    private void initializeDatabase() {
        ExpenseTrackerDatabase expenseTrackerDatabase = Room.databaseBuilder(
                requireContext(), ExpenseTrackerDatabase.class, ExpenseTrackerDatabase.DATABASE_NAME).allowMainThreadQueries().build();

        userDAO = expenseTrackerDatabase.userDAO();
        expenseDAO = expenseTrackerDatabase.expenseDAO();
    }

    private void displayData() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(Preferences.EXPENSE_TRACKER_PREFERENCES, MODE_PRIVATE);

        long currUserID = sharedPreferences.getLong(Preferences.USER_ID_KEY, -1);

        User currUser = userDAO.getUserById(currUserID);

        Double loggedInUserTotalExpenses = expenseDAO.getTotalExpensesByUser(currUserID);

        List<Expense> expenseList = expenseDAO.getExpensesByUser(currUserID);

        if (!expenseList.isEmpty()) {
            mEmptyExpensesTextView.setVisibility(View.GONE);

            mExpensesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

            mExpensesRecyclerView.setAdapter(new ExpenseAdapter(requireContext(), expenseList));
            mMonthsExpenses.setText("$" + Double.toString(loggedInUserTotalExpenses));
        } else {
            mExpensesRecyclerView.setVisibility(View.GONE);
            mMonthsExpenses.setText("$0");
        }

        mCurrentDate.setText(currDate.format(formatter));
        mBudget.setText("$" + currUser.getBudget());
    }
}