package com.example.mydiscount.soap;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "discount"
})
@XmlRootElement(name = "updateDiscountRequest", namespace = "http://localhost.com/discount")
public class UpdateDiscountRequest {
    @XmlElement(required = true)
    protected Discount discount;

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }
}
