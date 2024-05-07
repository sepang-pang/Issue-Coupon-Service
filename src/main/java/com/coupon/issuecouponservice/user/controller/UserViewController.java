package com.coupon.issuecouponservice.user.controller;

import com.coupon.issuecouponservice.common.ApiResponse;
import com.coupon.issuecouponservice.user.dto.UserParam;
import com.coupon.issuecouponservice.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class UserViewController {

    private final UserService userService;

    // default 페이지
    @GetMapping({"", "/"})
    public String index(){
        return "index";
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    // 로그인 후 role 이 USER 인 회원만 접근 가능한 페이지
    @GetMapping("/user")
    @ResponseBody
    public String user(){
        return "userPage";
    }

    @GetMapping("/oauth2/signup")
    public String loadOauthSignUp(HttpServletRequest httpServletRequest, Model model){
        // 값 받아서 넘기기
        UserParam userData = (UserParam) httpServletRequest.getSession().getAttribute("USER_DATA");
        model.addAttribute(userData);
        return "signup";
    }

    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<ApiResponse> modifyUser(@RequestBody UserParam param) {
        userService.createUser(param);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("유저 생성 성공", HttpStatus.OK.value()));
    }

}