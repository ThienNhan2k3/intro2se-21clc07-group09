package com.example.whatsfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whatsfood.Model.CartDetail;
import com.example.whatsfood.Model.Food;
import com.example.whatsfood.R;

import java.util.List;

public class CartAdapter extends BaseAdapter {

    private Context context;
    private int layout;

    private List<CartDetail> food_cartlist;

    public CartAdapter(Context context, int layout, List<CartDetail> foodlist) {
        this.context = context;
        this.layout = layout;
        this.food_cartlist = foodlist;
    }

    @Override
    public int getCount() {
        return food_cartlist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(layout,null);
        if(layout==R.layout.food_in_cart_detail) {
            TextView ten = (TextView) view.findViewById(R.id.food_name);
            TextView gia = (TextView) view.findViewById(R.id.price);
            TextView number = (TextView) view.findViewById(R.id.number);
            ImageView img = (ImageView) view.findViewById(R.id.img);
            CartDetail list = food_cartlist.get(position);

            ten.setText(list.getName());
            gia.setText(list.getPrice());
            number.setText(list.getNumber());
            img.setImageResource(list.getImage());
        }

        return view;
    }
}