package com.example.mydiscount.service.impl;

import com.example.mydiscount.dao.DiscountDao;
import com.example.mydiscount.dao.UserDiscountDao;
import com.example.mydiscount.soap.Discount;
import com.example.mydiscount.entity.UserDiscount;
import com.example.mydiscount.service.UserDiscountService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserDiscountServiceImpl implements UserDiscountService {
    private UserDiscountDao userDiscountDao;
    private DiscountDao discountDao;
    public UserDiscountServiceImpl(UserDiscountDao userDiscountDao, DiscountDao discountDao) {
        this.userDiscountDao = userDiscountDao;
        this.discountDao = discountDao;
    }

    @Override
    public List<UserDiscount> fetchAll() {
        return userDiscountDao.findAll();
    }

    @Override
    public List<UserDiscount> fetchUserDiscountsByDiscountId(Long discountId) {
        return userDiscountDao.findByDiscountId(discountId);
    }

    @Override
    public List<UserDiscount> fetchUserDiscountsByUserId(Long userId) {
        return userDiscountDao.findByUserId(userId);
    }

    @Override
    public void activate(Long userId, Long discountId) {
        userDiscountDao.activate(userId, discountId);
    }

    @Override
    public void deactivate(Long userId, Long discountId) {
        userDiscountDao.deactivate(userId, discountId);
    }

    public boolean isNotPresentAndStatusIsActive(Long userId, Long discountId) {
        Optional<Discount> discount = discountDao.findById(discountId);
        // found discount with id but its status is not active
        if(discount.isPresent() && !discount.get().isStatus()){
            return false;
        }
        UserDiscount userDiscount = userDiscountDao.findByUserIdAndDiscountId(userId, discountId);
        if (userDiscount == null) {
            return true;
        }
        return false;
    }

    @Override
    public List<UserDiscount> fetchCurrentActiveDiscountsByUserId(Long userId) {
        return userDiscountDao.findCurrentActiveDiscountsByUserId(userId);
    }

}
