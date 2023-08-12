package com.example.whatsfood.Model;

import com.example.whatsfood.Model.Food;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {
    private String orderId, buyerId, buyerName, sellerId, ship_to, price;
    private ArrayList<Food> foodList;
    int status;

    public Order(String orderId, String buyerId, String buyerName, String sellerId, String ship_to, String price, ArrayList<Food> foodList, int status) {
        this.orderId = orderId;
        this.buyerId = buyerId;
        this.buyerName = buyerName;
        this.sellerId = sellerId;
        this.ship_to = ship_to;
        this.price = price;
        this.foodList = foodList;
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getSellerId() {
        return sellerId;
    }

    public String getShip_to() {
        return ship_to;
    }

    public String getPrice() {
        return price;
    }

    public ArrayList<Food> getFoodList() {
        return foodList;
    }

    public int getStatus() {
        return status;
    }
}
