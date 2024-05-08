package com.coupon.issuecouponservice.service.coupon;

import com.coupon.issuecouponservice.domain.coupon.Coupon;
import com.coupon.issuecouponservice.dto.request.coupon.CouponCreationParam;
import com.coupon.issuecouponservice.dto.response.coupon.CouponForm;
import com.coupon.issuecouponservice.repository.coupon.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public List<CouponForm> readAllCoupons() {
        List<Coupon> findCoupons = couponRepository.findAllCoupons();

        return findCoupons.stream()
                .map(CouponForm::new)
                .collect(Collectors.toList());
    }
}
