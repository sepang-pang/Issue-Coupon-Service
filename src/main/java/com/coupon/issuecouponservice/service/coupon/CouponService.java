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

    // 쿠폰 생성
    public void createCoupon(CouponCreationParam param) {
        checkForDuplicateCouponName(param.getCouponName()); // 쿠폰 이름 중복 검증

        Coupon coupon = Coupon.CreateCoupon(param);

        couponRepository.save(coupon);
    }

    // 쿠폰 전체 조회
    @Transactional(readOnly = true)
    public List<CouponForm> readAllCoupons() {
        List<Coupon> findCoupons = couponRepository.findAllCoupons();

        return findCoupons.stream()
                .map(CouponForm::new)
                .collect(Collectors.toList());
    }

    // 쿠폰 수정
    public void modifyCoupon(Long couponId, CouponModificationParam param) {
        checkForDuplicateCouponName(param.getCouponName()); // 쿠폰 이름 중복 검증

        Coupon findCoupon = getCoupon(couponId);

        findCoupon.modifyCoupon(param);
    }

    // 쿠폰 삭제
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
    private void checkForDuplicateCouponName(String couponName) {
        boolean exists = couponRepository.existsByCouponName(couponName);

        if (exists) {
            throw new IllegalArgumentException("이미 존재하는 쿠폰명입니다.");
        }
    }

    // 쿠폰 상세 조회
    public CouponForm selectCoupon(Long couponId) {
        Coupon coupon = getCoupon(couponId);
        return new CouponForm(coupon);
    }
}
