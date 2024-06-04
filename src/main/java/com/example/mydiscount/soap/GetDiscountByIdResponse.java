package com.example.mydiscount.soap;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "discount"
})
@XmlRootElement(name = "getDiscountByIdResponse", namespace = "http://localhost.com/dicount")
public class GetDiscountByIdResponse {
    @XmlElement(required = true)
    protected Discount discount;

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }
}
