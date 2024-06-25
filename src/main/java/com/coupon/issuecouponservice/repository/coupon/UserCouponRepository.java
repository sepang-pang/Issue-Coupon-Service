package com.coupon.issuecouponservice.repository.coupon;

import com.coupon.issuecouponservice.domain.coupon.UserCoupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {

    @Query("select uc from UserCoupon uc " +
            "join fetch uc.coupon " +
            "where uc.user.id = :userId " +
            "order by uc.createdAt desc")
    Page<UserCoupon> findUserCouponsByUserId(@Param("userId") Long userId, Pageable pageable);

    Long countByCouponId(Long couponId);
}
