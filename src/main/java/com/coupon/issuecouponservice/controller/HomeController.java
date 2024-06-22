package com.coupon.issuecouponservice.controller;

import com.coupon.issuecouponservice.dto.response.coupon.CouponForm;
import com.coupon.issuecouponservice.dto.response.coupon.CouponOneForm;
import com.coupon.issuecouponservice.dto.response.user.UserForm;
import com.coupon.issuecouponservice.security.userdetails.UserDetailsImpl;
import com.coupon.issuecouponservice.service.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final CouponService couponService;

    @GetMapping({"", "/"})
    public String home(Model model) {
        CouponOneForm coupon = couponService.readActiveCoupon();
        model.addAttribute("coupon", coupon);
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/upcoming-coupons")
    public String upcoming() {
        return "upcoming-coupons";
    }

    @GetMapping("/past-coupons")
    public String past() {
        return "past-coupons";
    }

    @GetMapping("/my-page")
    public String myPage(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 사용자 정보를 UserForm 객체로 변환
        UserForm userForm = new UserForm(userDetails.getUser());

        // 현재 로그인한 사용자의 모든 쿠폰을 조회
        List<CouponForm> coupons = couponService.readAllUserCoupons(userDetails.getUser());

        model.addAttribute("user", userForm);
        model.addAttribute("coupons", coupons);

        return "my-page";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
