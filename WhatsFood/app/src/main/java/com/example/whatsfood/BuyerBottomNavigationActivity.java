package com.example.whatsfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BuyerBottomNavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_bottom_navigation_activity);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.buyer_bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Fragment fragment = null;
                if (id == R.id.buyer_home) {
                    fragment = new BuyerHomeActivity();
                } else if (id == R.id.buyer_order_list) {
                    fragment = new BuyerOrderListActivity();
                } else if (id == R.id.buyer_shopping_cart) {
                    fragment = new BuyerShoppingCartActivity();
                } else if (id == R.id.buyer_profile) {
                    fragment = new ProfileActivity();
                }
                if (fragment != null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.buyer_fragment_container, fragment)
                            .commit();
                    return true;
                }
                return false;
            }
        });
    }
}