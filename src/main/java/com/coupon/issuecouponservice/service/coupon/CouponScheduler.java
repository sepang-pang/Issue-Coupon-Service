package com.coupon.issuecouponservice.service.coupon;

import com.coupon.issuecouponservice.domain.coupon.Coupon;
import com.coupon.issuecouponservice.domain.coupon.CouponStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class CouponScheduler {

    private final CouponQueryService couponQueryService;
    private final TaskScheduler taskScheduler;

    public void scheduleCouponStatusChange(Coupon coupon) {
        // 쿠폰 활성화 스케줄
        taskScheduler.schedule(() -> activateCoupon(coupon.getId()), Date.from(coupon.getOpenAt().atZone(ZoneId.systemDefault()).toInstant()));

        // 쿠폰 비활성화 스케줄
        taskScheduler.schedule(() -> deactivateCoupon(coupon.getId()), Date.from(coupon.getClosedAt().atZone(ZoneId.systemDefault()).toInstant()));
    }

    private void activateCoupon(Long couponId) {
        Coupon coupon = couponQueryService.getCoupon(couponId);
        coupon.updateCouponStatus(CouponStatus.ACTIVE);
        couponQueryService.saveCoupon(coupon);
    }

    private void deactivateCoupon(Long couponId) {
        Coupon coupon = couponQueryService.getCoupon(couponId);
        coupon.updateCouponStatus(CouponStatus.CLOSED);
        couponQueryService.saveCoupon(coupon);
    }
}
