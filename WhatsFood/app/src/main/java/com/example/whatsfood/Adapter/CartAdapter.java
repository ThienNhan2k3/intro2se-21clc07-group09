package com.example.whatsfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whatsfood.Model.CartDetail;
import com.example.whatsfood.Model.Food;
import com.example.whatsfood.R;
import com.squareup.picasso.Picasso;

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
            ImageView img = (ImageView) view.findViewById(R.id.food_image);
            ImageView minus=(ImageView) view.findViewById(R.id.minus);
            ImageView plus=(ImageView) view.findViewById(R.id.plus);
            CartDetail list = food_cartlist.get(position);
            Button remove = (Button) view.findViewById(R.id.remove);
            ten.setText(list.getName());
            gia.setText(String.valueOf(list.getPrice()));
            number.setText(String.valueOf(list.getNumber()));

            Picasso.get().load(list.getImageUrl()).into(img);
            // Thiết lập sự kiện cho nút "minus"
            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentNumber = list.getNumber();
                    if (currentNumber > 1) {
                        currentNumber--;
                        list.setNumber(currentNumber);
                        notifyDataSetChanged(); // Cập nhật giao diện sau khi thay đổi số lượng
                    }
                }
            });

            // Thiết lập sự kiện cho nút "plus"
            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentNumber = list.getNumber();
                    currentNumber++;
                    list.setNumber(currentNumber);
                    notifyDataSetChanged(); // Cập nhật giao diện sau khi thay đổi số lượng

                }
            });
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position >= 0 && position < food_cartlist.size()) {
                        food_cartlist.remove(position);
                        notifyDataSetChanged();
                    }
                }
            });

        }

        return view;
    }
}
