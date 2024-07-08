package com.coupon.issuecouponservice.controller.coupon;

import com.coupon.issuecouponservice.dto.response.coupon.CouponOneForm;
import com.coupon.issuecouponservice.service.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CouponCommonController {

    private final CouponService couponService;

    @GetMapping({"", "/"})
    public String home(Model model) {
        CouponOneForm coupon = couponService.readActiveCoupon();
        model.addAttribute("coupon", coupon);
        return "main";
    }
}
