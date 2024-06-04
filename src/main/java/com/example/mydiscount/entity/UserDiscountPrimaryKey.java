package com.example.mydiscount.entity;

import com.example.mydiscount.soap.Discount;

import java.io.Serializable;

public class UserDiscountPrimaryKey implements Serializable {
    private User user;
    private Discount discount;

    public UserDiscountPrimaryKey() {
    }

    public UserDiscountPrimaryKey(User user, Discount discount) {
        this.user = user;
        this.discount = discount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }
}
