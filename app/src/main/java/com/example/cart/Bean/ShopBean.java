package com.example.cart.Bean;

public class ShopBean {
    private int id;
    private String pro_name;
    private int pro_count;
    private String pro_type;

    public int getPro_hasBuyCount() {
        return pro_hasBuyCount;
    }

    public void setPro_hasBuyCount(int pro_hasBuyCount) {
        this.pro_hasBuyCount = pro_hasBuyCount;
    }

    private int pro_hasBuyCount;//该水果购买的次数

    public int getPro_picture() {
        return pro_picture;
    }

    public void setPro_picture(int pro_picture) {
        this.pro_picture = pro_picture;
    }

    private int pro_picture;

    public String getPro_type() {
        return pro_type;
    }

    public void setPro_type(String pro_type) {
        this.pro_type = pro_type;
    }



    public int getPro_shopPrice() {
        return pro_shopPrice;
    }

    public void setPro_shopPrice(int pro_shopPrice) {
        this.pro_shopPrice = pro_shopPrice;
    }

    private int pro_shopPrice;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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


    /*      "pro_name text,"+
                    "pro_shopPrice integer,"+
                    "pro_count integer,"+
                    "pro_checkbox integer)";*/

}
