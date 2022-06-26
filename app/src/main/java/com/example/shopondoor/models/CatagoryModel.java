package com.example.shopondoor.models;

public class CatagoryModel {
    String name;
    String description;
    String discount;
    String img_url;
    String type;
    int pricehalf;
    int pricefull;

    public CatagoryModel() {
    }

    public CatagoryModel(String name, String type,String description, String discount, String img_url,int pricefull,int pricehalf) {
        this.name = name;
        this.description = description;
        this.discount = discount;
        this.img_url = img_url;
        this.type=type;
        this.pricefull=pricefull;
        this.pricehalf=pricehalf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
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
}
