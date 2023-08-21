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
import android.widget.TextView;

import com.example.whatsfood.Adapter.CartAdapter;
//import com.example.whatsfood.Model.CartDetail;
import com.example.whatsfood.Model.CartDetail;
import com.example.whatsfood.Model.Food;
import com.example.whatsfood.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
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
    private int totalMoney = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_buyer_view_cart, null);
        getActivity().setTitle("Shopping Cart");
        setHasOptionsMenu(true);

        listView= (ListView) v.findViewById(R.id.view_cart_food);
        TextView totalMoneyTextView = (TextView) v.findViewById(R.id.totalmoney);

        cartAdapter=new CartAdapter(getActivity(),R.layout.food_in_cart_detail,cartDetailList);
        listView.setAdapter(cartAdapter);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        String buyerId  = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mDatabase.child("Buyer").child(buyerId).child("cartDetailList").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String key = snapshot.getKey();
                if (key.equals("0")) {
                    return;
                }
                CartDetail cartItem = snapshot.getValue(CartDetail.class);

                if (cartItem != null) {
                    cartDetailList.add(cartItem);
                    totalMoney += cartItem.getNumber() * cartItem.getPrice();
                    totalMoneyTextView.setText(String.valueOf((totalMoney)));
                }
                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                snapshot = snapshot.child(snapshot.getKey());
                CartDetail cartItem = snapshot.getValue(CartDetail.class);
                if (cartItem != null && cartDetailList.isEmpty() == false) {
                    for (int i = 0; i < cartDetailList.size(); i++) {
                        if (cartDetailList.get(i).getFoodId() == cartItem.getFoodId()) {
                            totalMoney += (cartItem.getNumber() - cartDetailList.get(i).getNumber()) * cartDetailList.get(i).getPrice();
                            cartDetailList.set(i, cartItem);
                            break;
                        }
                    }
                    totalMoneyTextView.setText(String.valueOf((totalMoney)));
                }
                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                snapshot = snapshot.child(snapshot.getKey());
                CartDetail cartItem = snapshot.getValue(CartDetail.class);
                if (cartItem != null && cartDetailList.isEmpty() == false) {
                    for (int i = 0; i < cartDetailList.size(); i++) {
                        if (cartDetailList.get(i).getFoodId() == cartItem.getFoodId()) {
                            totalMoney -= cartDetailList.get(i).getNumber() * cartDetailList.get(i).getPrice();
                            cartDetailList.remove(i);
                            break;
                        }
                    }
                    totalMoneyTextView.setText(String.valueOf((totalMoney)));
                }
                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return v;
    }

}