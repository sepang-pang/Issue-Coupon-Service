package com.coupon.issuecouponservice.service.coupon;


import com.coupon.issuecouponservice.domain.coupon.Coupon;
import com.coupon.issuecouponservice.repository.coupon.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponQueryService {

    private final CouponRepository couponRepository;

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long couponId) {
        return couponRepository.findOneCouponByCouponId(couponId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않는 쿠폰입니다."));
    }

    @Transactional
    public void saveCoupon(Coupon coupon) {
        couponRepository.save(coupon);
    }
}
