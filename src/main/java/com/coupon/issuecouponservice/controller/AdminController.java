package com.coupon.issuecouponservice.controller;

import com.coupon.issuecouponservice.dto.response.coupon.CouponForm;
import com.coupon.issuecouponservice.service.coupon.CouponService;
import com.coupon.issuecouponservice.util.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.coupon.issuecouponservice.domain.user.Role.Authority.ADMIN;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Secured(ADMIN)
public class AdminController {

    private final CouponService couponService;

    @GetMapping()
    public String index() {
        return "admin/admin-main";
    }

    @GetMapping("/coupons")
    public String readAllCoupons(Model model, @PageableDefault(size = 9) Pageable pageable) {
        Page<CouponForm> coupons = couponService.readAllCoupons(pageable);
        addModelAttributes(model, coupons, "전체 쿠폰 조회");
        return "admin/admin-coupons";
    }

    @GetMapping("/coupons/upcoming")
    public String readAllOpenCoupons(Model model, @PageableDefault(size = 9) Pageable pageable) {
        Page<CouponForm> coupons = couponService.readAllOpenCoupons(pageable);
        addModelAttributes(model, coupons, "오픈 예정 쿠폰 조회");
        return "admin/admin-coupons";
    }

    @GetMapping("/coupons/closed")
    public String readAllClosedCoupons(Model model, @PageableDefault(size = 9) Pageable pageable) {
        Page<CouponForm> coupons = couponService.readAllClosedCoupons(pageable);
        addModelAttributes(model, coupons, "마감 쿠폰 조회");
        return "admin/admin-coupons";
    }

    private void addModelAttributes(Model model, Page<CouponForm> coupons, String category) {
        PaginationUtils paginationUtils = new PaginationUtils(coupons, 10);
        model.addAttribute("coupons", coupons);
        model.addAttribute("count", (int) coupons.getTotalElements());
        model.addAttribute("paginationUtils", paginationUtils);
        model.addAttribute("category", category);
    }
}
