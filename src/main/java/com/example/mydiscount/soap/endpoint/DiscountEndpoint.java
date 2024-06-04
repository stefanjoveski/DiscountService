package com.example.mydiscount.soap.endpoint;

import com.example.mydiscount.soap.*;
import com.example.mydiscount.soap.service.SoapDiscountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class DiscountEndpoint {
    private static final String NAMESPACE_URI = "http://localhost.com/discount";
    private final SoapDiscountService soapDiscountService;

    public DiscountEndpoint(SoapDiscountService soapDiscountService) {
        this.soapDiscountService = soapDiscountService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getDiscountByIdRequest")
    @ResponsePayload
    public GetDiscountByIdResponse getDiscountById(@RequestPayload GetDiscountByIdRequest request) {
        return soapDiscountService.getDiscountById(request);
    }
    @PostMapping("/ws/discount")

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllDiscountsRequest")
    @ResponsePayload
    public GetAllDiscountResponse getAllDiscounts(@RequestPayload GetAllDiscountsRequest request)
    {
        return soapDiscountService.getAllDiscounts(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteDiscountRequest")
    @ResponsePayload
    public DeleteDiscountResponse deleteDiscount(@RequestPayload DeleteDiscountRequest request)
    {
        return soapDiscountService.deleteDiscount(request);
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addDiscountRequest")
    @ResponsePayload
    public AddDiscountResponse addDiscount(@RequestPayload AddDiscountRequest request)
    {
        return soapDiscountService.addDiscount(request);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateDiscountRequest")
    @ResponsePayload
    public UpdateDiscountResponse updateDiscount(@RequestPayload UpdateDiscountRequest request)
    {
        return soapDiscountService.updateDiscount(request);
    }

}
