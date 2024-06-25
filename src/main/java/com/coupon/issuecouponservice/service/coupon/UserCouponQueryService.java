package com.coupon.issuecouponservice.service.coupon;

import com.coupon.issuecouponservice.domain.coupon.UserCoupon;
import com.coupon.issuecouponservice.repository.coupon.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCouponQueryService {

    private final UserCouponRepository userCouponRepository;

    public Page<UserCoupon> getUserCoupons(Long userId, Pageable pageable) {
        return userCouponRepository.findUserCouponsByUserId(userId, pageable);
    }

    public void saveUserCoupon(UserCoupon userCoupon) {
        userCouponRepository.save(userCoupon);
    }
}
