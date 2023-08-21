package com.example.whatsfood.Activity.Buyer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.whatsfood.Adapter.CartAdapter;
//import com.example.whatsfood.Model.CartDetail;
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

public class BuyerShoppingCartActivity extends Fragment {
    ListView listView;
    ArrayList<CartDetail> cartDetailList=new ArrayList<>();
    CartAdapter cartAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_buyer_view_cart, null);
        getActivity().setTitle("Shopping Cart");
        setHasOptionsMenu(true);

        listView= (ListView) v.findViewById(R.id.view_cart_food);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        String buyerId  = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mDatabase.child("Buyer").child(buyerId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Map<String, Object>> rawCartDetailList = (ArrayList<Map<String, Object>>) snapshot.child("cartDetailList").getValue();

                if (rawCartDetailList != null) {
                    cartDetailList.clear(); // Xóa danh sách cũ trước khi thêm dữ liệu mới

                    for (int i = 1; i < rawCartDetailList.size(); i++) {
                        Map<String, Object> rawCartItem = rawCartDetailList.get(i);

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
                cartAdapter=new CartAdapter(getActivity(),R.layout.food_in_cart_detail,cartDetailList);
                listView.setAdapter(cartAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return v;
    }

}