package com.coupon.issuecouponservice.repository.coupon;

import com.coupon.issuecouponservice.domain.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    @Query("select count(c) > 0 from Coupon c " +
            "where lower(replace(c.couponName, ' ', '')) = lower(replace(:couponName, ' ', '')) " +
            "and c.isDeleted = false")
    boolean existsByCouponName(@Param("couponName") String couponName);
}
