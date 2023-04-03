package com.example.cart.Bean;

public class BuyBean {
    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public int getPro_count() {
        return pro_count;
    }

    public void setPro_count(int pro_count) {
        this.pro_count = pro_count;
    }

    public int getPro_cost() {
        return pro_cost;
    }

    public void setPro_cost(int pro_cost) {
        this.pro_cost = pro_cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int id;

    private String pro_name;
    private int pro_count;
    private int pro_cost;
}
