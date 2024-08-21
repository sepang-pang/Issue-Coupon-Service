package com.coupon.issuecouponservice.facade;

import com.coupon.issuecouponservice.domain.user.User;
import com.coupon.issuecouponservice.dto.request.CouponIssueTestParam;
import com.coupon.issuecouponservice.dto.request.coupon.CouponIssueParam;
import com.coupon.issuecouponservice.repository.user.UserRepository;
import com.coupon.issuecouponservice.service.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j(topic = "RedissonLockFacade")
@RequiredArgsConstructor
public class RedissonLockTestFacade {
    private final RedissonClient redissonClient;
    private final CouponService couponService;
    private final UserRepository userRepository;

    public void issueCouponTestWithLock(CouponIssueTestParam param) {
        User user = userRepository.findById(param.getUserId()).get();
        RLock lock = redissonClient.getLock(param.getCouponId().toString());

        try {
            boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);

            if (!available) {
                log.info("Lock 획득 실패");
                return;
            }

            couponService.issueCoupon(param, user);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
