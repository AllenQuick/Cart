package com.example.cart.Bean;

public class CartDbBean {
    public int getUser_loginNum() {
        return user_loginNum;
    }

    public void setUser_loginNum(int user_loginNum) {
        this.user_loginNum = user_loginNum;
    }

    public String getUser_fruit() {
        return user_fruit;
    }

    public void setUser_fruit(String user_fruit) {
        this.user_fruit = user_fruit;
    }

    private int user_loginNum;
    private String user_fruit;
}
