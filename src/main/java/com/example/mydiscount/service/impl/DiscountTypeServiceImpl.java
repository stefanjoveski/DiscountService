package com.example.mydiscount.service.impl;

import com.example.mydiscount.dao.DiscountTypeDao;
import com.example.mydiscount.dao.UserDao;
import com.example.mydiscount.soap.Discount;
import com.example.mydiscount.entity.DiscountType;
import com.example.mydiscount.service.DiscountService;
import com.example.mydiscount.service.DiscountTypeService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class DiscountTypeServiceImpl implements DiscountTypeService {
    private DiscountTypeDao discountTypeDao;
    private UserDao userDao;
    private DiscountService discountService;

    public DiscountTypeServiceImpl(DiscountTypeDao discountTypeDao, UserDao userDao, DiscountService discountService) {
        this.discountTypeDao = discountTypeDao;
        this.userDao = userDao;
        this.discountService = discountService;
    }

    @Override
    public DiscountType loadDiscountTypeById(Long discountTypeId) {
        return discountTypeDao.findById(discountTypeId).orElseThrow(()-> new EntityNotFoundException("Discount type not found with id: " + discountTypeId));
    }

    @Override
    public DiscountType createDiscountType(String category) {
        return discountTypeDao.save(new DiscountType(category));
    }

    @Override
    public void upsertDiscountType(DiscountType discountType) {
        discountTypeDao.save(discountType);
    }

    @Override
    public List<DiscountType> fetchAll() {
        return discountTypeDao.findAll();
    }

    @Override
    public List<DiscountType> findDiscountTypeByCategory(String category) {
        return discountTypeDao.findDiscountTypesByCategory(category);
    }

    @Override
    public void removeDiscountType(Long discountTypeId) {
        DiscountType discountType = loadDiscountTypeById(discountTypeId);
        for (Discount discount : discountType.getDiscounts()){
            discountService.removeDiscount(discount.getDiscountId());
        }
        discountTypeDao.deleteById(discountTypeId);
    }

    @Override
    public List<Long> fetchDiscountTypesIds() {
        return discountTypeDao.findAll().stream().map(DiscountType::getDiscountTypeId).toList();
    }

    @Override
    public List<DiscountType> fetchWithAtLeastOneDiscount() {
        return discountTypeDao.findAllWithAtLeastOneDiscount();
    }

}
