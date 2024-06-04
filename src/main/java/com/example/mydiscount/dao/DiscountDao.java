package com.example.mydiscount.dao;

import com.example.mydiscount.soap.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiscountDao extends JpaRepository<Discount, Long> {

    @Query(value = "select e from Discount as e where e.code like %:keyword%")
    List<Discount> findDiscountsByCode(@Param("keyword") String keyword);

    @Query(value = "select * from discounts as d where d.status=true and d.discount_id in (select ud.discount_id from user_discounts as ud where ud.user_id = :userId)",
            nativeQuery = true)
    List<Discount> fetchActiveDiscountsForUser(@Param("userId") Long userId);

    @Query(value = "select * from discounts as d where d.status=true and d.discount_percentage >= :percentage and d.discount_id in (select ud.discount_id from user_discounts as ud where ud.user_id = :userId)",
            nativeQuery = true)
    List<Discount> fetchActiveDiscountsForUserWithPercentage(@Param("userId") Long userId, @Param("percentage") Integer percentage);

    List<Discount> findDiscountsByDiscountId(Long discountId);

    @Query(value = "select * from discounts as d where d.discount_type_id = :discountTypeId)",
            nativeQuery = true)
    List<Discount> findDiscountsByDiscountType(@Param("discountTypeId") Long discountTypeId);

    @Query(value = "select * from discounts as d where d.status = :status",
            nativeQuery = true)
    List<Discount> findDiscountsByStatus(@Param("status") boolean status);

    @Query(value = "select * from discounts as d where d.status = :status and d.discount_percentage >= :percentage",
            nativeQuery = true)
    List<Discount> findDiscountsByStatusAndPercentage(@Param("status") boolean status, @Param("percentage") Integer percentage);
    @Query(value = "select * from discounts as d where d.status = true and d.discount_percentage >= :percentage",
            nativeQuery = true)
    List<Discount> fetchActiveDiscountsWithPercentage(Integer percentage);
}
