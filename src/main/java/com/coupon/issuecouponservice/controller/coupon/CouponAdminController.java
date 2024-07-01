package com.coupon.issuecouponservice.controller.coupon;

import com.coupon.issuecouponservice.dto.request.coupon.CouponCreationParam;
import com.coupon.issuecouponservice.dto.request.coupon.CouponModificationParam;
import com.coupon.issuecouponservice.dto.response.ApiResponseForm;
import com.coupon.issuecouponservice.service.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.coupon.issuecouponservice.domain.user.Role.Authority.ADMIN;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Secured(ADMIN)
public class CouponAdminController {

    private final CouponService couponService;

    @PostMapping("/coupon")
    public ResponseEntity<ApiResponseForm> createCoupon(@RequestPart("param") CouponCreationParam param,
                                                        @RequestPart("couponImage") MultipartFile file) throws IOException {
        couponService.createCoupon(param, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseForm("쿠폰 생성 성공", HttpStatus.CREATED.value()));

    }

    @PatchMapping("/coupon/{couponId}")
    public void modifyCoupon(@PathVariable("couponId") Long couponId, @RequestBody CouponModificationParam param) {

        couponService.modifyCoupon(couponId, param);

    }

    @DeleteMapping("/coupon/{couponId}")
    public void deleteCoupon(@PathVariable("couponId") Long couponId) {

        couponService.deleteCoupon(couponId);

    }

}
