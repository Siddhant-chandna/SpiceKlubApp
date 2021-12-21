package com.example.shopondoor.models;

public class CatDetailModel {
    String name;
    String price;
    String img_url;
    String type;
    String typedeatil;
    int priceint;

    public CatDetailModel() {
    }

    public CatDetailModel(String name, String price, String img_url, String type, String typedeatil, int priceint) {
        this.name = name;
        this.price = price;
        this.img_url = img_url;
        this.type = type;
        this.typedeatil = typedeatil;
        this.priceint = priceint;
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

    public int getPriceint() {
        return priceint;
    }

    public void setPriceint(int priceint) {
        this.priceint = priceint;
    }

    public String getTypedeatil() {
        return typedeatil;
    }

    public void setTypedeatil(String typedeatil) {
        this.typedeatil = typedeatil;
    }
}
