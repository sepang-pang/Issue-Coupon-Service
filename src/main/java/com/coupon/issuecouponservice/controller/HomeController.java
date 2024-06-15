package com.coupon.issuecouponservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    @GetMapping({"", "/"})
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/upcoming-coupons")
    public String upcoming() {
        return "upcoming-coupons";
    }

    @GetMapping("/past-coupons")
    public String past() {
        return "past-coupons";
    }

    @GetMapping("/my-page")
    public String myPage() {
        return "my-page";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
