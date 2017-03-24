package com.tw.security.demo.domain;

import java.util.ArrayList;
import java.util.List;

public class Dealer {
    private Integer id;
    private String name;
    private List<Store> stores = new ArrayList<>();

    public Dealer() {
    }

    public Dealer(Integer id, String name, List<Store> stores) {
        this.id = id;
        this.name = name;
        this.stores = stores;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    @Override
    public String toString() {
        return "Dealer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stores=" + stores +
                '}';
    }
}
