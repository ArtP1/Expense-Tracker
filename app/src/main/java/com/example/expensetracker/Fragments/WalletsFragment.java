package com.example.expensetracker.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.expensetracker.FragmentContainerActivity;
import com.example.expensetracker.R;
import com.example.expensetracker.databinding.FragmentWalletsBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WalletsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WalletsFragment extends Fragment {

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
        ((FragmentContainerActivity) requireActivity()).getWindow().setStatusBarColor(statusBarColor);
        ((FragmentContainerActivity) requireActivity()).getWindow().getDecorView().setSystemUiVisibility(0);

        FragmentWalletsBinding mWalletFragmentBinding = FragmentWalletsBinding.inflate(inflater, container, false);
        View view = mWalletFragmentBinding.getRoot();

        // Inflate the layout for this fragment
        return view;
    }
}