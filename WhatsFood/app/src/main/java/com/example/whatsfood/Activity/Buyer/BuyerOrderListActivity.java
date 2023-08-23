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

import com.example.whatsfood.Adapter.FoodAdapter;
import com.example.whatsfood.Adapter.OrderAdapter;
import com.example.whatsfood.Model.CartDetail;
import com.example.whatsfood.Model.Food;
import com.example.whatsfood.Model.Order;
import com.example.whatsfood.R;

import java.util.ArrayList;
import java.util.HashMap;

public class BuyerOrderListActivity extends Fragment {
    ListView listView;
    ArrayList<Order> orderList;
    OrderAdapter orderAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_buyer_order_list, null);
        requireActivity().setTitle("Order List");
        setHasOptionsMenu(true);

        listView = (ListView) v.findViewById(R.id.list_view_buyer_order_list);

        ArrayList<CartDetail> foodList = new  ArrayList<CartDetail>();
        foodList.add(new CartDetail());
        foodList.add(new CartDetail());
        foodList.add(new CartDetail());
        Order newOrder = new Order("orderId", "buyerId", "buyerName", "sellerId", "ship_to", 100000, foodList);
        orderList = new ArrayList<Order>();
        orderList.add(newOrder);

        orderAdapter = new OrderAdapter(getActivity(), R.layout.order_placeholder_buyer, orderList);
        listView.setAdapter(orderAdapter);

        return v;
    }
}