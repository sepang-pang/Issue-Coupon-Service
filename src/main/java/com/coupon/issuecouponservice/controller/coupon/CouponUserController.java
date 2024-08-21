package com.coupon.issuecouponservice.controller.coupon;

import com.coupon.issuecouponservice.domain.user.User;
import com.coupon.issuecouponservice.dto.request.CouponIssueTestParam;
import com.coupon.issuecouponservice.dto.request.coupon.CouponIssueParam;
import com.coupon.issuecouponservice.dto.response.ApiResponseForm;
import com.coupon.issuecouponservice.facade.RedissonLockFacade;
import com.coupon.issuecouponservice.security.userdetails.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.coupon.issuecouponservice.domain.user.Role.Authority.USER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
//@Secured(USER)
public class CouponUserController {

    private final RedissonLockFacade redissonLockFacade;

    // 쿠폰 발급
    @PostMapping("/coupon")
    public ResponseEntity<ApiResponseForm> issueCoupon(@RequestBody CouponIssueParam couponIssueParam, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        redissonLockFacade.issueCouponWithLock(couponIssueParam, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseForm("쿠폰 발급에 성공했습니다.", HttpStatus.OK.value()));
    }

    // 쿠폰 발급 테스트
    @PostMapping("/coupon-test")
    public ResponseEntity<ApiResponseForm> issueCouponTest(@RequestBody CouponIssueTestParam couponIssueTestParam) {
        redissonLockFacade.issueCouponTestWithLock(couponIssueTestParam);
        return ResponseEntity.ok().body(new ApiResponseForm("쿠폰 발급에 성공했습니다.", HttpStatus.OK.value()));
    }
}