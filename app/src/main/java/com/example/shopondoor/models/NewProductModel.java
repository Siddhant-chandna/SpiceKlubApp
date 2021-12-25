package com.example.shopondoor.models;

import java.io.Serializable;

public class NewProductModel implements Serializable {
    String name;
    String description;
    String price;
    String image_url;
    int priceint;

    public NewProductModel() {
    }

    public NewProductModel(String name, String description, String price, String image_url, int priceint) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image_url = image_url;
        this.priceint = priceint;
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

    public int getPriceint() {
        return priceint;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setPriceint(int priceint) {
        this.priceint = priceint;
    }
}
