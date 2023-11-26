package com.example.expensetracker.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.expensetracker.Components.TransactionAdapter;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.TransactionDAO;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.UserDAO;
import com.example.expensetracker.ExpenseTrackerDb.Entities.Transaction;
import com.example.expensetracker.ExpenseTrackerDb.Entities.User;
import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;
import com.example.expensetracker.FragmentContainerActivity;
import com.example.expensetracker.Preferences;
import com.example.expensetracker.R;
import com.example.expensetracker.UserSettingsActivity;
import com.example.expensetracker.databinding.FragmentHomeBinding;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

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
    private RecyclerView mTransactionsRecyclerView;
    private TransactionDAO transactionDAO;
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
        int statusBarColor = ContextCompat.getColor(requireContext(), R.color.black);
        ((FragmentContainerActivity) requireActivity()).getWindow().setStatusBarColor(statusBarColor);
        ((FragmentContainerActivity) requireActivity()).getWindow().getDecorView().setSystemUiVisibility(0);

        FragmentHomeBinding mHomeFragmentBinding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = mHomeFragmentBinding.getRoot();

        initializeDatabase();

        mMonthsExpenses = mHomeFragmentBinding.monthsExpenses;
        mCurrentDate = mHomeFragmentBinding.currentDate;
        ImageView mSettingsImg = mHomeFragmentBinding.settingsImg;
        mBudget = mHomeFragmentBinding.budget;
        mTransactionsRecyclerView = mHomeFragmentBinding.expensesRecyclerView;
        mEmptyExpensesTextView = mHomeFragmentBinding.emptyExpensesTextView;
        TextView mSeeAllTransactionsTextView = mHomeFragmentBinding.seeAllTransactionsTextView;

        mSettingsImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), UserSettingsActivity.class));
            }
        });

        displayData();

        mSeeAllTransactionsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new TransactionsFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

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

        Double userMonthTotalExpensesAmount = transactionDAO.getTotalMonthlyExpensesByUserID(currUserID);

        try {
            LiveData<List<Transaction>> recentTransactionsLiveData = transactionDAO.getMonthMostRecentExpensesByUserID(currUserID);

            recentTransactionsLiveData.observe(getViewLifecycleOwner(), recentTransactionsList -> {
                if (recentTransactionsList != null && !recentTransactionsList.isEmpty()) {
                    if(recentTransactionsList.size() <= 4) {

                    }

                    mEmptyExpensesTextView.setVisibility(View.GONE);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext()) {
                        @Override
                        public boolean canScrollVertically() {
                            return false;
                        }
                    };

                    mTransactionsRecyclerView.setLayoutManager(layoutManager);
                    TransactionAdapter transactionAdapter = new TransactionAdapter(requireContext(), recentTransactionsList);
                    mTransactionsRecyclerView.setAdapter(transactionAdapter);

                    mMonthsExpenses.setText("$" + userMonthTotalExpensesAmount);
                    // Set up swipe gestures using ItemTouchHelper
                    ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                        @Override
                        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                            return false;
                        }

                        @Override
                        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                            int position = viewHolder.getBindingAdapterPosition();
                            Transaction transaction = transactionAdapter.getTransaction(position);

                            if (direction == ItemTouchHelper.LEFT) {
                                // Handle delete action
                                transactionDAO.deleteTransaction(transaction);
                                transactionAdapter.removeTransaction(position);
                                transactionAdapter.notifyItemRemoved(position);
                                Snackbar.make(mTransactionsRecyclerView, "Deleted: " + transaction.getTitle(), Snackbar.LENGTH_LONG)
                                        .setAction("Undo", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                recentTransactionsList.add(position, transaction);
                                                transactionDAO.insertTransaction(transaction);
                                                transactionAdapter.notifyItemInserted(position);
                                            }
                                        }).show();
                            }
                        }

                        // Additional method to customize swipe appearance
                        @Override
                        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(requireContext(), R.color.adminRed))
                                    .addSwipeLeftActionIcon(R.drawable.recycler_view_delete_swipe_icon)
                                    .create()
                                    .decorate();

                            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                        }
                    };

                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
                    itemTouchHelper.attachToRecyclerView(mTransactionsRecyclerView);

                } else {
                    // Set a height for mEmptyExpensesTextView here
                    int desiredHeightInPixels = getResources().getDimensionPixelSize(R.dimen.empty_expenses_text_height); // Define your desired height in resources

                    ViewGroup.LayoutParams params = mEmptyExpensesTextView.getLayoutParams();
                    params.height = desiredHeightInPixels;
                    mEmptyExpensesTextView.setLayoutParams(params);

                    // Hide or show based on your requirement
                    mEmptyExpensesTextView.setVisibility(View.VISIBLE); // or View.GONE based on your requirement

                    mTransactionsRecyclerView.setVisibility(View.GONE);
                    mMonthsExpenses.setText("$0");
                }
            });
        } catch (Exception e) {
            mTransactionsRecyclerView.setVisibility(View.GONE);
            mMonthsExpenses.setText("$0");
        }


        mCurrentDate.setText(currDate.format(formatter));
        mBudget.setText("$" + currUser.getBudget());
    }
}