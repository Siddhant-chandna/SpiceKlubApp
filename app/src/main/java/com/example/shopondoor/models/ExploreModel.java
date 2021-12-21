package com.example.shopondoor.models;

public class ExploreModel {
    String name;
    String img_url;
    String type;
    int priceint;

    public ExploreModel() {
    }

    public ExploreModel(String name, String img_url, String type,int priceint) {
        this.name = name;
        this.img_url = img_url;
        this.type = type;
        this.priceint=priceint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
