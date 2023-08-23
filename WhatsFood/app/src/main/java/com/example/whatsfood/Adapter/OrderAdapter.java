package com.example.whatsfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.whatsfood.Model.CartDetail;
import com.example.whatsfood.Model.Food;
import com.example.whatsfood.Model.Order;
import com.example.whatsfood.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderAdapter extends BaseAdapter {

    private final Context context;
    private final int layout;
    private final List<Order> orderList;

    public OrderAdapter(Context context, int layout, List<Order> orderList) {
        this.context = context;
        this.layout = layout;
        this.orderList = orderList;
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int i) {
        return orderList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        TextView orderId, buyerName, address, price, status, foodNameList;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            if (layout == R.layout.order_placeholder_seller) {
                viewHolder.orderId = (TextView) view.findViewById(R.id.result);
                viewHolder.address = (TextView) view.findViewById(R.id.address);
                viewHolder.buyerName = (TextView) view.findViewById(R.id.buyersName);
                viewHolder.price = (TextView) view.findViewById(R.id.priceAmount);
            } else if (layout == R.layout.order_placeholder_buyer) {
                viewHolder.status = (TextView) view.findViewById(R.id.status_text_view_order_placeholder_buyer);
                viewHolder.price = (TextView) view.findViewById(R.id.total_price_order_placeholder_buyer);
                viewHolder.orderId = (TextView) view.findViewById(R.id.order_id_text_view_order_placeholder_buyer);
                viewHolder.foodNameList = (TextView) view.findViewById(R.id.food_name_list_order_placeholder_buyer);
            }

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Order order = orderList.get(i);
        if (layout == R.layout.order_placeholder_seller) {
            viewHolder.address.setText(order.getShip_to());
            viewHolder.buyerName.setText(order.getBuyerName());
            viewHolder.orderId.setText(order.getOrderId());
            viewHolder.price.setText("" + order.getTotalMoney());
        } else if (layout == R.layout.order_placeholder_buyer) {
            viewHolder.orderId.setText(order.getOrderId());
            viewHolder.price.setText("" + order.getTotalMoney());
            viewHolder.status.setText(order.getStatus());
            ArrayList<CartDetail> foodList = order.getFoodList();
            String foodNameList = "Food List: ";
            for (int j = 0; j < foodList.size() - 1; j++) {
                foodNameList += foodList.get(j).getName() + ", ";
            }
            foodNameList += foodList.get(foodList.size() - 1).getName();
            viewHolder.foodNameList.setText(foodNameList);
        }

        return view;
    }
}
