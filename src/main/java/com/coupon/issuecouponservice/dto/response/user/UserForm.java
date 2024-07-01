package com.coupon.issuecouponservice.dto.response.user;

import com.coupon.issuecouponservice.domain.user.User;
import lombok.Getter;

@Getter
public class UserForm {

    private String nickName;

    private String email;

    private String image;

    private String provider;

    public UserForm(User user) {
        this.nickName = user.getNickName();
        this.email = user.getEmail();
        this.image = user.getUserImage();
        this.provider = user.getProvider();
    }
}
