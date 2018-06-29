package com.rhernandez.social_ruth.models;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by Roberto Hernandez on 6/24/2018.
 */

public class PostEntity implements Serializable {
    private String userPhoto;
    private String userName;
    private String image;
    private String path;
    private String title;
    private String description;
    private int likes;
    private boolean add;

    public PostEntity() {
    }

    public PostEntity(String userPhoto, String userName, String image, String title, String description, int likes) {
        this.userPhoto = userPhoto;
        this.userName = userName;
        this.image = image;
        this.title = title;
        this.description = description;
        this.likes = likes;
    }

    public PostEntity(String userPhoto, String userName, String title, String description, int likes) {
        this.userPhoto = userPhoto;
        this.userName = userName;
        this.title = title;
        this.description = description;
        this.likes = likes;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isAdd() {
        return add;
    }

    public void setAdd(boolean add) {
        this.add = add;
        likes = add ? likes + 1 : likes - 1;
    }
}
