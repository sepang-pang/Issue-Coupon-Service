package com.coupon.issuecouponservice.dto.request.coupon;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
public class CouponModificationParam {

    private String couponName;
    private String couponContent;
    private String couponImage;
    private Integer totalQuantity;
    private Integer remainQuantity;
    private LocalDateTime openAt;
    private LocalDateTime closedAt;
    private LocalDateTime expiredAt;
}
