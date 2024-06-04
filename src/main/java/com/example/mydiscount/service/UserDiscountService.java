package com.example.mydiscount.service;

import com.example.mydiscount.entity.UserDiscount;

import java.util.List;

public interface UserDiscountService {

    List<UserDiscount> fetchUserDiscountsByUserId(Long userId);
    List<UserDiscount> fetchAll();
    List<UserDiscount> fetchUserDiscountsByDiscountId(Long discountId);

    void activate(Long userId, Long discountId);
    void deactivate(Long userId, Long discountId);
    boolean isNotPresentAndStatusIsActive(Long userId, Long discountId);

    List<UserDiscount> fetchCurrentActiveDiscountsByUserId(Long userId);


}
