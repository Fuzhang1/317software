package com.example.a317soft.bean;

import java.util.Base64;

public class Commodity {
    private Integer id;
    private Integer user_id;
    private String title;
    private String description;
    private byte[] picture;

    public Commodity(Integer user_id, String title, String description, byte[] picture) {
        this.id = 0;
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.picture = picture;
    }

    public Commodity() {
        this.id = null;
        this.user_id = null;
        this.title = null;
        this.description = null;
        this.picture = null;

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
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

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
}
