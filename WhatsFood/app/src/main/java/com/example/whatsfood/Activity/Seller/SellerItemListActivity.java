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

import java.io.Serializable;
import java.util.ArrayList;

public class SellerItemListActivity extends Fragment {

    GridView gridView;
    ArrayList<Food> foodList;
    FoodAdapter foodAdapter;
    AppCompatButton addBtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_seller_item_list, null);
        getActivity().setTitle("Item List");
        setHasOptionsMenu(true);

        addBtn = (AppCompatButton) view.findViewById(R.id.add_food_seller_food_list_activity);
        gridView = (GridView) view.findViewById(R.id.order_grid_view_seller_item_lít);

        String imageUrl = "https://spoonsofflavor.com/wp-content/uploads/2020/08/Easy-Chicken-Fry-Recipe.jpg";
        ArrayList<String> comments = new ArrayList<String>();
        comments.add("Delicous");
        comments.add("So expensive");
        comments.add("affordable price");

        foodList = new ArrayList<Food>();
        for (int i = 0; i < 10; i++) {
            foodList.add(new Food("foodId",
                    "Chicken Fried",
                    "description",
                    "100000 " + "VND",
                    imageUrl,
                    "1",
                    "sellerId",
                    "Shoppe",
                    comments));
        }
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

