package com.coupon.issuecouponservice.service.coupon;

import com.coupon.issuecouponservice.domain.user.User;
import com.coupon.issuecouponservice.dto.request.coupon.CouponIssueParam;
import com.coupon.issuecouponservice.facade.RedisLockStockFacade;
import com.coupon.issuecouponservice.repository.coupon.UserCouponRepository;
import com.coupon.issuecouponservice.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@TestPropertySource(locations = "/application-test.properties")
@SpringBootTest
class CouponServiceTest {

    @Autowired
    private RedisLockStockFacade redisLockStockFacade;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Autowired
    private UserRepository userRepository;

    CouponIssueParam couponIssueParam;

    List<User> users;

    @BeforeEach
    void setUp() {
        couponIssueParam = new CouponIssueParam(1L);

        users = userRepository.findAll();
    }

    @AfterEach
    void after() {
        userCouponRepository.deleteAll();
    }

    @Test
    @DisplayName("쿠폰 여러 명 발급 - 레디슨")
    void issueCoupon_redisson() throws InterruptedException {
        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int threadNumber = i + 1;
            User user = users.get(i);
            executorService.submit(() -> {
                try {
                    redisLockStockFacade.issueCoupon(couponIssueParam, user);
                    System.out.println("Thread " + threadNumber + " - 성공");

                } catch (PessimisticLockingFailureException e) {
                    System.out.println("Thread " + threadNumber + " - 락 충돌 감지");

                } catch (Exception e) {
                    System.out.println("Thread " + threadNumber + " - " + e.getMessage());

                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        int count = userCouponRepository.countByCouponId(couponIssueParam.getCouponId());

        assertThat(count).isEqualTo(100);
    }
}