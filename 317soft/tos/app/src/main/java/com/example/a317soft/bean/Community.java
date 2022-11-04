package com.example.a317soft.bean;

public class Community {
    private int id;
    private String title;
    private String description;
    private byte[] picture;
    private int memberCount;

    public Community(String title, String description, byte[] picture){
        this.id = 0;
        this.title = title;
        this.description = description;
        this.picture = picture;
        this.memberCount = 1;
    }

    public Community(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }
}
