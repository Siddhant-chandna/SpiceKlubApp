package com.example.shopondoor.models;

public class BannerModel {
    String img_url;
    public BannerModel() {
    }
    public BannerModel(String img_url){
        this.img_url=img_url;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
