package com.coupon.issuecouponservice.controller.user;

import com.coupon.issuecouponservice.dto.request.user.UserModificationParam;
import com.coupon.issuecouponservice.dto.response.ApiResponseForm;
import com.coupon.issuecouponservice.dto.response.coupon.CouponForm;
import com.coupon.issuecouponservice.dto.response.user.UserForm;
import com.coupon.issuecouponservice.security.userdetails.UserDetailsImpl;
import com.coupon.issuecouponservice.service.coupon.CouponService;
import com.coupon.issuecouponservice.service.user.UserService;
import com.coupon.issuecouponservice.util.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CouponService couponService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user/profile/setup")
    public String setupProfile() {
        return "user/profile-setup";
    }

    @GetMapping("/user/my-page")
    public String myPage(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails,
                         @PageableDefault(size = 5) Pageable pageable) {
        UserForm userForm = new UserForm(userDetails.getUser());
        Page<CouponForm> coupons = couponService.readAllUserCoupons(userDetails.getUser(), pageable);

        PaginationUtils paginationUtils = new PaginationUtils(coupons, 5);

        model.addAttribute("user", userForm);
        model.addAttribute("coupons", coupons);
        model.addAttribute("count", (int) coupons.getTotalElements());
        model.addAttribute("paginationUtils", paginationUtils);

        return "user/my-page";
    }

    @ResponseBody
    @PatchMapping("/user/profile/setup")
    public ResponseEntity<ApiResponseForm> modifyUser(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                      @RequestPart("param") UserModificationParam param,
                                                      @RequestPart(value = "userImage", required = false) MultipartFile file) throws IOException {
        userService.modifyUserProfile(userDetails.getUser(), param, file);

        return ResponseEntity.ok().body(new ApiResponseForm("프로필 작성 완료", HttpStatus.OK.value()));
    }
}
