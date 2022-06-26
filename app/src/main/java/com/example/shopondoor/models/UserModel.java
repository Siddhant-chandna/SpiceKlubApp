package com.example.shopondoor.models;

import android.text.Editable;

public class UserModel {
    String name;
    String email;
    String password;
    String profileImg;
    String phone;
    String address;

    public UserModel() {
    }

    public UserModel(String userName, String userEmail,String userPhone, String userPassword) {

        this.name=userName;
        this.email=userEmail;
        this.phone=userPhone;
        this.password=userPassword;
    }

    public UserModel(String name, String email, String password, String profileImg, String phone, String address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profileImg = profileImg;
        this.phone = phone;
        this.address = address;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
