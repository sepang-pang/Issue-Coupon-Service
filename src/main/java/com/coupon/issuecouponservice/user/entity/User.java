package com.coupon.issuecouponservice.user.entity;

import com.coupon.issuecouponservice.user.dto.UserParam;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "nick_name")
    private String nickname;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "provider", nullable = false)
    private String provider;

    @Column(name = "provider_id", nullable = false)
    private String providerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRoleEnum role;

    public static User createUser(UserParam param) {
        User user = User.builder()
                .username(param.getUsername())
                .nickname(param.getNickname())
                .email(param.getEmail())
                .role(UserRoleEnum.USER)
                .provider(param.getProvider())
                .providerId(param.getProviderId())
                .build();
        return user;
    }
}
