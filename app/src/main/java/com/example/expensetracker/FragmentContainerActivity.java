package com.example.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.expensetracker.Fragments.AccountFragment;
import com.example.expensetracker.Fragments.HomeFragment;
import com.example.expensetracker.Fragments.NewTransactionFragment;
import com.example.expensetracker.Fragments.TransactionsFragment;
import com.example.expensetracker.Fragments.WalletsFragment;
import com.example.expensetracker.databinding.ActivityFragmentContainerBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class FragmentContainerActivity extends AppCompatActivity {
    ActivityFragmentContainerBinding mFragmentContainerBinding;
    BottomNavigationView mBottomNavigation;
    FrameLayout mFrameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragmentContainerBinding = ActivityFragmentContainerBinding.inflate(getLayoutInflater());
        setContentView(mFragmentContainerBinding.getRoot());

        mBottomNavigation = mFragmentContainerBinding.userNavbar;
        mFrameLayout = mFragmentContainerBinding.frameLayout;

        mBottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if(itemId == R.id.home) {
                    loadFragment(new HomeFragment(), false);

                } else if(itemId == R.id.transaction) {
                    loadFragment(new TransactionsFragment(), false);

                } else if(itemId == R.id.wallet) {
                    loadFragment(new WalletsFragment(), false);

                } else if (itemId == R.id.account) {
                    loadFragment(new AccountFragment(), false);

                } else if(itemId == R.id.newTransaction) {
                    loadFragment(new NewTransactionFragment(), false);
                }

                return true;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFragment(new HomeFragment(), true);
    }
    private void loadFragment(Fragment fragment, boolean isAppInitialized) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(isAppInitialized) {
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentTransaction.add(R.id.frameLayout, fragment);
        } else {
            fragmentTransaction.replace(R.id.frameLayout, fragment);

        }
        fragmentTransaction.commit();
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, FragmentContainerActivity.class);
    }

}