package com.example.whatsfood.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.whatsfood.Activity.Buyer.BuyerBottomNavigationActivity;
import com.example.whatsfood.Activity.Seller.SellerBottomNavigationActivity;
import com.example.whatsfood.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView logo = (TextView) findViewById(R.id.main_activity_logo);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        logo.startAnimation(fadeIn);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                startActivity(new Intent(MainActivity.this, SellerBottomNavigationActivity.class));
                finish();
            }
        }, 900);
    }


}