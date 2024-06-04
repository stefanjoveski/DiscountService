package com.example.mydiscount.soap;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "discount"
})
@XmlRootElement(name = "addDiscountRequest", namespace = "http://localhost.com/discount")
public class AddDiscountRequest {
    @XmlElement(required = true)
    protected Discount discount;

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }
}
