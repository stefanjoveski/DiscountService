package com.example.mydiscount.service.impl;

import com.example.mydiscount.dao.DiscountDao;
import com.example.mydiscount.dao.DiscountTypeDao;
import com.example.mydiscount.dao.UserDao;
import com.example.mydiscount.soap.Discount;
import com.example.mydiscount.entity.DiscountType;
import com.example.mydiscount.entity.User;
import com.example.mydiscount.service.DiscountService;
import com.example.mydiscount.service.UserDiscountService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
@Service
@Transactional
public class DiscountServiceImpl implements DiscountService {
    private DiscountDao discountDao;
    private UserDao userDao;
    private DiscountTypeDao discountTypeDao;

    private UserDiscountService userDiscountService;

    public DiscountServiceImpl(DiscountDao discountDao, UserDao userDao, DiscountTypeDao discountTypeDao, UserDiscountService userDiscountService) {
        this.discountDao = discountDao;
        this.userDao = userDao;
        this.discountTypeDao = discountTypeDao;
        this.userDiscountService = userDiscountService;
    }

    @Override
    public Discount loadDiscountById(Long discountId) {
        return discountDao.findById(discountId).orElseThrow(() -> new EntityNotFoundException("Discount with id: " + discountId + " not found"));
    }

    @Override
    public Discount createDiscount(String code, Integer discountPercentage, Date startDate, Date endDate, boolean status, Long discountTypeId){
        DiscountType discountType = discountTypeDao.findById(discountTypeId).orElseThrow(() -> new EntityNotFoundException("Discount type with id: " + discountTypeId + " not found"));
        return discountDao.save(new Discount(code, discountPercentage, startDate, endDate, status, discountType));
    }

    @Override
    public Discount upsertDiscount(Discount discount) {
        if(discount.getStartDate().after(discount.getEndDate())){
            throw new RuntimeException("Start Date must not be after End Date");
        }
        return discountDao.save(discount);
    }

    @Override
    public List<Discount> findDiscountsByCode(String keyword) {
        return discountDao.findDiscountsByCode(keyword);
    }

    @Override
    public void activate(Long userId, Long discountId) {
        Discount discount = discountDao.findById(discountId).orElseThrow(() -> new EntityNotFoundException("Discount with id " + discountId + " not found"));
        User user = userDao.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        userDiscountService.activate(user.getUserId(), discount.getDiscountId());
    }
    @Override
    public void deactivate(Long userId, Long discountId) {
        Discount discount = discountDao.findById(discountId).orElseThrow(() -> new EntityNotFoundException("Discount with id " + discountId + " not found"));
        User user = userDao.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        userDiscountService.deactivate(user.getUserId(), discount.getDiscountId());
    }
    @Override
    public List<Discount> fetchAll() {
        return discountDao.findAll();
    }

    @Override
    public List<Discount> fetchAllWithPercentage(Integer percentage) {
        return discountDao.fetchActiveDiscountsWithPercentage(percentage);
    }

    @Override
    public List<Discount> fetchDiscountsForUser(Long userId) {
        return discountDao.fetchActiveDiscountsForUser(userId);
    }

    @Override
    public List<Discount> fetchActiveDiscountsForUser(Long userId) {
        return discountDao.fetchActiveDiscountsForUser(userId);
    }
    @Override
    public List<Discount> fetchActiveDiscountsForUserWithPercentage(Long userId, Integer percentage) {
        return discountDao.fetchActiveDiscountsForUserWithPercentage(userId, percentage);
    }

    @Override
    public List<Discount> fetchAllWithActiveStatusDiscounts(Integer percentage) {
        return discountDao.findDiscountsByStatusAndPercentage(true, percentage);
    }
    @Override
    public List<Discount> fetchAllWithActiveStatusDiscounts() {
        return discountDao.findDiscountsByStatus(true);
    }

    @Override
    public void removeDiscount(Long discountId) {
        discountDao.deleteById(discountId);
    }

    @Override
    public List<Discount> loadDiscountByType(Long discountTypeId) {
        return discountDao.findDiscountsByDiscountType(discountTypeId);
    }
}
