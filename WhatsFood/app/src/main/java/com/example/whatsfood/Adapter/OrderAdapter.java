package com.example.whatsfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.whatsfood.Model.Order;
import com.example.whatsfood.R;

import java.util.List;

public class OrderAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Order> orderList;

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
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        TextView orderId, buyerName, address, price;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            viewHolder.orderId = (TextView) view.findViewById(R.id.result);
            viewHolder.address = (TextView) view.findViewById(R.id.address);
            viewHolder.buyerName = (TextView) view.findViewById(R.id.buyersName);
            viewHolder.price = (TextView) view.findViewById(R.id.priceAmount);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Order order = orderList.get(i);
        viewHolder.orderId.setText(order.getOrderId());
        viewHolder.address.setText(order.getShip_to());
        viewHolder.buyerName.setText(order.getBuyerName());
        viewHolder.price.setText(order.getPrice());
        return view;
    }
}
