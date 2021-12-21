package com.example.shopondoor.models;

import java.io.Serializable;

public class ViewAllModel implements Serializable {

    String name;
    String type;
    String price;
    String description;
    String img_url;
    String discount;
    int priceint;

    public ViewAllModel() {
    }

    public ViewAllModel(String name,String type, String price, String description, String img_url, String discount,int priceint) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.description = description;
        this.img_url = img_url;
        this.discount = discount;
        this.priceint=priceint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public int getPriceint() {
        return priceint;
    }

    public void setPriceint(int priceint) {
        this.priceint = priceint;
    }
}
