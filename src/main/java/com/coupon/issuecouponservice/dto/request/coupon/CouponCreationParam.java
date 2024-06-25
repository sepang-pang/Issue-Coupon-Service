package com.coupon.issuecouponservice.dto.request.coupon;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CouponCreationParam {

    private String couponName;

    private String couponContent;

    private String couponImage;

    private int totalQuantity;

    private int remainQuantity;

    private LocalDateTime openAt;

    private LocalDateTime closedAt;

    private LocalDateTime expiredAt;
}
