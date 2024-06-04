package com.example.mydiscount.soap.service;
import com.example.mydiscount.soap.*;

public interface SoapDiscountService {

    GetDiscountByIdResponse getDiscountById(GetDiscountByIdRequest request);
    DeleteDiscountResponse deleteDiscount(DeleteDiscountRequest request);

    GetAllDiscountResponse getAllDiscounts(GetAllDiscountsRequest request);

    AddDiscountResponse addDiscount(AddDiscountRequest request);

    UpdateDiscountResponse updateDiscount(UpdateDiscountRequest request);

}
