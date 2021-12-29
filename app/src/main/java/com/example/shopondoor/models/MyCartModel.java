package com.example.shopondoor.models;

import java.io.Serializable;

public class MyCartModel implements Serializable {
    String productName;
    String productImage;
    int productPriceint;
    String productPrice;
    String totalQuantity;
    int totalPrice;
    String documentId;
    String currentDate;
    String currentTime;
    String description;
    double totaldiscountPrice;

    public MyCartModel() {
    }

    public MyCartModel(String productName, String productImage, int productPriceint, String productPrice, String totalQuantity, int totalPrice, String documentId, String currentDate, String currentTime, String description, double totaldiscountPrice) {
        this.productName = productName;
        this.productImage = productImage;
        this.productPriceint = productPriceint;
        this.productPrice = productPrice;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
        this.documentId = documentId;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.description = description;
        this.totaldiscountPrice = totaldiscountPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description=description;
    }

    public void setProductPriceint(int productPriceint) {
        this.productPriceint = productPriceint;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getProductPriceint() {
        return productPriceint;
    }

    public void setProductPricint(int productPrice) {
        this.productPriceint = productPriceint;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public double getTotaldiscountPrice() {
        return totaldiscountPrice;
    }

    public void setTotaldiscountPrice(double totaldiscountPrice) {
        this.totaldiscountPrice = totaldiscountPrice;
    }
}
