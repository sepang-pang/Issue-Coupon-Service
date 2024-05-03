package com.coupon.issuecouponservice.user.controller;

import com.coupon.issuecouponservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class UserViewController {

    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;

    @GetMapping({"", "/"})
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/admin")
    public String adminPage(){
        return "adminPage";
    }

}
