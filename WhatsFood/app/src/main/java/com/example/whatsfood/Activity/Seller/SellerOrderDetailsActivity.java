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
import com.example.whatsfood.UI_Functions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SellerOrderDetailsActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Food> foodList;
    FoodAdapter foodAdapter;
    Button approve_button, deny_button;
    ImageButton back_button;
    TextView view_orderID, view_address;
    String orderID;
    Order order;

    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_order_details);

        foodList = new ArrayList<Food>();
        listView = (ListView) findViewById(R.id.food_list_seller_order_details);
        approve_button = (Button) findViewById(R.id.approve_button);
        deny_button = (Button) findViewById(R.id.deny_button);
        back_button = findViewById(R.id.image_button_seller_view_seleted_food);
        view_orderID = findViewById(R.id.order_id_text_view_seller_order_details);
        view_address = findViewById(R.id.address_text_view_seller_order_details);
        foodAdapter = new FoodAdapter(this, R.layout.item_suborder, foodList);
        listView.setAdapter(foodAdapter);

        orderID = getIntent().getStringExtra("OrderID");

        databaseRef.child("Order").child(orderID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                order = snapshot.getValue(Order.class);
                if (order != null) {
                    update_order();
                }
                else {
                    UI_Functions.CreatePopup(SellerOrderDetailsActivity.this, "Order has been cancelled");
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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
                order.setStatus("shipping");
                update_order();
                finish();
            }
        });
        deny_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                order.setStatus("denied");
                order.UpdateDataToServer();
                finish();
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void update_order() {
        view_orderID.setText(order.getOrderId());
        view_address.setText(order.getShip_to());
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
                        if (origin_food != null) {
                            if (food.getNumber() > origin_food.getQuantity()) {
                                order.foodList.remove(food);
                                order.totalMoney -= food.getPrice() * food.getNumber();
                                order.UpdateDataToServer();
                            } else {
                                foodList.add(origin_food);
                                foodAdapter.foodList.add(origin_food);
                                foodAdapter.notifyDataSetChanged();
                            }
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