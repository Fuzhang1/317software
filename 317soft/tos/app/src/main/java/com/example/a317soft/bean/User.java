package com.example.a317soft.bean;

import java.io.Serializable;

public class User implements Serializable {

    private Integer id;
    private String username;
    private String password;

    public User(String username, String password){
        this.id = 0;
        this.username = username;
        this.password = password;
    }

    public User(){
        this.id = 0;
        this.username = null;
        this.password = null;
    }

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}
}
