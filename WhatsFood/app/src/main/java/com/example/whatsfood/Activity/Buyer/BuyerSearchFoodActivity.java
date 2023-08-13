package com.example.whatsfood.Activity.Buyer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsfood.Activity.SelectAccountTypeActivity;
import com.example.whatsfood.R;

public class BuyerSearchFoodActivity extends AppCompatActivity {

    ImageButton search_button;
    ImageButton back_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_search_food);
        ((TextView)findViewById(R.id.header)).setText("WhatsFood");
        search_button = (ImageButton)findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchFood();
            }
        });
        back_button = (ImageButton)findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BuyerSearchFoodActivity.this, BuyerHomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void SearchFood() {
        Intent intent = new Intent(BuyerSearchFoodActivity.this, BuyerSearchFoodActivity.class);
        startActivity(intent);
    }
}
