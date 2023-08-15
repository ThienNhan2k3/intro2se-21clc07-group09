package com.example.whatsfood.Model;

import android.widget.Adapter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;

public class Food implements Serializable {
    String foodId, name, description, price, imageUrl, quantity, sellerId, storeName;
    ArrayList<String> comments;

    public Food() {
    }

    public Food(String foodId, String name, String description, String price, String imageUrl, String quantity, String sellerId, String storeName, ArrayList<String> comments) {
        this.foodId = foodId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.comments = comments;
        this.sellerId = sellerId;
        this.storeName = storeName;
    }

    public String getFoodId() {
        return foodId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getSellerId() {
        return sellerId;
    }

    public String getStoreName() {
        return storeName;
    }


    public ArrayList<String> getComments() {
        return comments;
    }



    public void writeToFirebase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Food").child(this.sellerId).child(this.foodId);
        databaseReference.setValue(this);
    }

}
