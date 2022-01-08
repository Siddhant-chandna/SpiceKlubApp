package com.example.shopondoor.models;

import java.io.Serializable;

public class ViewOrderModel implements Comparable<ViewOrderModel>{
    String productName;
    String productImage;
    int productPriceint;
    String productPrice;
    String totalQuantity;
    int totalPrice;
    String documentId;
    String currentDate;
    String currentTime;
    double totaldiscountPrice;
    String OrderStatus;

    public ViewOrderModel() {
    }

    public ViewOrderModel(String productName, String productImage, int productPriceint, String productPrice, String totalQuantity, int totalPrice, String documentId, String currentDate, String currentTime, double totaldiscountPrice, String orderStatus) {
        this.productName = productName;
        this.productImage = productImage;
        this.productPriceint = productPriceint;
        this.productPrice = productPrice;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
        this.documentId = documentId;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.totaldiscountPrice = totaldiscountPrice;
        OrderStatus = orderStatus;
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

    public void setProductPriceint(int productPriceint) {
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

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
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

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

    public int compareTo(ViewOrderModel o) {
        if (!this.getCurrentDate().equals(o.getCurrentDate())) {
            int b=this.getCurrentDate().compareTo(o.getCurrentDate());
            return b>=0 ? -1 : 1;
        }
        int c=this.getCurrentTime().compareTo(o.getCurrentTime());
        return c>=0 ? -1:1;
    }
}
