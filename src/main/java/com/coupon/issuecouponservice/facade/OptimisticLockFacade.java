package com.coupon.issuecouponservice.facade;

import com.coupon.issuecouponservice.domain.user.User;
import com.coupon.issuecouponservice.dto.request.coupon.CouponIssueParam;
import com.coupon.issuecouponservice.service.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OptimisticLockFacade {

    private final CouponService couponService;

    public void issueCoupon(CouponIssueParam param, User user) throws InterruptedException {
        while (true) {
            try {
                couponService.issueCoupon(param, user);
                break;
            } catch (IllegalArgumentException e) {
                throw e;
            } catch (Exception e) {
                System.out.println("Version 불일치 발생 ! : " + e.getMessage());
                Thread.sleep(50); // 재시도 전 잠시 대기
            }
        }
    }
}