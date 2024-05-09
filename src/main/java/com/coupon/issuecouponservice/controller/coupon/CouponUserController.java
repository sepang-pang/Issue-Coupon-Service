package com.coupon.issuecouponservice.controller.coupon;

import com.coupon.issuecouponservice.dto.response.coupon.CouponForm;
import com.coupon.issuecouponservice.service.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class CouponUserController {

    private final CouponService couponService;

    // 쿠폰 발급



    // 쿠폰 전체 조회
    @GetMapping("/coupon")
    public List<CouponForm> readAllCoupons() {
        return couponService.readAllCoupons();
    }

    // 쿠폰 상세 조회
    @GetMapping("/coupone/{couponId}")
    public CouponForm selectCoupon(@PathVariable Long couponId){
        return couponService.selectCoupon(couponId);
    }

    // 사용자 쿠폰 전체 조회

    // 사용자 쿠폰 상세 조회
}