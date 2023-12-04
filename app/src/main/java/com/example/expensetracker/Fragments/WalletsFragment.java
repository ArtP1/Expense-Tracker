package com.example.expensetracker.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.example.expensetracker.Components.TransactionAdapter;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.TransactionDAO;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.UserDAO;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.UserDigitalWalletDAO;
import com.example.expensetracker.ExpenseTrackerDb.Entities.Transaction;
import com.example.expensetracker.ExpenseTrackerDb.Entities.User;
import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;
import com.example.expensetracker.ExpenseTrackerDb.Models.UserDigitalWalletWithInformation;
import com.example.expensetracker.Preferences;
import com.example.expensetracker.R;
import com.example.expensetracker.databinding.FragmentWalletsBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WalletsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WalletsFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private RecyclerView mRecentWalletTransactionsRecyclerView;
    private CardView mRecentWalletTransactionsCardView;
    private TextView mWalletUserFirstNameTextView;
    private TextView mEmptyTransactionsTextView;
    private TextView mCardNumberOrToken;
    private TextView mNoUserWalletsTextView;
    private SwitchCompat mSetDigitalWalletDefault;

    private ImageView mDigitalWalletImageView;
    private TextView mWalletTypeTextView;

    private TextView mUserWalletNumberOrTokenPlaceholderTextView;
    private TransactionDAO transactionDAO;
    private UserDAO userDAO;
    private UserDigitalWalletDAO userDigitalWalletDAO;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WalletsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WalletsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WalletsFragment newInstance(String param1, String param2) {
        WalletsFragment fragment = new WalletsFragment();
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

        FragmentWalletsBinding mWalletFragmentBinding = FragmentWalletsBinding.inflate(inflater, container, false);
        View view = mWalletFragmentBinding.getRoot();

        this.sharedPreferences = requireActivity().getSharedPreferences(Preferences.EXPENSE_TRACKER_PREFERENCES, MODE_PRIVATE);
        long currUserID = sharedPreferences.getLong(Preferences.USER_ID_KEY, -1);

        initializeDatabase();

        User currUser = userDAO.getUserById(currUserID);

        this.mRecentWalletTransactionsRecyclerView = mWalletFragmentBinding.digitalRecentWalletTransactionsRecyclerView;
        this.mRecentWalletTransactionsCardView = mWalletFragmentBinding.recentWalletTransactionsCardView;
        this.mEmptyTransactionsTextView = mWalletFragmentBinding.emptyTransactionsTextView;
        this.mDigitalWalletImageView = mWalletFragmentBinding.digitalWalletImgView;
        this.mWalletUserFirstNameTextView = mWalletFragmentBinding.walletUserFirstNameTextView;
        this.mCardNumberOrToken = mWalletFragmentBinding.cardNumberOrToken;
        this.mWalletTypeTextView = mWalletFragmentBinding.walletTypeTextView;
        this.mNoUserWalletsTextView = mWalletFragmentBinding.noUserWalletsTextView;
        this.mUserWalletNumberOrTokenPlaceholderTextView = mWalletFragmentBinding.userWalletNumberOrTokenPlaceholderTextView;
        this.mSetDigitalWalletDefault = mWalletFragmentBinding.setDigitalWalletDefault;

        UserDigitalWalletWithInformation userDigitalWalletWithInformation = userDigitalWalletDAO.getDefaultUserDigitalWalletAndInformationByUserID(currUserID);
        if (userDigitalWalletWithInformation != null) {
            if(userDigitalWalletWithInformation.getImg() != null && !userDigitalWalletWithInformation.getImg().isEmpty()) {
                Glide.with(getContext())
                        .load(userDigitalWalletWithInformation.getImg()) // Load image from the URL string
                        .placeholder(R.drawable.default_expense_icon) // Placeholder while loading
                        .error(R.drawable.error_icon) // Error placeholder if loading fails
                        .into(mDigitalWalletImageView); // Set the loaded image to ImageView
            } else {
                mDigitalWalletImageView.setImageResource(R.drawable.default_expense_icon);
            }

            mSetDigitalWalletDefault.setChecked(true);
            mWalletUserFirstNameTextView.setText(currUser.getFirstName());
            mCardNumberOrToken.setText(userDigitalWalletWithInformation.getCard_number_or_token().substring(userDigitalWalletWithInformation.getCard_number_or_token().length() - 4));
            mWalletTypeTextView.setText(userDigitalWalletWithInformation.getWallet_type());
        } else {
            mDigitalWalletImageView.setVisibility(View.GONE);
            mWalletUserFirstNameTextView.setText("");
            mCardNumberOrToken.setText("");
            mWalletTypeTextView.setText("");
            mNoUserWalletsTextView.setVisibility(View.VISIBLE);
            mUserWalletNumberOrTokenPlaceholderTextView.setVisibility(View.GONE);
        }

        mSetDigitalWalletDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userDigitalWalletDAO.userHasDefaultDigitalWallet(currUserID)) {
                    if(mSetDigitalWalletDefault.isChecked()) {
                        Toast.makeText(getContext(), "Defaul wallet is already set!", Toast.LENGTH_SHORT).show();
                    } else if(!mSetDigitalWalletDefault.isChecked()) {
                        userDigitalWalletDAO.updateUserDefaultWalletStatus(currUserID);
                    }
                }
            }
        });

        try {
            LiveData<List<Transaction>> digitalTransactionLiveDataList = transactionDAO.getMonthMostRecentWalletTransactionsByUserID(currUserID);
            digitalTransactionLiveDataList.observe(getViewLifecycleOwner(), digitalTransactionList -> {
                if (digitalTransactionList != null && !digitalTransactionList.isEmpty()) {
                    if (digitalTransactionList.size() <= 4) {
                        // Set a specific height to the CardView when list size is less than or equal to 4
                        ViewGroup.LayoutParams layoutParams = mRecentWalletTransactionsCardView.getLayoutParams();
                        layoutParams.height = getResources().getDimensionPixelSize(R.dimen.recent_expenses_cardview_height);
                        mRecentWalletTransactionsCardView.setLayoutParams(layoutParams);
                    } else {
                        // Reset the height to default or any other desired height when list size is greater than 4
                        ViewGroup.LayoutParams layoutParams = mRecentWalletTransactionsCardView.getLayoutParams();
                        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT; // or set a default height
                        mRecentWalletTransactionsCardView.setLayoutParams(layoutParams);
                    }

                    LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext()) {
                        @Override
                        public boolean canScrollVertically() {
                            return false;
                        }
                    };

                    mRecentWalletTransactionsRecyclerView.setLayoutManager(layoutManager);
                    TransactionAdapter transactionAdapter = new TransactionAdapter(requireContext(), digitalTransactionList);
                    mRecentWalletTransactionsRecyclerView.setAdapter(transactionAdapter);

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
                                digitalTransactionList.add(position, transaction);
                                Snackbar.make(mRecentWalletTransactionsRecyclerView, "Deleted: " + transaction.getTitle(), Snackbar.LENGTH_LONG)
                                        .setAction("Undo", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                transactionDAO.insertTransaction(transaction);
                                                digitalTransactionList.add(position, transaction);
                                                transactionAdapter.notifyItemInserted(position);

                                                if (digitalTransactionList.isEmpty()) {
                                                    mEmptyTransactionsTextView.setVisibility(View.VISIBLE);
                                                    mRecentWalletTransactionsRecyclerView.setVisibility(View.GONE);
                                                } else {
                                                    mEmptyTransactionsTextView.setVisibility(View.GONE);
                                                    mRecentWalletTransactionsRecyclerView.setVisibility(View.VISIBLE);
                                                }
                                            }
                                        }).show();

                                transactionAdapter.removeTransaction(position);
                                transactionAdapter.notifyItemRemoved(position);
                                transactionAdapter.notifyItemRangeChanged(position, transactionAdapter.getItemCount());


                                if (digitalTransactionList.isEmpty()) {
                                    mEmptyTransactionsTextView.setVisibility(View.VISIBLE);
                                    mRecentWalletTransactionsRecyclerView.setVisibility(View.GONE);
                                } else {
                                    mEmptyTransactionsTextView.setVisibility(View.GONE);
                                    mRecentWalletTransactionsRecyclerView.setVisibility(View.VISIBLE);
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
                    itemTouchHelper.attachToRecyclerView(mRecentWalletTransactionsRecyclerView);
                } else {
                    mEmptyTransactionsTextView.setVisibility(View.VISIBLE);
                    mRecentWalletTransactionsRecyclerView.setVisibility(View.GONE);
                }
            });

        }catch (Exception e) {
            e.printStackTrace();
            mEmptyTransactionsTextView.setVisibility(View.VISIBLE);
            mRecentWalletTransactionsRecyclerView.setVisibility(View.GONE);
        }

        // Inflate the layout for this fragment
        return view;
    }

    private void initializeDatabase() {
        ExpenseTrackerDatabase expenseTrackerDatabase = Room.databaseBuilder(
                requireContext(), ExpenseTrackerDatabase.class, ExpenseTrackerDatabase.DATABASE_NAME).allowMainThreadQueries().build();

        this.transactionDAO = expenseTrackerDatabase.transactionDAO();
        this.userDAO = expenseTrackerDatabase.userDAO();
        this.userDigitalWalletDAO = expenseTrackerDatabase.userDigitalWalletDAO();
    }
}