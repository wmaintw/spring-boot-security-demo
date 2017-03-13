package com.tw.security.demo.aop;

public class TargetResourceId {
    private String id;

    public TargetResourceId() {
    }

    public TargetResourceId(String id) {
        this.id = id;
    }

    public String get() {
        return id;
    }

    @Override
    public String toString() {
        return "TargetResourceId{" +
                "id='" + id + '\'' +
                '}';
    }
}
