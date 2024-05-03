package com.coupon.issuecouponservice.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRoleEnum {

    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    private final String key;


}
