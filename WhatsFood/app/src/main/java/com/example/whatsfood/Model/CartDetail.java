package com.example.whatsfood.Model;

public class CartDetail {
    private String foodId;

    private String imageUrl;
    private String name;

    private int price;
    private int number;

    public CartDetail(String foodId, String imageUrl, String name, int price, int number) {
        this.foodId = foodId;
        this.imageUrl = imageUrl;
        this.name = name;
        this.price = price;
        this.number = number;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


}
