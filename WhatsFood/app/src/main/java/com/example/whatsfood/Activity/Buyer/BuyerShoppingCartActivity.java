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
import com.example.whatsfood.Model.CartDetail;
import com.example.whatsfood.R;

import java.util.ArrayList;

public class BuyerShoppingCartActivity extends Fragment {
    ListView listView;
    ArrayList<CartDetail> cartDetails;
    CartAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_buyer_shopping_cart, null);
        getActivity().setTitle("Shopping Cart");
        setHasOptionsMenu(true);

        listView= (ListView) v.findViewById(R.id.food_in_cart_detail);
        cartDetails=new ArrayList<>();
        cartDetails.add(new CartDetail(R.drawable.hamburger,"Hamburger",100000,2));

        adapter=new CartAdapter(getActivity(),R.layout.food_in_cart_detail,cartDetails);

        listView.setAdapter(adapter);
        return v;
    }

}