package com.example.cart.moudle;

import java.io.Serializable;

public class shoppingCart implements Serializable {
    private String proImg;
    private String ProName;
    private String shopPrice;
    private String markPrice;
    private String proCount;
    public String getProImg() {
        return proImg;
    }
    public void setProImg(String proImg) {
        this.proImg = proImg;
    }
    public String getProName() {
        return ProName;
    }
    public void setProName(String proName) {
        ProName = proName;
    }
    public String getShopPrice() {
        return shopPrice;
    }
    public void setShopPrice(String shopPrice) {
        this.shopPrice = shopPrice;
    }
    public String getMarkPrice() {
        return markPrice;
    }
    public void setMarkPrice(String markPrice) {
        this.markPrice = markPrice;
    }
    public String getProCount() {
        return proCount;
    }
    public void setProCount(String proCount) {
        this.proCount = proCount;
    }
}
