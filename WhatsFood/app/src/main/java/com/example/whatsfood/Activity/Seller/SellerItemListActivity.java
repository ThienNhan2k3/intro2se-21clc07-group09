package com.example.whatsfood.Activity.Seller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.whatsfood.Adapter.FoodAdapter;
import com.example.whatsfood.Model.Food;
import com.example.whatsfood.R;

import java.util.ArrayList;

public class SellerItemListActivity extends Fragment {

    GridView gridView;
    ArrayList<Food> foodList;
    FoodAdapter foodAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_seller_item_list, null);
        getActivity().setTitle("Item List");
        setHasOptionsMenu(true);

        gridView = (GridView) view.findViewById(R.id.order_grid_view_seller_item_lít);

        String imageUrl = "https://spoonsofflavor.com/wp-content/uploads/2020/08/Easy-Chicken-Fry-Recipe.jpg";
        String[] comments = {"Delicous", "So expensive", "affordable price"};

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
        foodAdapter = new FoodAdapter(getActivity(), R.layout.food_holder_buyer, foodList);
        gridView.setAdapter(foodAdapter);

        return view;
    }
}

