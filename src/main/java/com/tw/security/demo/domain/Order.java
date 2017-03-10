package com.tw.security.demo.domain;

public class Order {
    private Integer id;
    private String content;
    private Integer ownerId;

    public Order() {
    }

    public Order(Integer id, String content, Integer ownerId) {
        this.id = id;
        this.content = content;
        this.ownerId = ownerId;
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

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", ownerId=" + ownerId +
                '}';
    }
}
