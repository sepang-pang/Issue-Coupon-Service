package com.coupon.issuecouponservice.controller.coupon;

import com.coupon.issuecouponservice.dto.request.CouponIssueTestParam;
import com.coupon.issuecouponservice.dto.response.ApiResponseForm;
import com.coupon.issuecouponservice.facade.RedissonLockTestFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class CouponUserTestController {

    private final RedissonLockTestFacade redissonLockTestFacade;

    // 쿠폰 발급 테스트
    @PostMapping("/coupon-test")
    public ResponseEntity<ApiResponseForm> issueCouponTest(@RequestBody CouponIssueTestParam couponIssueTestParam) {
        redissonLockTestFacade.issueCouponTestWithLock(couponIssueTestParam);
        return ResponseEntity.ok().body(new ApiResponseForm("쿠폰 발급에 성공했습니다.", HttpStatus.OK.value()));
    }

}
