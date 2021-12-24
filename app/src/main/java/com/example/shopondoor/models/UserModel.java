package com.example.shopondoor.models;

public class UserModel {
    String name;
    String email;
    String password;
    String profileImg;
    int phone;
    String address;

    public UserModel() {
    }

    public UserModel(String name, String email, String password, String profileImg, int phone, String address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profileImg = profileImg;
        this.phone = phone;
        this.address = address;
    }

    public UserModel(String userName, String userEmail, String userPassword) {
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

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
