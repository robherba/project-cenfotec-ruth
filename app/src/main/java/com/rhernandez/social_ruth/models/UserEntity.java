package com.rhernandez.social_ruth.models;

/**
 * Created by Roberto Hernandez on 6/27/2018.
 */

public class UserEntity {
    private String image;
    private String name;
    private String phone;
    private String state;

    public UserEntity() {

    }

    public UserEntity(String image, String name, String phone, String state) {
        this.image = image;
        this.name = name;
        this.phone = phone;
        this.state = state;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
