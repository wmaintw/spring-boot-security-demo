package com.tw.security.demo.domain;

public class Order {
    private Integer id;
    private String content;
    private Integer ownerId;
    private Integer storeId;
    private String status;

    public Order() {
    }

    public Order(Integer id, String content, Integer ownerId, Integer storeId) {
        this.id = id;
        this.content = content;
        this.ownerId = ownerId;
        this.storeId = storeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", ownerId=" + ownerId +
                ", storeId=" + storeId +
                ", status='" + status + '\'' +
                '}';
    }
}
