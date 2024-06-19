package com.coupon.issuecouponservice.service.coupon;

import com.coupon.issuecouponservice.domain.coupon.Coupon;
import com.coupon.issuecouponservice.domain.coupon.CouponStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.ZoneId;

@Component
@RequiredArgsConstructor
public class CouponScheduler {

    private final CouponQueryService couponQueryService;
    private final TransactionTemplate transactionTemplate;
    private final TaskScheduler taskScheduler;

    public void scheduleCouponStatusChange(Coupon coupon) {
        // 쿠폰 활성화 스케줄
        taskScheduler.schedule(() -> activateCoupon(coupon.getId()), coupon.getOpenAt().atZone(ZoneId.systemDefault()).toInstant());

        // 쿠폰 비활성화 스케줄
        taskScheduler.schedule(() -> deactivateCoupon(coupon.getId()), coupon.getClosedAt().atZone(ZoneId.systemDefault()).toInstant());
    }

    private void activateCoupon(Long couponId) {
        transactionTemplate.execute(status -> {
            Coupon coupon = couponQueryService.getCoupon(couponId);
            coupon.updateCouponStatus(CouponStatus.ACTIVE);
            return null;
        });
    }

    private void deactivateCoupon(Long couponId) {
        transactionTemplate.execute(status -> {
            Coupon coupon = couponQueryService.getCoupon(couponId);
            coupon.updateCouponStatus(CouponStatus.CLOSED);
            return null;
        });
    }
}
