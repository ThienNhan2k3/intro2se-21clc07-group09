package com.example.whatsfood.Activity.Seller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.whatsfood.Adapter.FoodAdapter;
import com.example.whatsfood.Model.Food;
import com.example.whatsfood.Model.Order;
import com.example.whatsfood.R;

import java.util.ArrayList;

public class SellerOrderDetailsActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Food> foodList;
    FoodAdapter foodAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_order_details);

        listView = (ListView) findViewById(R.id.food_list_seller_order_details);

        Order order = (Order) getIntent().getSerializableExtra("Order");

        foodList = order.getFoodList();
        foodAdapter = new FoodAdapter(this, R.layout.item_suborder, foodList);
        listView.setAdapter(foodAdapter);
    }
}