package com.coupon.issuecouponservice.controller.coupon;

import com.coupon.issuecouponservice.dto.request.coupon.CouponIssueParam;
import com.coupon.issuecouponservice.dto.response.ApiResponseForm;
import com.coupon.issuecouponservice.dto.response.coupon.CouponForm;
import com.coupon.issuecouponservice.facade.RedissonLockFacade;
import com.coupon.issuecouponservice.security.userdetails.UserDetailsImpl;
import com.coupon.issuecouponservice.service.coupon.CouponService;
import com.coupon.issuecouponservice.util.PaginationUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.coupon.issuecouponservice.domain.user.Role.Authority.USER;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
@Secured(USER)
public class CouponUserController {

    private final CouponService couponService;
    private final RedissonLockFacade redissonLockFacade;

    // 쿠폰 발급
    @ResponseBody
    @PostMapping("/coupon")
    public ResponseEntity<ApiResponseForm> issueCoupon(@RequestBody CouponIssueParam couponIssueParam, @AuthenticationPrincipal UserDetailsImpl userDetails){
        redissonLockFacade.issueCouponWithLock(couponIssueParam, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseForm("쿠폰 발급에 성공했습니다.", HttpStatus.OK.value()));
    }

}