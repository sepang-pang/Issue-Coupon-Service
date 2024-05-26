package com.coupon.issuecouponservice.service.coupon;

import com.coupon.issuecouponservice.domain.coupon.Coupon;
import com.coupon.issuecouponservice.domain.user.Role;
import com.coupon.issuecouponservice.domain.user.User;
import com.coupon.issuecouponservice.dto.request.coupon.CouponIssueParam;
import com.coupon.issuecouponservice.facade.RedissonLockFacade;
import com.coupon.issuecouponservice.repository.coupon.CouponRepository;
import com.coupon.issuecouponservice.repository.coupon.UserCouponRepository;
import com.coupon.issuecouponservice.repository.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "/application-test.properties")
class CouponConcurrencyTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private RedissonLockFacade redissonLockFacade;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Autowired
    private UserRepository userRepository;

    CouponIssueParam param;

    Coupon coupon;

    List<User> users = new ArrayList<>();

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 1000; i++ ) {
            User user = User.builder()
                    .username("test" + i)
                    .nickName("Test" + i)
                    .role(Role.USER)
                    .email("test" + i + "@example.com")
                    .provider("test")
                    .providerId("test" + i)
                    .build();
            userRepository.save(user);
            users.add(user);
        }

        coupon = Coupon.builder()
                .couponName("테스트 쿠폰")
                .couponImage("image")
                .expiredAt(LocalDateTime.now().plusDays(30))
                .totalQuantity(100)
                .build();

        couponRepository.save(coupon);

        param = new CouponIssueParam(coupon.getId());
    }

    @AfterEach
    void after() {
        userCouponRepository.deleteAll();
        couponRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @Transactional
    @DisplayName("쿠폰 한 명 발급")
    void 쿠폰_한_명_발급() {
        // given
        User user = users.get(0);

        // when
        couponService.issueCoupon(param, user);

        // then
        Long count = userCouponRepository.countByCouponId(param.getCouponId());
        assertThat(count).isEqualTo(1L);
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
                    redissonLockFacade.issueCouponWithLock(param, user);
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

        Assertions.assertThat(count).isEqualTo(100);
    }


}