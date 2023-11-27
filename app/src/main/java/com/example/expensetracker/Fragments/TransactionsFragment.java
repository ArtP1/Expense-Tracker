package com.example.expensetracker.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;
import com.example.expensetracker.Preferences;
import com.example.expensetracker.R;
import com.example.expensetracker.databinding.FragmentTransactionsBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionsFragment extends Fragment {

    private TextView mIncomeAmountTextView;
    private TextView mExpenseAmountTextView;

    private TextView mEmptyExpensesTextView;
    private RecyclerView mAllTransactionsRecyclerView;

    private BarChart mBarChart;

    private CardView mAllTransactionsCardView;

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
        requireActivity().getWindow().setStatusBarColor(statusBarColor);
        requireActivity().getWindow().getDecorView().setSystemUiVisibility(0);

        FragmentTransactionsBinding mTransactionsFragmentBinding = FragmentTransactionsBinding.inflate(inflater, container, false);
        View view = mTransactionsFragmentBinding.getRoot();

        initializeDatabase();

        mIncomeAmountTextView = mTransactionsFragmentBinding.incomeAmountTextView;
        mExpenseAmountTextView = mTransactionsFragmentBinding.expenseAmountTextView;
        mAllTransactionsRecyclerView = mTransactionsFragmentBinding.allTransactionsRecyclerView;
        mEmptyExpensesTextView = mTransactionsFragmentBinding.emptyExpensesTextView;
        mAllTransactionsCardView = mTransactionsFragmentBinding.allTransactionsCardView;
        mBarChart = mTransactionsFragmentBinding.barChart;

        displayData();

        return view;
    }

    private String[] months() {
        return new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
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

        // Fetch data for the last 6 months (adjust logic as per your requirements)
        List<Float> incomeList = new ArrayList<>();
        List<Float> expenseList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);

        for (int i = 5; i >= 0; i--) {
            // Calculate the month and year for the previous months
            calendar.set(Calendar.YEAR, currentYear);
            calendar.set(Calendar.MONTH, currentMonth - i); // Subtract 'i' months
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1; // Calendar months are zero-based

            // Adjust for negative months
            if (month <= 0) {
                month += 12;
                year -= 1;
            }

            String yearMonth = String.format(Locale.US, "%04d-%02d", year, month);

            double income = transactionDAO.getMonthlyIncomeForMonth(currUserID, yearMonth);
            double expense = transactionDAO.getMonthlyExpenseForMonth(currUserID, yearMonth);
            incomeList.add((float) income);
            expenseList.add((float) expense);
        }


// Reverse lists to maintain correct chronological order
        Collections.reverse(incomeList);
        Collections.reverse(expenseList);


        // Inside onCreateView or another appropriate method in your Fragment

        ArrayList<BarEntry> incomeEntries = new ArrayList<>();
        ArrayList<BarEntry> expenseEntries = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            incomeEntries.add(new BarEntry(i, incomeList.get(i)));
            expenseEntries.add(new BarEntry(i, expenseList.get(i)));
        }

        BarDataSet incomeDataSet = new BarDataSet(incomeEntries, "Income");
        incomeDataSet.setColor(Color.GREEN); // Set color for income bars
        incomeDataSet.setDrawValues(false);

        BarDataSet expenseDataSet = new BarDataSet(expenseEntries, "Expense");
        expenseDataSet.setColor(Color.RED);
        expenseDataSet.setDrawValues(false);// Set color for expense bars

        // Set position and width for the bars
        float groupSpace = 0.2f; // Space between groups of bars
        float barSpace = 0.1f; // Space between individual bars in a group
        float barWidth = 0.35f; // Width of each bar

        BarData barData = new BarData(incomeDataSet, expenseDataSet);
        barData.setBarWidth(barWidth); // Adjust bar width as needed

        barData.groupBars(0, groupSpace, barSpace);
        mBarChart.setData(barData);

        mBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(months())); // Set month labels on x-axis
        mBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mBarChart.getXAxis().setDrawGridLines(false); // Disable drawing vertical grid lines on the X-axis
        mBarChart.getDescription().setEnabled(false);
        mBarChart.setDrawGridBackground(false); // Disable drawing the background grid
        mBarChart.getAxisRight().setEnabled(false); // Disable the right Y-axis


        mBarChart.getAxisLeft().setDrawAxisLine(false); // Make Y-axis line invisible
        mBarChart.getAxisLeft().setDrawGridLines(true); // Enable drawing horizontal grid lines
        mBarChart.getAxisLeft().setGridColor(Color.LTGRAY); // Set color for grid lines
        mBarChart.getAxisLeft().setGridLineWidth(1f); // Set width for grid lines
        mBarChart.getAxisLeft().setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART); // Place Y-axis text outside the chart
        mBarChart.getAxisLeft().setTextColor(Color.BLACK); // Set the text color for Y-axis labels

        mBarChart.getLegend().setEnabled(true); // Show legend
        mBarChart.getLegend().setTextColor(Color.BLACK); // Set legend text color
        mBarChart.getLegend().setTextSize(12f); // Set legend text size

        // Set legend at the top-right
        mBarChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        mBarChart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        mBarChart.getLegend().setOrientation(Legend.LegendOrientation.HORIZONTAL);
        mBarChart.getLegend().setDrawInside(false);

        // Adjust offsets to move the legend closer to the chart
        mBarChart.setExtraTopOffset(10f);
        mBarChart.setExtraRightOffset(10f); // Adjust as needed

        // Ensure proper chart layout
        mBarChart.setFitBars(true);
        mBarChart.setExtraBottomOffset(10f);

        // Refresh the chart
        mBarChart.invalidate();


        mIncomeAmountTextView.setText("$" + transactionDAO.getTotalMonthlyEarningsByUserID(currUserID));
        mExpenseAmountTextView.setText("$" + transactionDAO.getTotalMonthlyExpensesByUserID(currUserID));

        try {
            LiveData<List<Transaction>> allUserTransactionsLiveData = transactionDAO.getMonthMostRecentTransactionsByUserID(currUserID);

            allUserTransactionsLiveData.observe(getViewLifecycleOwner(), userTransactions -> {
                if (userTransactions != null && !userTransactions.isEmpty()) {
                    mEmptyExpensesTextView.setVisibility(View.GONE);

                    if (userTransactions.size() <= 4) {
                        // Set a specific height to the CardView when list size is less than or equal to 4
                        ViewGroup.LayoutParams layoutParams = mAllTransactionsCardView.getLayoutParams();
                        layoutParams.height = getResources().getDimensionPixelSize(R.dimen.recent_expenses_cardview_height);
                        mAllTransactionsCardView.setLayoutParams(layoutParams);
                    } else {
                        // Reset the height to default or any other desired height when list size is greater than 4
                        ViewGroup.LayoutParams layoutParams = mAllTransactionsCardView.getLayoutParams();
                        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT; // or set a default height
                        mAllTransactionsCardView.setLayoutParams(layoutParams);
                    }

                    LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext()) {
                        @Override
                        public boolean canScrollVertically() {
                            return false;
                        }
                    };

                    mAllTransactionsRecyclerView.setLayoutManager(layoutManager);
                    TransactionAdapter transactionAdapter = new TransactionAdapter(requireContext(), userTransactions);
                    mAllTransactionsRecyclerView.setAdapter(new TransactionAdapter(requireContext(), userTransactions));

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

                                Snackbar.make(mAllTransactionsRecyclerView, "Deleted: " + transaction.getTitle(), Snackbar.LENGTH_LONG)
                                        .setAction("Undo", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                userTransactions.add(position, transaction);
                                                transactionDAO.insertTransaction(transaction);

                                                if (transaction.getTransType().equals(Transaction.Type.EXPENSE)) {
                                                    mExpenseAmountTextView.setText("$" + transactionDAO.getTotalMonthlyExpensesByUserID(currUserID));
                                                } else if (transaction.getTransType().equals(Transaction.Type.EARNING)) {
                                                    mIncomeAmountTextView.setText("$" + transactionDAO.getTotalMonthlyEarningsByUserID(currUserID));
                                                }
                                                transactionAdapter.notifyItemInserted(position);
                                            }
                                        }).show();

                                if (transaction.getTransType().equals(Transaction.Type.EXPENSE)) {
                                    mExpenseAmountTextView.setText("$" + transactionDAO.getTotalMonthlyExpensesByUserID(currUserID));
                                } else if (transaction.getTransType().equals(Transaction.Type.EARNING)) {
                                    mIncomeAmountTextView.setText("$" + transactionDAO.getTotalMonthlyEarningsByUserID(currUserID));
                                }
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
                    itemTouchHelper.attachToRecyclerView(mAllTransactionsRecyclerView);
                } else {
                    mEmptyExpensesTextView.setVisibility(View.VISIBLE); // or View.GONE based on your requirement

                    mAllTransactionsRecyclerView.setVisibility(View.GONE);
                }
            });
        } catch (Exception e) {
            mAllTransactionsRecyclerView.setVisibility(View.GONE);
            mEmptyExpensesTextView.setVisibility(View.VISIBLE);
            e.printStackTrace();
        }
    }
}

