package com.hanakochan.doan.models;

public class Room {
    private String username;
    private String id_post;
    private String gender;
    private String price;
    private String type;
    private String slot_available;
    private String other;
    private String city;
    private String district;
    private String street;
    private String number;
    private String image;
    private String image_user;
    private String time;
    private String id_user;
    public Room(){

    }

    public Room(String price, String type, String slot_available, String other, String city, String district, String street, String number, String image, String image_user, String username, String gender, String id_post, String time, String id_user ) {
        this.price = price;
        this.type = type;
        this.city = city;
        this.city = slot_available;
        this.city = other;
        this.district = district;
        this.street = street;
        this.number = number;
        this.image = image;
        this.image_user = image_user;
        this.username = username;
        this.gender = gender;
        this.id_post = id_post;
        this.time = time;
        this.id_user = id_user;
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

    public String getSlot_available() {
        return slot_available;
    }

    public String getOther() {
        return other;
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

    public String getImage_user() {
        return image_user;
    }

    public String getId_post() {
        return id_post;
    }

    public String getTime() {
        return time;
    }

    public String getId_user() {
        return id_user;
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

    public void setSlot_available(String slot_available) {
        this.slot_available = slot_available;
    }

    public void setOther(String other) {
        this.other = other;
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

    public void setImage_user(String image_user) {
        this.image_user = image_user;
    }

    public void setId_post(String id_post) {
        this.id_post = id_post;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }
}
