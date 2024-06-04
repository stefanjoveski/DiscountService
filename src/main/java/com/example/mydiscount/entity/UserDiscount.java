package com.example.mydiscount.entity;

import com.example.mydiscount.soap.Discount;

import javax.persistence.*;

@Entity
@Table(name = "user_discounts")
@IdClass(UserDiscountPrimaryKey.class)
public class UserDiscount {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "discount_id", nullable = false)
    private Discount discount;

    public UserDiscount() {
    }

    public UserDiscount(User user, Discount discount) {
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
