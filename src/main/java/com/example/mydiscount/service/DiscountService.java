package com.example.mydiscount.service;

import com.example.mydiscount.soap.Discount;

import java.util.Date;
import java.util.List;

public interface DiscountService {
    Discount loadDiscountById(Long discountId);
    Discount createDiscount(String code, Integer discountPercentage, Date startDate, Date endDate, boolean status, Long discountTypeId);
    Discount upsertDiscount(Discount discount);
    List<Discount> findDiscountsByCode(String name);
    void activate(Long userId, Long discountId);
    void deactivate(Long userId, Long discountId);
    List<Discount> fetchAll();
    List<Discount> fetchAllWithPercentage(Integer percentage);
    List<Discount> fetchDiscountsForUser(Long userId);
    List<Discount> fetchActiveDiscountsForUser(Long userId);
    List<Discount> fetchActiveDiscountsForUserWithPercentage(Long userId, Integer percentage);
    List<Discount> fetchAllWithActiveStatusDiscounts();
    List<Discount> fetchAllWithActiveStatusDiscounts(Integer percentage);
    void removeDiscount(Long discountId);
    List<Discount> loadDiscountByType(Long discountTypeId);
}
