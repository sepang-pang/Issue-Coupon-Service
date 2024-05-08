package com.coupon.issuecouponservice.dto.response.coupon;

import com.coupon.issuecouponservice.domain.coupon.Coupon;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CouponForm {
    private Long couponId;
    private int totalQuantity;
    private int remainQuantity;
    private LocalDateTime expiredAt;

    public CouponForm(Coupon coupon) {
        this.couponId = coupon.getId();
        this.totalQuantity = coupon.getTotalQuantity();
        this.remainQuantity = coupon.getRemainQuantity();
        this.expiredAt = coupon.getExpiredAt();
    }
}
