package com.example.whatsfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whatsfood.Model.Food;
import com.example.whatsfood.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodAdapter extends BaseAdapter {
    Context context;
    private int layout;
    List<Food> foodList;

    public FoodAdapter(Context context, int layout, List<Food> foodList) {
        this.context = context;
        this.layout = layout;
        this.foodList = foodList;
    }

    public FoodAdapter() {
    }

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class FoodViewHolder {
        TextView foodName, store, price;
        ImageView foodImageView;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        FoodViewHolder foodViewHolder = null;
        if (view == null) {
            foodViewHolder = new FoodViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.food_holder_buyer, null);
            foodViewHolder.foodImageView = (ImageView) view.findViewById(R.id.img_view_food_holder_buyer);
            foodViewHolder.foodName = (TextView) view.findViewById(R.id.food_name_food_holder_buyer);
            foodViewHolder.store = (TextView) view.findViewById(R.id.store_food_holder_buyer);
            foodViewHolder.price = (TextView) view.findViewById(R.id.price_food_holder_buyer);
            view.setTag(foodViewHolder);
        } else {
            foodViewHolder = (FoodViewHolder) view.getTag();
        }

        Food food = foodList.get(i);
        foodViewHolder.foodName.setText(food.getName());
        foodViewHolder.store.setText(food.getSellerName());
        foodViewHolder.price.setText(food.getPrice());
        Picasso.get()
                .load(food.getImageUrl())
                .into(foodViewHolder.foodImageView);
        return view;
    }
}
