package com.example.whatsfood.Activity.Buyer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.whatsfood.Model.Food;
import com.example.whatsfood.R;
import com.example.whatsfood.Adapter.FoodAdapter;

import java.util.ArrayList;

public class BuyerHomeActivity extends Fragment {

    ListView listView;
    ArrayList<Food> foodList;
    FoodAdapter foodAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_buyer_home, null);
        getActivity().setTitle("Home");
        setHasOptionsMenu(true);

        listView = (ListView) view.findViewById(R.id.listView);

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
        foodAdapter = new FoodAdapter(getActivity(), R.layout.food_holder_buyer, foodList);
        listView.setAdapter(foodAdapter);
        return view;
    }
}