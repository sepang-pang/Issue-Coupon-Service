package com.coupon.issuecouponservice.service.coupon;

import com.coupon.issuecouponservice.domain.coupon.Coupon;
import com.coupon.issuecouponservice.dto.request.coupon.CouponCreationParam;
import com.coupon.issuecouponservice.dto.request.coupon.CouponModificationParam;
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

    @Transactional(readOnly = true)
    public List<CouponForm> readAllCoupons() {
        List<Coupon> findCoupons = couponRepository.findAllCoupons();

        return findCoupons.stream()
                .map(CouponForm::new)
                .collect(Collectors.toList());
    }

    public void modifyCoupon(Long couponId, CouponModificationParam param) {
        Coupon findCoupon = getCoupon(couponId);

        findCoupon.modifyCoupon(param);
    }

    public void deleteCoupon(Long couponId) {
        Coupon findCoupon = getCoupon(couponId);

        findCoupon.deleteCoupon();
    }


    // 쿠폰 조회
    private Coupon getCoupon(Long couponId) {
        return couponRepository.findOneCouponByCouponId(couponId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));
    }

    // 쿠폰 검증
    private void checkForDuplicateCouponName(CouponCreationParam param) {
        boolean exists = couponRepository.existsByCouponName(param.getCouponName());

        if (exists) {
            throw new IllegalArgumentException("이미 존재하는 쿠폰명입니다.");
        }
    }
}
