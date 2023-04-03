package com.example.cart.Bean;

public class CartBean {
    public int getCart_shopPrice() {
        return cart_shopPrice;
    }

    public void setCart_shopPrice(int cart_shopPrice) {
        this.cart_shopPrice = cart_shopPrice;
    }

    public int getCart_picture() {
        return cart_picture;
    }

    public void setCart_picture(int cart_picture) {
        this.cart_picture = cart_picture;
    }

    private int cart_picture;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCart_name() {
        return cart_name;
    }

    public void setCart_name(String cart_name) {
        this.cart_name = cart_name;
    }

    public int getCart_count() {
        return cart_count;
    }

    public void setCart_count(int cart_count) {
        this.cart_count = cart_count;
    }

    public int getItem_sum() {
        return item_sum;
    }

    public void setItem_sum(int item_sum) {
        this.item_sum = item_sum;
    }

    public boolean isItem_check() {
        return item_check;
    }

    public void setItem_check(boolean item_check) {
        this.item_check = item_check;
    }
    private int id;
    private String cart_name;
    private int cart_count;
    private int cart_shopPrice;
    private int item_sum;
    private boolean item_check;
}
