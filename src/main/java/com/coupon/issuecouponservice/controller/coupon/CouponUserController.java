package com.coupon.issuecouponservice.controller.coupon;

import com.coupon.issuecouponservice.dto.request.coupon.CouponIssueParam;
import com.coupon.issuecouponservice.dto.response.coupon.CouponForm;
import com.coupon.issuecouponservice.security.userdetails.UserDetailsImpl;
import com.coupon.issuecouponservice.service.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
//@Secured("USER")
public class CouponUserController {

    private final CouponService couponService;

    // 쿠폰 발급
    @PostMapping("/coupon")
    public void issueCoupon(@RequestBody CouponIssueParam couponIssueParam, @AuthenticationPrincipal UserDetailsImpl userDetails){
        couponService.issueCoupon(couponIssueParam, userDetails.getUser());
    }

    // 사용자 쿠폰 전체 조회
    @GetMapping("/{userId}/coupon")
    public List<CouponForm> readAllUserCoupons(@PathVariable Long userId){
//        return couponService.readAllUserCoupons(userId);
        return couponService.readAllUserCouponsV1(userId);
//        return couponService.readAllUserCouponsV2(userId);
//        return couponService.readAllUserCouponsV3(userId);
    }

}