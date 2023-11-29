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

    private static final String SELECTED_FRAGMENT_TAG = "selectedFragmentTag";
    private Fragment selectedFragment;


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

                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);

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

                if (currentFragment != null) {
                    if (!selectedFragment.getClass().equals(currentFragment.getClass())) {
                        loadFragment(selectedFragment, false);
                    }
                }

                return true;
            }
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the tag of the selected fragment if it exists
        if (selectedFragment != null) {
            outState.putString(SELECTED_FRAGMENT_TAG, selectedFragment.getTag());
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore the selected fragment if available
        String tag = savedInstanceState.getString(SELECTED_FRAGMENT_TAG);
        if (tag != null) {
            selectedFragment = getSupportFragmentManager().findFragmentByTag(tag);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Load the selected fragment or the default fragment (HomeFragment)
        if (selectedFragment != null) {
            loadFragment(selectedFragment, true);
        } else {
            loadFragment(new HomeFragment(), true);
        }
    }

    public void loadFragment(Fragment fragment, boolean isAppInitialized) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (isAppInitialized) {
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            Fragment existingFragment = fragmentManager.findFragmentByTag(fragment.getClass().getSimpleName());
            if (existingFragment == null) {
                fragmentTransaction.add(R.id.frameLayout, fragment, fragment.getClass().getSimpleName());
            } else {
                fragment = existingFragment;
            }
        } else {
            // Check if the fragment is already added
            if (fragment.isAdded()) {
                fragmentTransaction.show(fragment);
            } else {
                Fragment existingFragment = fragmentManager.findFragmentByTag(fragment.getClass().getSimpleName());
                if (existingFragment != null) {
                    fragmentTransaction.show(existingFragment);
                } else {
                    fragmentTransaction.replace(R.id.frameLayout, fragment, fragment.getClass().getSimpleName());
                }
            }
        }

        // Hide other fragments
        for (Fragment existingFragment : fragmentManager.getFragments()) {
            if (existingFragment != null && !existingFragment.equals(fragment)) {
                fragmentTransaction.hide(existingFragment);
            }
        }

        fragmentTransaction.commit();
    }


    public void setSelectedFragment(Fragment fragment) {
        selectedFragment = fragment;
    }

    // Add this method to update the selected item in the BottomNavigationView
    public void updateSelectedNavItem(int itemId) {
        mBottomNavigation.setSelectedItemId(itemId);
    }
    public static Intent getIntent(Context context) {
        return new Intent(context, FragmentContainerActivity.class);
    }

}