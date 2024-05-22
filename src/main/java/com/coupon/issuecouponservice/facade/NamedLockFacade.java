package com.coupon.issuecouponservice.facade;

import com.coupon.issuecouponservice.domain.user.User;
import com.coupon.issuecouponservice.dto.request.coupon.CouponIssueParam;
import com.coupon.issuecouponservice.repository.coupon.NamedLockRepository;
import com.coupon.issuecouponservice.service.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NamedLockFacade {

    private final CouponService couponService;
    private final NamedLockRepository namedLockRepository;

    @Transactional
    public void issueCoupon(CouponIssueParam param, User user) {
        try {
            // 쿠폰 ID를 이용해 락을 획득
            namedLockRepository.getLock(param.getCouponId().toString());
            couponService.issueCoupon(param, user); // 비즈니스 로직 수행
        } finally {
            // 락 해제
            namedLockRepository.releaseLock(param.getCouponId().toString());
        }
    }
}

