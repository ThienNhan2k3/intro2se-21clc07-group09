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

//        String imageUrl = "https://spoonsofflavor.com/wp-content/uploads/2020/08/Easy-Chicken-Fry-Recipe.jpg";
//        String[] comments = {"Delicous", "So expensive", "affordable price"};
//        foodList = new ArrayList<Food>();
//        for (int i = 0; i < 10; i++) {
//            foodList.add(new Food("foodId",
//                    "name",
//                    "description",
//                    "100000",
//                    imageUrl,
//                    "1",
//                    "sellerId",
//                    "Shoppe",
//                    comments));
//        }
        foodAdapter = new FoodAdapter(this, R.layout.item_suborder, foodList);
        listView.setAdapter(foodAdapter);
    }
}