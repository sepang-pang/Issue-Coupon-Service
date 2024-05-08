package com.coupon.issuecouponservice.controller.coupon;

import com.coupon.issuecouponservice.dto.request.coupon.CouponModificationParam;
import com.coupon.issuecouponservice.dto.request.coupon.CouponCreationParam;
import com.coupon.issuecouponservice.dto.response.ApiResponseForm;
import com.coupon.issuecouponservice.dto.response.coupon.CouponForm;
import com.coupon.issuecouponservice.service.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.coupon.issuecouponservice.domain.user.Role.Authority.ADMIN;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Secured(ADMIN)
public class CouponAdminController {

    private final CouponService couponService;

    @PostMapping("/coupon")
    public ResponseEntity<ApiResponseForm> createCoupon(@RequestBody CouponCreationParam param) {

        couponService.createCoupon(param);

        return ResponseEntity.ok().body(new ApiResponseForm("쿠폰 생성 성공", HttpStatus.OK.value()));
    }

    @GetMapping("/coupon")
    public List<CouponForm> readAllCoupons() {

        return couponService.readAllCoupons();
    }

    @PatchMapping("/coupon/{couponId}")
    public ResponseEntity<ApiResponseForm> modifyCoupon(@PathVariable("couponId") Long couponId,
                                                        @RequestBody CouponModificationParam param) {

        couponService.modifyCoupon(couponId, param);

        return ResponseEntity.ok().body(new ApiResponseForm("쿠폰 생성 성공", HttpStatus.OK.value()));
    }

    @DeleteMapping("/coupon/{couponId}")
    public ResponseEntity<ApiResponseForm> deleteCoupon(@PathVariable("couponId") Long couponId) {

        couponService.deleteCoupon(couponId);

        return ResponseEntity.ok().body(new ApiResponseForm("쿠폰 삭제 성공", HttpStatus.OK.value()));
    }
}
