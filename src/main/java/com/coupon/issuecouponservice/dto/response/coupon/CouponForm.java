package com.coupon.issuecouponservice.dto.response.coupon;

import com.coupon.issuecouponservice.domain.coupon.Coupon;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CouponForm {
    private Long couponId;
    private String couponName;
    private int totalQuantity;
    private int remainQuantity;
    private int possessionCount;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;

    public CouponForm(Coupon coupon) {
        this.couponId = coupon.getId();
        this.couponName = coupon.getCouponName();
        this.totalQuantity = coupon.getTotalQuantity();
        this.remainQuantity = coupon.getRemainQuantity();
        this.createdAt = coupon.getCreatedAt();
        this.expiredAt = coupon.getExpiredAt();
    }

    public CouponForm(Coupon coupon, LocalDateTime createdAt, int possessionCount) {
        this.couponId = coupon.getId();
        this.couponName = coupon.getCouponName();
        this.totalQuantity = coupon.getTotalQuantity();
        this.remainQuantity = coupon.getRemainQuantity();
        this.possessionCount = possessionCount;
        this.createdAt = createdAt;
        this.expiredAt = coupon.getExpiredAt();
    }
}
