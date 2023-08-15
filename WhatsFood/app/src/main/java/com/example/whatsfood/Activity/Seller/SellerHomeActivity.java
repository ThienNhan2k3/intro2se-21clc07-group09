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

import com.example.whatsfood.Activity.Buyer.BuyerBottomNavigationActivity;
import com.example.whatsfood.Adapter.OrderAdapter;
import com.example.whatsfood.Model.Food;
import com.example.whatsfood.Model.Order;
import com.example.whatsfood.R;

import java.io.Serializable;
import java.util.ArrayList;

public class SellerHomeActivity extends Fragment {

    GridView gridView;
    ArrayList<Order> orderList;
    OrderAdapter orderAdapter;
    ArrayList<Food> foodList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_seller_home, null);
        getActivity().setTitle("Home");
        setHasOptionsMenu(true);

        gridView = (GridView) view.findViewById(R.id.order_grid_list);

        String imageUrl = "https://spoonsofflavor.com/wp-content/uploads/2020/08/Easy-Chicken-Fry-Recipe.jpg";
        ArrayList<String> comments = new ArrayList<String>();
        comments.add("Delicous");
        comments.add("So expensive");
        comments.add("affordable price");

        foodList = new ArrayList<Food>();
        for (int i = 0; i < 10; i++) {
            foodList.add(new Food("foodId",
                    "name",
                    "description",
                    "100000",
                    imageUrl,
                    "1",
                    "sellerId",
                    "Shoppe",
                    comments));
        }

        orderList = new ArrayList<Order>();
        for (int i = 0; i < 10; i++) {
            orderList.add(new Order("#1234",
                    "Buyer ID",
                    "Dai",
                    "Seller Id",
                    " 227 Đ. Nguyễn Văn Cừ, Phường 4, Quận 5, Thành phố Hồ Chí Minh",
                    "100000",
                    foodList,
                    0));
        }
        orderAdapter = new OrderAdapter(getActivity(), R.layout.order_placeholder, orderList);
        gridView.setAdapter(orderAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), SellerOrderDetailsActivity.class);
                intent.putExtra("Order", (Serializable) orderList.get(i));
                startActivity(intent);
            }
        });

        return view;
    }
}