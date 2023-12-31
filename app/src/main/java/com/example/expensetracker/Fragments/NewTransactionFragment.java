package com.example.expensetracker.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.expensetracker.ExpenseTrackerDb.DAOs.CategoryDAO;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.DigitalWalletDAO;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.PaymentMethodDAO;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.TransactionDAO;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.UserDigitalWalletDAO;
import com.example.expensetracker.ExpenseTrackerDb.Entities.Category;
import com.example.expensetracker.ExpenseTrackerDb.Entities.DigitalWallet;
import com.example.expensetracker.ExpenseTrackerDb.Entities.PaymentMethod;
import com.example.expensetracker.ExpenseTrackerDb.Entities.Transaction;
import com.example.expensetracker.ExpenseTrackerDb.Entities.UserDigitalWallet;
import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;
import com.example.expensetracker.Preferences;
import com.example.expensetracker.R;
import com.example.expensetracker.databinding.FragmentNewTransactionBinding;

import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewTransactionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewTransactionFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private EditText mEditTextTransTitle;
    private EditText mEditTextTransDescrip;
    private EditText mEditTextTransLocation;
    private EditText mEditTextTransAmount;
    TextView mTransactionDateTextView;
    private Spinner mCategorySpinner;
    private Spinner mPaymentMethodsSpinner;
    private Spinner mUserDigitalWalletsSpinner;
    private LinearLayout mDigitalWalletPopupInputsContainer;
    private RelativeLayout mNewDigitalWalletForm;

    private CheckBox mCheckBoxTransExpense;
    private CheckBox mCheckBoxTransEarning;
    EditText mNewDigitalWalletNumberToken;
    private CheckBox mNewDigitalWalletIsDefault;
    private Spinner mNewDigitalWalletTypeSpinner;
    private CategoryDAO categoryDAO;
    private TransactionDAO transactionDAO;
    private PaymentMethodDAO paymentMethodDAO;
    private UserDigitalWalletDAO userDigitalWalletDAO;
    private DigitalWalletDAO digitalWalletDAO;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewExpenseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewTransactionFragment newInstance(String param1, String param2) {
        NewTransactionFragment fragment = new NewTransactionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public NewTransactionFragment() {
        // Required empty public constructor
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

        FragmentNewTransactionBinding mNewTransactionBinding = FragmentNewTransactionBinding.inflate(inflater, container, false);
        View view = mNewTransactionBinding.getRoot();

        initializeDatabase();

        this.sharedPreferences = requireActivity().getSharedPreferences(Preferences.EXPENSE_TRACKER_PREFERENCES, MODE_PRIVATE);
        long currUserID = sharedPreferences.getLong(Preferences.USER_ID_KEY, -1);


        this.mEditTextTransTitle = mNewTransactionBinding.editTextTransTitle;
        this.mEditTextTransDescrip = mNewTransactionBinding.transDescriptionInput;
        this.mEditTextTransLocation = mNewTransactionBinding.editTextTransLocation;
        this.mEditTextTransAmount = mNewTransactionBinding.editTextTransAmount;
        this.mCategorySpinner = mNewTransactionBinding.categorySpinner;
        this.mCheckBoxTransExpense = mNewTransactionBinding.checkBoxTransExpense;
        this.mCheckBoxTransEarning = mNewTransactionBinding.checkBoxTransEarning;
        Button mCreateTransactionBtn = mNewTransactionBinding.createTransactionBtn;
        this.mPaymentMethodsSpinner = mNewTransactionBinding.paymentMethodsSpinner;
        this.mTransactionDateTextView = mNewTransactionBinding.transactionDateTextView;
        this.mDigitalWalletPopupInputsContainer = mNewTransactionBinding.digitalWalletPopupInputsContainer;
        this.mUserDigitalWalletsSpinner = mNewTransactionBinding.userDigitalWalletsSpinner;
        Button mCreateDigitalWalletBtn = mNewTransactionBinding.createDigitalWalletBtn;
        this.mNewDigitalWalletForm = mNewTransactionBinding.newDigitalWalletForm;
        View mNewDigitalWalletFormBckgrd = mNewTransactionBinding.newDigitalWalletFormBckgrd;
        Button mAddNewDigitalWalletBtn = mNewTransactionBinding.addNewDigitalWalletBtn;
        this.mNewDigitalWalletIsDefault = mNewTransactionBinding.newDigitalWalletIsDefaultInput;
        this.mNewDigitalWalletNumberToken = mNewTransactionBinding.newDigitalWalletNumberTokenInput;
        this.mNewDigitalWalletTypeSpinner = mNewTransactionBinding.newDigitalWalletTypeSpinnerInput;

        displayData();

        mTransactionDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        mPaymentMethodsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

                if (selectedItem.equals("Digital Wallet")) {
                    // Show the digitalWalletSpinner
                    mDigitalWalletPopupInputsContainer.setVisibility(View.VISIBLE);

                    LiveData<List<UserDigitalWallet>> userDigitalWalletsLiveDataList = userDigitalWalletDAO.getUserDigitalWalletsByUserID(currUserID);
                    userDigitalWalletsLiveDataList.observe(getViewLifecycleOwner(), userDigitalWalletsList -> {
                        if(userDigitalWalletsList != null && !userDigitalWalletsList.isEmpty()) {
                            ArrayAdapter<UserDigitalWallet> userDigitalWalletArrayAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, userDigitalWalletsList);
                            userDigitalWalletArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            mUserDigitalWalletsSpinner.setAdapter(userDigitalWalletArrayAdapter);
                        } else {
                            Toast.makeText(getContext(), "You have no digital wallet(s)!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    mDigitalWalletPopupInputsContainer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where nothing is selected
            }
        });

        mCreateDigitalWalletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getWindow().setStatusBarColor(ContextCompat.getColor(requireContext(), R.color.domedBckgrnd));
                requireActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

                mNewDigitalWalletForm.setVisibility(View.VISIBLE);

                List<DigitalWallet> digitalWallets = digitalWalletDAO.getAllDigitalWallets();

                ArrayAdapter<DigitalWallet> digitalWalletArrayAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, digitalWallets);
                digitalWalletArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                mNewDigitalWalletTypeSpinner.setAdapter(digitalWalletArrayAdapter);
            }
        });

        mAddNewDigitalWalletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DigitalWallet selectedDigitalWallet = (DigitalWallet) mNewDigitalWalletTypeSpinner.getSelectedItem();
                String digitalWalletName = selectedDigitalWallet.getName();

                String cardNumberOrToken = mNewDigitalWalletNumberToken.getText().toString();
                boolean isSetDefaultWalletInput = mNewDigitalWalletIsDefault.isChecked();

                userDigitalWalletDAO.insertUserDigitalWallet(new UserDigitalWallet(currUserID, digitalWalletName, cardNumberOrToken, isSetDefaultWalletInput));

                mNewDigitalWalletForm.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Successfully registered new Digital Wallet!", Toast.LENGTH_SHORT).show();
            }
        });
        mNewDigitalWalletFormBckgrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getWindow().setStatusBarColor(ContextCompat.getColor(requireContext(), R.color.white));
                requireActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                mNewDigitalWalletForm.setVisibility(View.GONE);
            }
        });
        mCreateTransactionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String transTitle = mEditTextTransTitle.getText().toString();
                String transDescrip = mEditTextTransDescrip.getText().toString();
                String transLocation = mEditTextTransLocation.getText().toString();
                String amountString = mEditTextTransAmount.getText().toString();
                double transAmount = 0.0;

                if(!amountString.isEmpty()) {
                    transAmount = Double.parseDouble(amountString);
                }

                boolean transIsExpense = mCheckBoxTransExpense.isChecked();
                boolean transIsEarning = mCheckBoxTransEarning.isChecked();

                Category selectedCategory = (Category) mCategorySpinner.getSelectedItem();
                String transCategoryName = selectedCategory.getName();

                PaymentMethod selectedPaymentMethod = (PaymentMethod) mPaymentMethodsSpinner.getSelectedItem();
                String transPaymentMethod = selectedPaymentMethod.getMethod();


                Transaction newTransaction = null;
                if(!transTitle.isEmpty() && !transCategoryName.equals("N/A") && !transPaymentMethod.equals("N/A") && (transIsExpense || transIsEarning) && transAmount > 0) {
                    if(transIsExpense && transIsEarning) {
                        Toast.makeText(getContext(), "Transaction can only be either an expense or an earning!", Toast.LENGTH_SHORT).show();
                    } else {
                        if(transPaymentMethod.equals("Digital Wallet")) {
                            UserDigitalWallet selectedUserWallet = (UserDigitalWallet) mUserDigitalWalletsSpinner.getSelectedItem();
                            Integer walletId = (int) selectedUserWallet.getId();

                            if (transIsExpense) {
                                newTransaction = new Transaction(currUserID, transCategoryName, transPaymentMethod, walletId, transAmount, transTitle, transDescrip, transLocation, Transaction.Type.EXPENSE);
                            }

                            if (transIsEarning) {
                                newTransaction = new Transaction(currUserID, transCategoryName, transPaymentMethod, walletId, transAmount, transTitle, transDescrip, transLocation, Transaction.Type.EARNING);
                            }
                        } else {
                            if (transIsExpense) {
                                newTransaction = new Transaction(currUserID, transCategoryName, transPaymentMethod, transAmount, transTitle, transDescrip, transLocation, Transaction.Type.EXPENSE);
                            }

                            if (transIsEarning) {
                                newTransaction = new Transaction(currUserID, transCategoryName, transPaymentMethod, transAmount, transTitle, transDescrip, transLocation, Transaction.Type.EARNING);
                            }
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "Fill out all required inputs!", Toast.LENGTH_SHORT).show();
                }

                if (newTransaction != null) {
                    transactionDAO.insertTransaction(newTransaction);

                    Toast.makeText(getContext(), "Successfully logged new transaction!", Toast.LENGTH_SHORT).show();
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout, new HomeFragment())
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        return view;
    }

    private void initializeDatabase() {
        ExpenseTrackerDatabase expenseTrackerDatabase = Room.databaseBuilder(
                requireContext(), ExpenseTrackerDatabase.class, ExpenseTrackerDatabase.DATABASE_NAME).allowMainThreadQueries().build();

        categoryDAO = expenseTrackerDatabase.categoryDAO();
        transactionDAO = expenseTrackerDatabase.transactionDAO();
        paymentMethodDAO = expenseTrackerDatabase.paymentMethodDAO();
        userDigitalWalletDAO = expenseTrackerDatabase.userDigitalWalletDAO();
        digitalWalletDAO = expenseTrackerDatabase.digitalWalletDAO();
    }

    private void displayData() {
        List<Category> categoryList = categoryDAO.getAllCategories();

        Category defaultCategory = new Category("N/A");
        categoryList.add(0, defaultCategory);

        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, categoryList);
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mCategorySpinner.setAdapter(categoryArrayAdapter);
        mCategorySpinner.setSelection(0);

        List<PaymentMethod> paymentMethodsList = paymentMethodDAO.getAllPaymentMethods();

        PaymentMethod defaultPaymentMethod = new PaymentMethod("N/A");
        paymentMethodsList.add(0, defaultPaymentMethod);

        ArrayAdapter<PaymentMethod> paymentMethodArrayAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, paymentMethodsList);
        paymentMethodArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mPaymentMethodsSpinner.setAdapter(paymentMethodArrayAdapter);
        mPaymentMethodsSpinner.setSelection(0);
    }

    public void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Set up the DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
                    // Do something with the selected date
                    String selectedDate = selectedDayOfMonth + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    TextView transactionDateTextView = requireView().findViewById(R.id.transactionDateTextView);
                    transactionDateTextView.setText(selectedDate);
                }, year, month, dayOfMonth);

        // Set the minimum date to the start of the current month
        Calendar minCalendar = Calendar.getInstance();
        minCalendar.set(Calendar.DAY_OF_MONTH, 1);
        datePickerDialog.getDatePicker().setMinDate(minCalendar.getTimeInMillis());

        // Set the maximum date to the end of the current month
        Calendar maxCalendar = Calendar.getInstance();
        maxCalendar.set(Calendar.DAY_OF_MONTH, maxCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(maxCalendar.getTimeInMillis());

        datePickerDialog.show();
    }

}