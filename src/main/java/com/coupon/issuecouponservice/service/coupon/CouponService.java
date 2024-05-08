package com.coupon.issuecouponservice.service.coupon;

import com.coupon.issuecouponservice.domain.coupon.Coupon;
import com.coupon.issuecouponservice.dto.request.coupon.CouponCreationParam;
import com.coupon.issuecouponservice.repository.coupon.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CouponService {

    private final CouponRepository couponRepository;

    public void createCoupon(CouponCreationParam param) {
        checkForDuplicateCouponName(param);

        Coupon coupon = Coupon.CreateCoupon(param);

        couponRepository.save(coupon);
    }

    private void checkForDuplicateCouponName(CouponCreationParam param) {
        boolean exists = couponRepository.existsByCouponName(param.getCouponName());

        if (exists) {
            throw new IllegalArgumentException("이미 존재하는 쿠폰명입니다.");
        }
    }
}
