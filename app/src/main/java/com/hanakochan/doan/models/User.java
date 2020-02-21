package com.hanakochan.doan.models;

public class User {
    String username;
    String birthday;
    String hometown;
    String gender;
    String phone;
    String id;
    String photo;
    public User() {

    }
    public User(String username, String birthday, String hometown, String gender, String phone, String id, String photo){
        this.username = username;
        this.birthday = birthday;
        this.hometown = hometown;
        this.gender = gender;
        this.phone = phone;
        this.id = id;
        this.photo = photo;
    }


    public String getUsername() {
        return username;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getHometown() {
        return hometown;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getId() {
        return id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
