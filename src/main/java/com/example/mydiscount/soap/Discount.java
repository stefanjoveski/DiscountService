package com.example.mydiscount.soap;

import com.example.mydiscount.entity.DiscountType;
import com.example.mydiscount.entity.UserDiscount;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "discount", namespace = "http://localhost.com/discount", propOrder = {
        "discountId",
        "code",
        "discountPercentage",
        "startDate",
        "endDate",
        "status"
})
@Entity
@Table(name = "discounts")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discount_id")
    private Long discountId;
    @Basic
    @XmlElement(required = true)
    @Column(name = "code", nullable = false, length = 8)
    private String code;
    @Basic
    @XmlElement(required = true)
    @Column(name = "discount_percentage", nullable = false)
    private Integer discountPercentage;
    @Basic
    @XmlElement(required = true)
    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @Basic
    @XmlElement(required = true)
    @Column(name = "end_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    @Basic
    @XmlElement(required = true)
    @Column(name = "status", nullable = false)
    private boolean status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_type_id", referencedColumnName = "discount_type_id", nullable = false)
    private DiscountType discountType;
    @OneToMany(mappedBy = "discount", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<UserDiscount> activeUserDiscounts = new HashSet<>();

    public Discount() {
    }

    public Discount(String code, Integer discountPercentage, Date startDate, Date endDate, boolean status, DiscountType discountType) {
        this.code = code;
        this.discountPercentage = discountPercentage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountType = discountType;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount = (Discount) o;
        return status == discount.status && Objects.equals(discountId, discount.discountId) && Objects.equals(discountPercentage, discount.discountPercentage) && Objects.equals(startDate, discount.startDate) && Objects.equals(endDate, discount.endDate) && Objects.equals(code, discount.code) && Objects.equals(discountType, discount.discountType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discountId, code, discountPercentage, startDate, endDate, status, discountType);
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

    public Integer getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Integer discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public Set<UserDiscount> getActiveUserDiscounts() {
        return activeUserDiscounts;
    }

    public void setActiveUserDiscounts(Set<UserDiscount> activeUserDiscounts) {
        this.activeUserDiscounts = activeUserDiscounts;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "discountId=" + discountId +
                ", code='" + code + '\'' +
                ", discountPercentage=" + discountPercentage +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                ", discountType=" + discountType +
                '}';
    }
}
