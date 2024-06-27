package com.coupon.issuecouponservice.controller;

import com.coupon.issuecouponservice.util.PaginationUtils;
import com.coupon.issuecouponservice.dto.response.coupon.CouponForm;
import com.coupon.issuecouponservice.dto.response.coupon.CouponOneForm;
import com.coupon.issuecouponservice.dto.response.user.UserForm;
import com.coupon.issuecouponservice.security.userdetails.UserDetailsImpl;
import com.coupon.issuecouponservice.service.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final CouponService couponService;

    @GetMapping({"", "/"})
    public String home(Model model) {
        CouponOneForm coupon = couponService.readActiveCoupon();
        model.addAttribute("coupon", coupon);
        return "main";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/create-coupon")
    public String create() {
        return "create-coupon";
    }

    @GetMapping("/upcoming-coupons")
    public String upcoming() {
        return "upcoming-coupons";
    }

    @GetMapping("/past-coupons")
    public String past(Model model, @PageableDefault(size = 9) Pageable pageable) {

        Page<CouponForm> coupons = couponService.readAllClosedCoupons(pageable);

        PaginationUtils paginationUtils = new PaginationUtils(coupons, 10);

        model.addAttribute("coupons", coupons);
        model.addAttribute("count", (int) coupons.getTotalElements());
        model.addAttribute("paginationUtils", paginationUtils);

        return "past-coupons";
    }

    @GetMapping("/my-page")
    public String myPage(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails,
                         @PageableDefault(size = 5) Pageable pageable) {
        UserForm userForm = new UserForm(userDetails.getUser());
        Page<CouponForm> coupons = couponService.readAllUserCoupons(userDetails.getUser(), pageable);

        PaginationUtils paginationUtils = new PaginationUtils(coupons, 5);

        model.addAttribute("user", userForm);
        model.addAttribute("coupons", coupons);
        model.addAttribute("count", (int) coupons.getTotalElements());
        model.addAttribute("paginationUtils", paginationUtils);

        return "my-page";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
