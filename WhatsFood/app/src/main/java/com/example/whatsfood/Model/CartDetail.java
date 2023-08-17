package com.example.whatsfood.Model;

public class CartDetail {
    private int image;
    private String name;
    private int price;
    private int number;

    public CartDetail(int image, String name, int price, int number) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.number = number;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
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
