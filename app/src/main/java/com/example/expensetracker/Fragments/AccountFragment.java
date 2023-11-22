package com.example.expensetracker.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        FragmentAccountBinding mAccountBinding = FragmentAccountBinding.inflate(inflater, container, false);
        View view = mAccountBinding.getRoot();

        mExpensesPieChart = mAccountBinding.transactionsPieChart;

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

        return view;
    }
}