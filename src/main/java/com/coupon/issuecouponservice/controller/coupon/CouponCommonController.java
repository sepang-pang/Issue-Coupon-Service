package com.coupon.issuecouponservice.controller.coupon;

import com.coupon.issuecouponservice.dto.response.coupon.CouponForm;
import com.coupon.issuecouponservice.dto.response.coupon.CouponSummaryForm;
import com.coupon.issuecouponservice.service.coupon.CouponService;
import com.coupon.issuecouponservice.util.PaginationUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CouponCommonController {

    private final CouponService couponService;

    @GetMapping("/")
    public String home(Model model) {
        CouponSummaryForm coupon = couponService.readActiveCoupon();

        if (coupon == null) {
            System.out.println("오픈 예정 쿠폰 Null 체크");
            coupon = couponService.readOpenCoupon();
        }

        model.addAttribute("coupon", coupon);
        return "main";
    }

    // 오픈 예정 쿠폰 조회
    @GetMapping("/upcoming-coupons")
    public String upcoming(HttpServletRequest request, Model model, @PageableDefault(size = 9) Pageable pageable) {
        Page<CouponForm> coupons = couponService.readAllOpenCoupons(pageable);
        addModelAttributes(request, model, coupons);
        return "user/upcoming-coupons";
    }

    // 마감된 쿠폰 조회
    @GetMapping("/past-coupons")
    public String past(HttpServletRequest request, Model model, @PageableDefault(size = 9) Pageable pageable) {
        Page<CouponForm> coupons = couponService.readAllClosedCoupons(pageable);
        addModelAttributes(request, model, coupons);
        return "user/past-coupons";
    }

    private void addModelAttributes(HttpServletRequest request, Model model, Page<CouponForm> coupons) {
        PaginationUtils paginationUtils = new PaginationUtils(coupons, 10);
        model.addAttribute("baseUri", request.getRequestURI());
        model.addAttribute("coupons", coupons);
        model.addAttribute("count", (int) coupons.getTotalElements());
        model.addAttribute("paginationUtils", paginationUtils);
    }
}
