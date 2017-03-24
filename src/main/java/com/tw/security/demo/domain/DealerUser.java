package com.tw.security.demo.domain;

public class DealerUser {
    private Integer id;
    private Integer userId;
    private String name;
    private Integer storeId;

    public DealerUser() {
    }

    public DealerUser(Integer id, Integer userId, String name, Integer storeId) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.storeId = storeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    @Override
    public String toString() {
        return "DealerUser{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", storeId=" + storeId +
                '}';
    }
}
