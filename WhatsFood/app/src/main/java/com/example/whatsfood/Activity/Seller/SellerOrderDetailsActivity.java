package com.example.whatsfood.Activity.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.whatsfood.Activity.AfterRegisterActivity;
import com.example.whatsfood.Activity.LoginActivity;
import com.example.whatsfood.Adapter.FoodAdapter;
import com.example.whatsfood.Model.CartDetail;
import com.example.whatsfood.Model.Food;
import com.example.whatsfood.Model.Order;
import com.example.whatsfood.Model.Seller;
import com.example.whatsfood.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SellerOrderDetailsActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Food> foodList;
    FoodAdapter foodAdapter;
    Button approve_button, deny_button;
    ImageButton back_button;
    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_order_details);

        ((TextView)findViewById(R.id.header)).setText("Order Detail");
        back_button = (ImageButton)findViewById(R.id.back_button);
        listView = (ListView) findViewById(R.id.food_list_seller_order_details);
        approve_button = (Button) findViewById(R.id.approve_button);
        deny_button = (Button) findViewById(R.id.deny_button);
        foodList = new ArrayList<Food>();
        foodAdapter = new FoodAdapter(this, R.layout.item_suborder, foodList);
        listView.setAdapter(foodAdapter);

        order = (Order) getIntent().getSerializableExtra("Order");
        //foodList = order.getFoodList();

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
        approve_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_order();
                finish();
            }
        });
        deny_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                order.status = "denied";
                order.UpdateDataToServer();
                finish();
            }
        });
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        update_order();
    }

    private void update_order() {
        for (CartDetail food : order.getFoodList()) {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Food");
            mDatabase.child(food.getFoodId()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        order.foodList.remove(food);
                        order.totalMoney -= food.getPrice() * food.getNumber();
                        order.UpdateDataToServer();
                    }
                    else {
                        Food origin_food = task.getResult().getValue(Food.class);
                        if (origin_food == null || food.getNumber() > origin_food.getQuantity()) {
                            order.foodList.remove(food);
                            order.totalMoney -= food.getPrice() * food.getNumber();
                            order.UpdateDataToServer();
                        }
                        else {
                            foodList.add(origin_food);
                            foodAdapter.foodList.add(origin_food);
                            foodAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
        if (!order.getFoodList().isEmpty()) {
            return;
        }
    }
}