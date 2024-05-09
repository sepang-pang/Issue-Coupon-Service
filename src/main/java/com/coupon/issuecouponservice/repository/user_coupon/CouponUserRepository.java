package com.coupon.issuecouponservice.repository.user_coupon;

import com.coupon.issuecouponservice.domain.user_coupon.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponUserRepository extends JpaRepository<UserCoupon, Long> {
}
