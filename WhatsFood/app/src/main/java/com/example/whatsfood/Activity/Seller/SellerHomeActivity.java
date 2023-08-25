package com.example.whatsfood.Activity.Seller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.whatsfood.Activity.Buyer.BuyerViewSelectedFoodActivity;
import com.example.whatsfood.Adapter.OrderAdapterForBuyer;
import com.example.whatsfood.Model.Food;
import com.example.whatsfood.Model.Order;
import com.example.whatsfood.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class SellerHomeActivity extends Fragment {

    GridView gridView;
    ArrayList<Order> orderList;
    OrderAdapterForBuyer orderAdapter;
    ArrayList<Food> foodList;

    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_seller_home, null);
        getActivity().setTitle("Home");
        setHasOptionsMenu(true);

        gridView = (GridView) view.findViewById(R.id.order_grid_list);

        // TODO: Food

        orderList = new ArrayList<Order>();
        orderAdapter = new OrderAdapterForBuyer(getActivity(), R.layout.order_placeholder_seller, orderList);
        gridView.setAdapter(orderAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), SellerOrderDetailsActivity.class);
                intent.putExtra("Order", (Serializable) orderList.get(i));
                startActivity(intent);
            }
        });

        databaseRef.child("Order").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Order order = snapshot.getValue(Order.class);
                if (order != null) {
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    if (Objects.equals(order.getSellerId(), mAuth.getUid())) {
                        orderList.add(order);
                        orderAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Order order = snapshot.getValue(Order.class);
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                if (order != null) {
                    if (Objects.equals(mAuth.getUid(), order.getSellerId())) {
                        for (int i = 0; i < orderList.size(); i++) {
                            if (Objects.equals(orderList.get(i).getOrderId(), order.getOrderId())) {
                                orderList.set(i, order);
                            }
                        }
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Order order = snapshot.getValue(Order.class);
                if (order != null) {
                    orderList.remove(order);
                    orderAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), BuyerViewSelectedFoodActivity.class);
                intent.putExtra("OrderID", foodList.get(i).getFoodId());
                startActivity(intent);
            }
        });

        return view;
    }
}