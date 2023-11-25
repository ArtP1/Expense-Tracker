package com.example.expensetracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
                Fragment selectedFragment = null;

                // Get the currently displayed fragment
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);

                // Check which item was selected and assign the appropriate fragment
                if (itemId == R.id.home) {
                    selectedFragment = new HomeFragment();

                } else if (itemId == R.id.transaction) {
                    selectedFragment = new TransactionsFragment();

                } else if (itemId == R.id.wallet) {
                    selectedFragment = new WalletsFragment();

                } else if (itemId == R.id.account) {
                    selectedFragment = new AccountFragment();

                } else if (itemId == R.id.newTransaction) {
                    selectedFragment = new NewTransactionFragment();
                }

                // Check if the selected fragment is different from the current one
                if (currentFragment != null) {
                    if(!selectedFragment.getClass().equals(currentFragment.getClass())) {
                        loadFragment(selectedFragment, false);
                    }
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

    public void loadFragment(Fragment fragment, boolean isAppInitialized) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (isAppInitialized) {
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