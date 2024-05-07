package com.coupon.issuecouponservice.user.dto;

import com.coupon.issuecouponservice.user.entity.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserParam {

    private String username;
    private String nickname;
    private String email;
    private String provider;
    private String providerId;
    private UserRoleEnum role;


    public UserParam(String username, String email, String provider, String providerId) {
        this.username = username;
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
    }
}