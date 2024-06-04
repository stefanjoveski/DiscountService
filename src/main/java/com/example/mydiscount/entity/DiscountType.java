package com.example.mydiscount.entity;

import com.example.mydiscount.soap.Discount;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "discount_types")
public class DiscountType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discount_type_id", nullable = false)
    private Long discountTypeId;
    @Basic
    @Column(name = "category", unique = true, nullable = false, length = 45)
    private String category;
    @OneToMany(mappedBy = "discountType", fetch = FetchType.LAZY)
    private Set<Discount> discounts = new HashSet<>();

    public DiscountType() {
    }

    public DiscountType(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscountType that = (DiscountType) o;
        return Objects.equals(discountTypeId, that.discountTypeId) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discountTypeId, category);
    }

    public Long getDiscountTypeId() {
        return discountTypeId;
    }

    public void setDiscountTypeId(Long discountTypeId) {
        this.discountTypeId = discountTypeId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(Set<Discount> discounts) {
        this.discounts = discounts;
    }

    @Override
    public String toString() {
        return "DiscountType{" +
                "discountTypeId=" + discountTypeId +
                ", category='" + category + '\'' +
                '}';
    }
}
