package com.example.expensetracker.Fragments;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.expensetracker.ExpenseTrackerDb.DAOs.CategoryDAO;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.PaymentMethodDAO;
import com.example.expensetracker.ExpenseTrackerDb.DAOs.TransactionDAO;
import com.example.expensetracker.ExpenseTrackerDb.Entities.Category;
import com.example.expensetracker.ExpenseTrackerDb.Entities.PaymentMethod;
import com.example.expensetracker.ExpenseTrackerDb.ExpenseTrackerDatabase;
import com.example.expensetracker.FragmentContainerActivity;
import com.example.expensetracker.R;
import com.example.expensetracker.databinding.FragmentNewTransactionBinding;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewTransactionFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class NewTransactionFragment extends Fragment {


    private EditText mEditTextTransTitle;
    private EditText mEditTextTransDescrip;
    private EditText mEditTextTransLocation;
    private EditText mEditTextTransAmount;

    private Spinner mCategorySpinner;
    private Spinner mPaymentMethodsSpinner;
    private CheckBox mCheckBoxTransExpense;
    private CheckBox mCheckBoxTransEarning;
    private Button mCreateTransactionBtn;

    private CategoryDAO categoryDAO;
    private TransactionDAO transactionDAO;

    private PaymentMethodDAO paymentMethodDAO;

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
        ((FragmentContainerActivity) requireActivity()).getWindow().setStatusBarColor(statusBarColor);
        ((FragmentContainerActivity) requireActivity()).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        FragmentNewTransactionBinding mNewTransactionBinding = FragmentNewTransactionBinding.inflate(inflater, container, false);
        View view = mNewTransactionBinding.getRoot();

        initializeDatabase();

        mEditTextTransTitle = mNewTransactionBinding.editTextTransTitle;
        mEditTextTransDescrip = mNewTransactionBinding.editTextTransDescrip;
        mEditTextTransLocation = mNewTransactionBinding.editTextTransLocation;
        mEditTextTransAmount = mNewTransactionBinding.editTextTransAmount;
        mCategorySpinner = mNewTransactionBinding.categorySpinner;
        mCheckBoxTransExpense = mNewTransactionBinding.checkBoxTransExpense;
        mCheckBoxTransEarning = mNewTransactionBinding.checkBoxTransEarning;
        mCreateTransactionBtn = mNewTransactionBinding.createTransactionBtn;
        mPaymentMethodsSpinner = mNewTransactionBinding.paymentMethodsSpinner;

        displayData();

        mCreateTransactionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String transTitle = mEditTextTransTitle.getText().toString();
                String transDescrip = mEditTextTransDescrip.getText().toString();
                String transLocation = mEditTextTransLocation.getText().toString();
                double transDouble = Double.parseDouble(mEditTextTransAmount.getText().toString());
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
}