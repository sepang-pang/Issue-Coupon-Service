package com.coupon.issuecouponservice.dto.response.coupon;

import com.coupon.issuecouponservice.domain.coupon.Coupon;
import com.coupon.issuecouponservice.domain.coupon.CouponStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CouponSummaryForm {

    private Long couponId;
    private String couponName;
    private String couponContent;
    private String couponImage;
    private CouponStatus couponStatus;
    private int totalQuantity;
    private int remainQuantity;
    private LocalDateTime openAt;
    private LocalDateTime closedAt;
    private LocalDateTime expiredAt;

    public CouponSummaryForm(Coupon coupon) {
        this.couponId = coupon.getId();
        this.couponName = coupon.getCouponName();
        this.couponContent = coupon.getCouponContent();
        this.couponImage = coupon.getCouponImage();
        this.couponStatus = coupon.getCouponStatus();
        this.totalQuantity = coupon.getTotalQuantity();
        this.remainQuantity = coupon.getRemainQuantity();
        this.openAt = coupon.getOpenAt();
        this.closedAt = coupon.getClosedAt();
        this.expiredAt = coupon.getExpiredAt();
    }
}
