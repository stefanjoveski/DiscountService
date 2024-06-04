package com.example.mydiscount.dao;

import com.example.mydiscount.entity.DiscountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiscountTypeDao extends JpaRepository<DiscountType, Long> {

    @Query(value = "select d from DiscountType as d where d.category like %:keyword%")
    List<DiscountType> findDiscountTypesByCategory(@Param("keyword") String keyword);
    @Query(value = "select dt.discount_type_id, dt.category " +
            "from discount_types dt " +
            "left join discounts d on d.discount_type_id = dt.discount_type_id " +
            "where d.status = true " +
            "group by dt.discount_type_id " +
            "having count(d.discount_id) > 0", nativeQuery = true)
    List<DiscountType> findAllWithAtLeastOneDiscount();
}
