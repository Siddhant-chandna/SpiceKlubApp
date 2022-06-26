package com.example.shopondoor.models;

import java.io.Serializable;

public class RecomendedModel implements Serializable {
    String name;
    String img_url;
    String rating;
    String description;
    String price;
    int pricehalf;
    int pricefull;

    public RecomendedModel() {
    }

    public RecomendedModel(String name, String img_url, String rating, String description, String price,int pricehalf,int pricefull) {
        this.name = name;
        this.img_url = img_url;
        this.rating = rating;
        this.description = description;
        this.price = price;
        this.pricehalf=pricehalf;
        this.pricefull=pricefull;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
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

    public void setpricehalf(int pricehalf) {
        this.pricehalf = pricehalf;
    }
    public int getpricefull() {
        return pricefull;
    }

    public void setpricefull(int pricefull) {
        this.pricefull = pricefull;
    }
}
