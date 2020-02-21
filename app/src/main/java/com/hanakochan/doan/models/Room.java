package com.hanakochan.doan.models;

import java.util.Date;

public class Room {
    private String username;
    private String id_post;
    private String gender;
    private String gender_roommate;
    private String price;
    private String lenght;
    private String width;
    private String type;
    private String slot_available;
    private String other;
    private String city;
    private String district;
    private String ward;
    private String street;
    private String number;
    private String image;
    private String image_user;
    private String time;
    private String id_user;
    private String phone;
    private String hometown;
    private String birthday;
    private String note;
    private String verified;

    public Room() {

    }

    public Room(String price, String type, String lenght, String width, String slot_available, String other,
                String city, String district, String ward, String street, String number, String image,
                String image_user, String username, String gender, String gender_roommate, String id_post,
                String time, String id_user, String phone, String hometown, String birthday, String note, String verified) {
        this.price = price;
        this.lenght = lenght;
        this.width = width;
        this.type = type;
        this.city = city;
        this.city = slot_available;
        this.city = other;
        this.district = district;
        this.ward = ward;
        this.street = street;
        this.number = number;
        this.image = image;
        this.image_user = image_user;
        this.username = username;
        this.gender = gender;
        this.gender_roommate = gender_roommate;
        this.id_post = id_post;
        this.time = time;
        this.id_user = id_user;
        this.hometown = hometown;
        this.phone = phone;
        this.birthday = birthday;
        this.note = note;
        this.verified = verified;
    }

    public String getUsername() {
        return username;
    }

    public String getGender() {
        return gender;
    }

    public String getGender_roommate() {
        return gender_roommate;
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

    public String getWard() {
        return ward;
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

    public String getLenght() {
        return lenght;
    }

    public String getWidth() {
        return width;
    }

    public String getPhone() {
        return phone;
    }

    public String getHometown() {
        return hometown;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getNote() {
        return note;
    }

    public String getVerified() {
        return verified;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setGender_roommate(String gender_roommate) {
        this.gender_roommate = gender_roommate;
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

    public void setWard(String ward) {
        this.ward = ward;
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

    public void setLenght(String lenght) {
        this.lenght = lenght;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }
}

