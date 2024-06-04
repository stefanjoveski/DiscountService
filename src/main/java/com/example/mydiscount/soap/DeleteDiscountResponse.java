package com.example.mydiscount.soap;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "status"
})
@XmlRootElement(name = "deleteDiscountResponse", namespace = "http://localhost.com/dicount")
public class DeleteDiscountResponse {
    @XmlElement(required = true)
    protected String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
