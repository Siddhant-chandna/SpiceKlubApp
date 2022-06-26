package com.example.shopondoor.models;

import java.io.Serializable;

public class CatDetailModel implements Serializable {
    String name;
    String price;
    String img_url;
    String type;
    String typedeatil;
    int pricehalf;
    int pricefull;
    String description;

    public CatDetailModel() {
    }

    public CatDetailModel(String name, String price, String img_url, String type, String typedeatil, int pricehalf,int pricefull, String description) {
        this.name = name;
        this.price = price;
        this.img_url = img_url;
        this.type = type;
        this.typedeatil = typedeatil;
        this.pricehalf = pricehalf;
        this.pricefull=pricefull;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPricehalf() {
        return pricehalf;
    }

    public void setPricehalf(int pricehalf) {
        this.pricehalf = pricehalf;
    }
    public int getPricefull() {
        return pricefull;
    }

    public void setPricefull(int pricefull) {
        this.pricefull = pricefull;
    }

    public String getTypedeatil() {
        return typedeatil;
    }

    public void setTypedeatil(String typedeatil) {
        this.typedeatil = typedeatil;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
