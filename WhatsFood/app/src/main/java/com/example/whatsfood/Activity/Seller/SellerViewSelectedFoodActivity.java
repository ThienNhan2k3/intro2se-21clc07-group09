package com.example.whatsfood.Activity.Seller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.whatsfood.R;

public class SellerViewSelectedFoodActivity extends AppCompatActivity {

    AppCompatButton updateBtn, deleteBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_view_selected_food);

        updateBtn = (AppCompatButton) findViewById(R.id.update_button_seller_view_selected_food);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SellerViewSelectedFoodActivity.this, SellerUpdateItemActivity.class));
            }
        });
    }
}