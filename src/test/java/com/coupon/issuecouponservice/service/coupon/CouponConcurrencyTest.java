package com.coupon.issuecouponservice.service.coupon;

import com.coupon.issuecouponservice.domain.user.User;
import com.coupon.issuecouponservice.dto.request.coupon.CouponIssueParam;
import com.coupon.issuecouponservice.repository.coupon.UserCouponRepository;
import com.coupon.issuecouponservice.repository.user.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "/application-test.properties")
class CouponConcurrencyTest {

    @Autowired
    private CouponService couponService;

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
    @Order(1)
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



}