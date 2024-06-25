package com.coupon.issuecouponservice.controller.coupon;

import com.coupon.issuecouponservice.dto.request.coupon.CouponIssueParam;
import com.coupon.issuecouponservice.dto.response.coupon.CouponForm;
import com.coupon.issuecouponservice.facade.RedissonLockFacade;
import com.coupon.issuecouponservice.security.userdetails.UserDetailsImpl;
import com.coupon.issuecouponservice.service.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.coupon.issuecouponservice.domain.user.Role.Authority.USER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Secured(USER)
public class CouponUserController {

    private final CouponService couponService;
    private final RedissonLockFacade redissonLockFacade;

    // 쿠폰 발급
    @PostMapping("/coupon")
    public void issueCoupon(@RequestBody CouponIssueParam couponIssueParam, @AuthenticationPrincipal UserDetailsImpl userDetails){
        redissonLockFacade.issueCouponWithLock(couponIssueParam, userDetails.getUser());
    }

    // 사용자 쿠폰 전체 조회
    @GetMapping("/coupon")
    public Page<CouponForm> readAllUserCoupons(@AuthenticationPrincipal UserDetailsImpl userDetails, Pageable pageable){
        return couponService.readAllUserCoupons(userDetails.getUser(), pageable);
    }

}