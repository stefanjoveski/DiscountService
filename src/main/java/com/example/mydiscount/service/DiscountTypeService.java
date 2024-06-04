package com.example.mydiscount.service;

import com.example.mydiscount.entity.DiscountType;

import java.util.List;

public interface DiscountTypeService {
    DiscountType loadDiscountTypeById(Long discountTypeId);
    DiscountType createDiscountType(String category);
    void upsertDiscountType(DiscountType discountType);
    List<DiscountType> fetchAll();
    List<DiscountType> findDiscountTypeByCategory(String category);
    void removeDiscountType(Long discountTypeId);
    List<Long> fetchDiscountTypesIds();

    List<DiscountType> fetchWithAtLeastOneDiscount();
}
