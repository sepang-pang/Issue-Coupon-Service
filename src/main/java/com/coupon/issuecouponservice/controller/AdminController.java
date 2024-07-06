package com.coupon.issuecouponservice.controller;

import com.coupon.issuecouponservice.dto.response.coupon.CouponForm;
import com.coupon.issuecouponservice.dto.response.coupon.CouponOneForm;
import com.coupon.issuecouponservice.service.coupon.CouponService;
import com.coupon.issuecouponservice.util.PaginationUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/create-coupon")
    public String createCoupon() {
        return "admin/create-coupon";
    }

    @GetMapping("/update-coupon/{couponId}")
    public String updateCoupon(Model model, @PathVariable Long couponId) {
        CouponOneForm couponOneForm = couponService.selectCoupon(couponId);
        model.addAttribute("coupon", couponOneForm);
        return "admin/update-coupon";
    }

    @GetMapping("/coupons")
    public String readAllCoupons(HttpServletRequest request, Model model, @PageableDefault(size = 9) Pageable pageable) {
        Page<CouponForm> coupons = couponService.readAllCoupons(pageable);
        addModelAttributes(request, model, coupons, "전체 쿠폰 조회");
        return "admin/admin-coupons";
    }

    @GetMapping("/coupons/upcoming")
    public String readAllOpenCoupons(HttpServletRequest request, Model model, @PageableDefault(size = 9) Pageable pageable) {
        Page<CouponForm> coupons = couponService.readAllOpenCoupons(pageable);
        addModelAttributes(request, model, coupons, "오픈 예정 쿠폰 조회");
        return "admin/admin-coupons";
    }

    @GetMapping("/coupons/closed")
    public String readAllClosedCoupons(HttpServletRequest request, Model model, @PageableDefault(size = 9) Pageable pageable) {
        Page<CouponForm> coupons = couponService.readAllClosedCoupons(pageable);
        addModelAttributes(request, model, coupons, "마감 쿠폰 조회");
        return "admin/admin-coupons";
    }

    private void addModelAttributes(HttpServletRequest request, Model model, Page<CouponForm> coupons, String category) {
        PaginationUtils paginationUtils = new PaginationUtils(coupons, 10);
        model.addAttribute("baseUri", request.getRequestURI());
        model.addAttribute("coupons", coupons);
        model.addAttribute("count", (int) coupons.getTotalElements());
        model.addAttribute("paginationUtils", paginationUtils);
        model.addAttribute("category", category);
    }
}
