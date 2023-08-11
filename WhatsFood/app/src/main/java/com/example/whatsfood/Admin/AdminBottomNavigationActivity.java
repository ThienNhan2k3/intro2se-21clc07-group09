package com.example.whatsfood.Admin;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.whatsfood.Buyer.BuyerHomeActivity;
import com.example.whatsfood.Buyer.BuyerOrderListActivity;
import com.example.whatsfood.Buyer.BuyerShoppingCartActivity;
import com.example.whatsfood.ProfileActivity;
import com.example.whatsfood.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminBottomNavigationActivity extends AppCompatActivity {
    Fragment fragment;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_bottom_navigation);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.admin_bottom_navigation);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.admin_fragment_container, new AdminHomeActivity())
                .commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                fragment = null;
                if (id == R.id.admin_navigation_users) {
                    fragment = new AdminHomeActivity();
                } else if (id == R.id.admin_navigation_register) {
                    fragment = new AdminSellerRegisterRequestsActivity();
                } else if (id == R.id.admin_navigation_report) {
                    fragment = new AdminReportsListActivity();
                }
                if (fragment != null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.admin_fragment_container, fragment)
                            .commit();
                    return true;
                }
                return false;
            }
        });
    }
}
