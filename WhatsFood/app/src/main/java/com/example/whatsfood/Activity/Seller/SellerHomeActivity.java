package com.example.whatsfood.Activity.Seller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.whatsfood.Adapter.OrderAdapter;
import com.example.whatsfood.Model.Food;
import com.example.whatsfood.Model.Order;
import com.example.whatsfood.R;

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

        String imageUrl = "https://media.istockphoto.com/id/1309352410/photo/cheeseburger-with-tomato-and-lettuce-on-wooden-board.jpg?s=612x612&w=0&k=20&c=lfsA0dHDMQdam2M1yvva0_RXfjAyp4gyLtx4YUJmXgg=";
        String[] comments = {"Delicous", "So expensive", "affordable price"};

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
        orderAdapter = new OrderAdapter(getActivity(), R.layout.activity_seller_home, orderList);
        gridView.setAdapter(orderAdapter);

        return view;
    }
}