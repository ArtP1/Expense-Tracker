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
import com.example.expensetracker.Fragments.ExpensesFragment;
import com.example.expensetracker.Fragments.HomeFragment;
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

        loadFragment(new HomeFragment(), true);

        mBottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if(itemId == R.id.home) {
                    loadFragment(new HomeFragment(), false);

                } else if(itemId == R.id.expenses) {
                    loadFragment(new ExpensesFragment(), false);

                } else if(itemId == R.id.wallet) {
                    loadFragment(new WalletsFragment(), false);

                } else if (itemId == R.id.account) {
                    loadFragment(new AccountFragment(), false);

                }

                return true;
            }
        });

    }

    private void loadFragment(Fragment fragment, boolean isAppInitialized) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(isAppInitialized) {
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