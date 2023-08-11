package com.example.whatsfood.Model;

public class Food {
    String foodId, name, description, price, imageUrl, quantity, sellerId, sellerName;
    String[] comments;

    public Food() {
    }

    public Food(String foodId, String name, String description, String price, String imageUrl, String quantity, String sellerId, String sellerName, String[] comments) {
        this.foodId = foodId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.comments = comments;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
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

    public String[] getComments() {
        return comments;
    }

    public String getSellerName() {
        return sellerName;
    }
}
