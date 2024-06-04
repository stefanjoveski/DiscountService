package com.example.mydiscount.soap;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "status"
})
@XmlRootElement(name = "addDiscountResponse", namespace = "http://localhost.com/dicount")
public class AddDiscountResponse {
    @XmlElement(required = true)
    protected String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
