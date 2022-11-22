package com.example.a317soft.bean;

import java.io.Serializable;

public class UserInfo implements Serializable {

    private Integer id;
    private String username;
    private String QQ;
    private String phone;
    private byte[] profile;

    public UserInfo(){

    }

    public UserInfo(Integer id, String username, String QQ, String phone, byte[] profile) {
        this.id = id;
        this.username = username;
        this.QQ = QQ;
        this.phone = phone;
        this.profile = profile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public byte[] getProfile() {
        return profile;
    }

    public void setProfile(byte[] profile) {
        this.profile = profile;
    }
}
