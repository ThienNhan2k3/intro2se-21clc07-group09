package com.example.whatsfood.Activity.Seller;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.whatsfood.Adapter.FoodAdapter;
import com.example.whatsfood.Model.Food;
import com.example.whatsfood.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.ArrayList;

public class SellerItemListActivity extends Fragment {

    GridView gridView;
    ArrayList<Food> foodList;
    FoodAdapter foodAdapter;
    AppCompatButton addBtn;

    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_seller_item_list, null);
        getActivity().setTitle("Item List");
        setHasOptionsMenu(true);

        addBtn = (AppCompatButton) view.findViewById(R.id.add_food_seller_food_list_activity);
        gridView = (GridView) view.findViewById(R.id.order_grid_view_seller_item_lít);

        foodList = new ArrayList<Food>();
        databaseRef.child("Food").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Food food = snapshot.getValue(Food.class);
                if (food != null) {
                    foodList.add(food);
                    foodAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Food food = snapshot.getValue(Food.class);
                if (food != null && foodList.isEmpty() == false) {
                    for (int i = 0; i < foodList.size(); i++) {
                        if (foodList.get(i).getFoodId() == food.getFoodId()) {
                            foodList.set(i, food);
                        }
                    }
                    foodAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Food food = snapshot.getValue(Food.class);
                if (food != null && foodList.isEmpty() == false) {
                    for (int i = 0; i < foodList.size(); i++) {
                        if (foodList.get(i).getFoodId() == food.getFoodId()) {
                            foodList.remove(i);
                            break;
                        }
                    }
                    foodAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        foodAdapter = new FoodAdapter(getActivity(), R.layout.shop_item_holder, foodList);
        gridView.setAdapter(foodAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity() , SellerViewSelectedFoodActivity.class);
                Food food = foodList.get(i);
                intent.putExtra("Item", (Serializable) food);
                startActivity(intent);

            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SellerAddNewItemActivity.class));
            }
        });

        return view;
    }
}

