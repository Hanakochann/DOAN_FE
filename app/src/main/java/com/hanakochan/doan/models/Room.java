package com.hanakochan.doan.models;

public class Room {
    private String username;
    private String gender;
    private String price;
    private String type;
    private String city;
    private String district;
    private String street;
    private String number;
    private String image;
    public Room(){

    }

    public Room(String price, String type, String city, String district, String street, String number, String image, String username, String gender ) {
        this.price = price;
        this.type = type;
        this.city = city;
        this.district = district;
        this.street = street;
        this.number = number;
        this.image = image;
        this.username = username;
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public String getGender() {
        return gender;
    }

    public String getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getImage() {
        return image;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
