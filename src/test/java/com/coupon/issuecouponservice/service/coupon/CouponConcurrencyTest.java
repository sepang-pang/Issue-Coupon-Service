package com.coupon.issuecouponservice.service.coupon;

import com.coupon.issuecouponservice.domain.user.User;
import com.coupon.issuecouponservice.dto.request.coupon.CouponIssueParam;
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
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@TestPropertySource(locations = "/application-test.properties")
@SpringBootTest
class CouponConcurrencyTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Autowired
    private UserRepository userRepository;

    CouponIssueParam param;

    Map<Integer, User> usersMap;


    @BeforeEach
    void setUp() {
        param = new CouponIssueParam(1L);

        List<User> users = userRepository.findAll();

        AtomicInteger counter = new AtomicInteger(0);

        usersMap = users.stream()
                .collect(Collectors.toMap(user -> counter.getAndIncrement(), user -> user));
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
        Long count = userCouponRepository.findByCouponId(param.getCouponId());
        assertThat(count).isEqualTo(1L);
    }

}