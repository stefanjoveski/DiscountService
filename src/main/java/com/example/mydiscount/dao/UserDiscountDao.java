package com.example.mydiscount.dao;

import com.example.mydiscount.entity.UserDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserDiscountDao extends JpaRepository<UserDiscount, Long> {

    @Query(value = "SELECT * FROM user_discounts WHERE user_id = :userId", nativeQuery = true)
    List<UserDiscount> findByUserId(@Param("userId") Long userId);
    @Query(value = "SELECT * FROM user_discounts WHERE discount_id = :discountId", nativeQuery = true)

    List<UserDiscount> findByDiscountId(@Param("discountId") Long discountId);
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_discounts(user_id, discount_id) values (:userId, :discountId)", nativeQuery = true)
    void activate(@Param("userId") Long userId, @Param("discountId") Long discountId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_discounts WHERE user_id = :userId AND discount_id = :discountId", nativeQuery = true)
    void deactivate(@Param("userId") Long userId, @Param("discountId") Long discountId);

    @Query(value = "SELECT * FROM user_discounts WHERE user_id = :userId AND discount_id = :discountId ", nativeQuery = true)
    UserDiscount findByUserIdAndDiscountId(@Param("userId") Long userId, @Param("discountId") Long discountId);
    @Query(value = "SELECT * FROM user_discounts ud " +
            "LEFT JOIN discounts d ON d.discount_id = ud.discount_id " +
            "WHERE ud.user_id = :userId AND d.status = true", nativeQuery = true)
    List<UserDiscount> findCurrentActiveDiscountsByUserId(@Param("userId") Long userId);
}
