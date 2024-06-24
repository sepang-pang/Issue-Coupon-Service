package com.coupon.issuecouponservice.dto.response.coupon;

import com.coupon.issuecouponservice.domain.coupon.Coupon;
import com.coupon.issuecouponservice.domain.coupon.ValidityStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CouponForm {
    private Long couponId;
    private String couponName;
    private String couponContent;
    private int totalQuantity;
    private int remainQuantity;
    private int possessionCount;
    private ValidityStatus validityStatus;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;

    public CouponForm(Coupon coupon) {
        this.couponId = coupon.getId();
        this.couponName = coupon.getCouponName();
        this.totalQuantity = coupon.getTotalQuantity();
        this.remainQuantity = coupon.getRemainQuantity();
        this.validityStatus = coupon.getValidityStatus();
        this.createdAt = coupon.getCreatedAt();
        this.expiredAt = coupon.getExpiredAt();
    }

    public CouponForm(Coupon coupon, LocalDateTime createdAt, int possessionCount) {
        this.couponId = coupon.getId();
        this.couponName = coupon.getCouponName();
        this.couponContent = coupon.getCouponContent();
        this.totalQuantity = coupon.getTotalQuantity();
        this.remainQuantity = coupon.getRemainQuantity();
        this.possessionCount = possessionCount;
        this.validityStatus = coupon.getValidityStatus();
        this.createdAt = createdAt;
        this.expiredAt = coupon.getExpiredAt();
    }
}
