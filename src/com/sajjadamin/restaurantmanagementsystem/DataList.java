package com.sajjadamin.restaurantmanagementsystem;

public class DataList {

    private int id, price, quantity;
    private String item;

    public DataList(int id, String item, int price, int quantity) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.item = item;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getItem() {
        return item;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setItem(String item) {
        this.item = item;
    }

}
