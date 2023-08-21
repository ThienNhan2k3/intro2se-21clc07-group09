package com.example.whatsfood.Activity.Buyer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsfood.Adapter.CartAdapter;
import com.example.whatsfood.Model.CartDetail;
import com.example.whatsfood.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class BuyerViewCart extends AppCompatActivity {
    ArrayList<CartDetail> cartDetailList=new ArrayList<>();
    CartAdapter cartAdapter;
    ListView listView;
    Dialog dialog;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_view_cart);
        listView=(ListView) findViewById(R.id.view_cart_food);
        dialog = new Dialog(this);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        String buyerId  = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mDatabase.child("Buyer").child(buyerId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Map<String, Object>> rawCartDetailList = (ArrayList<Map<String, Object>>) snapshot.child("cartDetailList").getValue();

                if (rawCartDetailList != null) {
                    cartDetailList.clear(); // Xóa danh sách cũ trước khi thêm dữ liệu mới

                    for (Map<String, Object> rawCartItem : rawCartDetailList) {
                        String foodId = (String) rawCartItem.get("foodId");
                        String imageUrl = (String) rawCartItem.get("imageUrl");
                        String name = (String) rawCartItem.get("name");
                        int price = ((Long) rawCartItem.get("price")).intValue();
                        int number = ((Long) rawCartItem.get("number")).intValue();

                        CartDetail cartItem = new CartDetail(foodId, imageUrl, name, price, number);
                        cartDetailList.add(cartItem);
                    }

                    // Tiếp tục xử lý dữ liệu sau khi đã tạo danh sách cartDetailList
                }
                cartAdapter=new CartAdapter(BuyerViewCart.this,R.layout.food_in_cart_detail,cartDetailList);
                listView.setAdapter(cartAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
