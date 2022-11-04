package com.example.a317soft.bean;

public class Post {
    private Integer id;
    private Integer user_id;
    private Integer commodity_id;
    private Integer community_id;
    private String description;
    private String price;

    public Post(Integer user_id, Integer commodity_id, Integer community_id, String description, String price) {
        this.id = null;
        this.user_id = user_id;
        this.commodity_id = commodity_id;
        this.community_id = community_id;
        this.description = description;
        this.price = price;
    }

    public Post() {
        this.id = null;
        this.user_id = null;
        this.commodity_id = null;
        this.community_id = null;
        this.description = null;
        this.price = null;
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

    public Integer getCommodity_id() {
        return commodity_id;
    }

    public void setCommodity_id(Integer commodity_id) {
        this.commodity_id = commodity_id;
    }

    public Integer getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(Integer community_id) {
        this.community_id = community_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
