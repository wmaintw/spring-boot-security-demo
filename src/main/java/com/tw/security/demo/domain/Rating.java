package com.tw.security.demo.domain;

public class Rating {
    private Integer id;
    private Integer star;
    private String comments;
    private Integer orderId;
    private Integer userId;

    public Rating() {
    }

    public Rating(Integer id, Integer star, String comments, Integer userId, Integer orderId) {
        this.id = id;
        this.star = star;
        this.comments = comments;
        this.userId = userId;
        this.orderId = orderId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", star=" + star +
                ", comments='" + comments + '\'' +
                ", orderId=" + orderId +
                ", userId=" + userId +
                '}';
    }
}
