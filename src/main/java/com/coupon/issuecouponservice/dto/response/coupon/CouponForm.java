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
    private String couponImage;
    private int totalQuantity;
    private int remainQuantity;
    private ValidityStatus validityStatus;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private LocalDateTime openAt;
    private LocalDateTime closedAt;

    public CouponForm(Coupon coupon) {
        this.couponId = coupon.getId();
        this.couponName = coupon.getCouponName();
        this.couponContent = coupon.getCouponContent();
        this.couponImage = coupon.getCouponImage();
        this.totalQuantity = coupon.getTotalQuantity();
        this.remainQuantity = coupon.getRemainQuantity();
        this.validityStatus = coupon.getValidityStatus();
        this.createdAt = coupon.getCreatedAt();
        this.expiredAt = coupon.getExpiredAt();
        this.openAt = coupon.getOpenAt();
        this.closedAt = coupon.getClosedAt();
    }

    public CouponForm(Coupon coupon, LocalDateTime createdAt) {
        this.couponId = coupon.getId();
        this.couponName = coupon.getCouponName();
        this.couponContent = coupon.getCouponContent();
        this.couponImage = coupon.getCouponImage();
        this.totalQuantity = coupon.getTotalQuantity();
        this.remainQuantity = coupon.getRemainQuantity();
        this.validityStatus = coupon.getValidityStatus();
        this.createdAt = createdAt;
        this.expiredAt = coupon.getExpiredAt();
    }
}
