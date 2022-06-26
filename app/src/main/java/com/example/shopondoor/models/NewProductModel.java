package com.example.shopondoor.models;

import java.io.Serializable;

public class NewProductModel implements Serializable {
    String name;
    String description;
    String price;
    String image_url;
    int pricehalf;
    int pricefull;

    public NewProductModel() {
    }

    public NewProductModel(String name, String description, String price, String image_url, int pricehalf,int pricefull) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image_url = image_url;
        this.pricehalf = pricehalf;
        this.pricefull = pricefull;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getpricehalf() {
        return pricehalf;
    }
    public int getpricefull() {
        return pricefull;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setpricefull(int pricefull) {
        this.pricefull = pricefull;
    }
}
