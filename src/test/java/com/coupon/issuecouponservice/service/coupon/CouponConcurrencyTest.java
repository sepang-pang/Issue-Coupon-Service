package com.coupon.issuecouponservice.service.coupon;

import com.coupon.issuecouponservice.domain.user.User;
import com.coupon.issuecouponservice.dto.request.coupon.CouponIssueParam;
import com.coupon.issuecouponservice.facade.OptimisticLockFacade;
import com.coupon.issuecouponservice.repository.coupon.UserCouponRepository;
import com.coupon.issuecouponservice.repository.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@TestPropertySource(locations = "/application-test.properties")
@SpringBootTest
class CouponConcurrencyTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private OptimisticLockFacade optimisticLockFacade;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Autowired
    private UserRepository userRepository;

    CouponIssueParam param;

    List<User> users;

    @BeforeEach
    void setUp() {
        param = new CouponIssueParam(1L);

        users = userRepository.findAll();
    }

    @AfterEach
    void after() {
        userCouponRepository.deleteAll();
    }

    @Test
    @Transactional
    @DisplayName("쿠폰 한 명 발급")
    void 쿠폰_한_명_발급() {
        // given
        User user = userRepository.findById(1L).get();

        // when
        couponService.issueCoupon(param, user);

        // then
        Long count = userCouponRepository.countByCouponId(param.getCouponId());
        assertThat(count).isEqualTo(1L);
    }

    @Test
    @DisplayName("쿠폰 여러 명 발급")
    void 쿠폰_여러_명_발급() throws InterruptedException {
        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int threadNumber = i + 1;
            int key = i;
            executorService.submit(() -> {
                try {
                    optimisticLockFacade.issueCoupon(param, users.get(key));
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

        Long count = userCouponRepository.countByCouponId(param.getCouponId());

        assertThat(count).isEqualTo(100);
    }

}