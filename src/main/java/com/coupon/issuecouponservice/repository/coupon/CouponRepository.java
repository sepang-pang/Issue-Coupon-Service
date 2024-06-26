package com.coupon.issuecouponservice.repository.coupon;

import com.coupon.issuecouponservice.domain.coupon.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    @Query("select count(c) > 0 from Coupon c " +
            "where lower(replace(c.couponName, ' ', '')) = lower(replace(:couponName, ' ', '')) " +
            "and c.isDeleted = false")
    boolean existsByCouponName(@Param("couponName") String couponName);

    Boolean existsByCouponImageAndId(String couponFileUrl, Long id);

    @Query("select c from Coupon c where c.isDeleted = false order by c.createdAt desc")
    List<Coupon> findAllCoupons();

    @Query("select c from Coupon c where c.id = :couponId and c.isDeleted = false")
    Optional<Coupon> findOneCouponByCouponId(@Param("couponId") Long couponId);

    // 진행 중 쿠폰 조회
    @Query("select c from Coupon c where c.couponStatus = 'ACTIVE' and c.isDeleted = false ")
    Optional<Coupon> findActiveCoupon();

    // 예정 쿠폰 조회
    @Query("select c from Coupon c where c.couponStatus = 'INACTIVE' and c.isDeleted = false order by c.openAt")
    Page<Coupon> findOpenCoupons(Pageable pageable);

    // 종료 된 쿠폰 조회
    @Query("select c from Coupon c where c.couponStatus = 'CLOSED' and c.isDeleted = false order by c.closedAt")
    Page<Coupon> findClosedCoupons(Pageable pageable);

    List<Coupon> findAllByOrderByClosedAtDesc();
}
