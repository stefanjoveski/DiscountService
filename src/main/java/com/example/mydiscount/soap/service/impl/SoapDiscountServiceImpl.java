package com.example.mydiscount.soap.service.impl;

import com.example.mydiscount.dao.DiscountDao;
import com.example.mydiscount.dao.DiscountTypeDao;
import com.example.mydiscount.entity.DiscountType;
import com.example.mydiscount.soap.*;
import com.example.mydiscount.soap.service.SoapDiscountService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class SoapDiscountServiceImpl implements SoapDiscountService {
    private DiscountDao discountDao;

    private DiscountTypeDao discountTypeDao;

    public SoapDiscountServiceImpl(DiscountDao discountDao, DiscountTypeDao discountTypeDao) {
        this.discountDao = discountDao;
        this.discountTypeDao = discountTypeDao;
    }

    @Override
    public GetDiscountByIdResponse getDiscountById(GetDiscountByIdRequest request) {
        Optional<Discount> discount = discountDao.findById(request.getDiscountId());
        GetDiscountByIdResponse response = new GetDiscountByIdResponse();
        if (discount.isPresent()) {
            response.setDiscount(discount.get());
        } else {
            response.setDiscount(null);
        }
        return response;
    }

    @Override
    public GetAllDiscountResponse getAllDiscounts(GetAllDiscountsRequest request) {
        GetAllDiscountResponse response = new GetAllDiscountResponse();
        response.setDiscounts(discountDao.findAll());
        return response;
    }

    @Override
    public AddDiscountResponse addDiscount(AddDiscountRequest request) {
        AddDiscountResponse response = new AddDiscountResponse();
        Long discountTypeId = request.getDiscount().getDiscountType().getDiscountTypeId();
        DiscountType discountType = discountTypeDao.findById(discountTypeId).orElseThrow(() -> new EntityNotFoundException("Discount type with id: " + discountTypeId + " not found"));
        Discount discount = request.getDiscount();

        discountDao.save(new Discount(discount.getCode(), discount.getDiscountPercentage(), discount.getStartDate(),
                discount.getEndDate(), discount.isStatus(), discountType));

        response.setStatus("Success");
        return response;
    }

    @Override
    public UpdateDiscountResponse updateDiscount(UpdateDiscountRequest request) {
        UpdateDiscountResponse response = new UpdateDiscountResponse();
        discountDao.findById(request.getDiscount().getDiscountId()).orElseThrow(() -> new EntityNotFoundException("Discount not found with ID: " + request.getDiscount().getDiscountId()));
        discountDao.save(request.getDiscount());
        response.setStatus("Success");
        return response;
    }

    @Override
    public DeleteDiscountResponse deleteDiscount(DeleteDiscountRequest request) {
        DeleteDiscountResponse response = new DeleteDiscountResponse();
        discountDao.deleteById(request.getDiscountId());
        response.setStatus("Success");
        return response;
    }
}
