package com.coupon.issuecouponservice.domain.user;

import com.coupon.issuecouponservice.domain.common.Timestamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends Timestamped {

    @Id
    private Long id;

    @Column(name = "nick_name", nullable = false)
    private String nickName;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "tel", nullable = false)
    private String tel;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "provider", nullable = false)
    private String provider;

    @Column(name = "provider_id", nullable = false)
    private String providerId;

    @Builder
    public User(String nickName, String username, String email, String tel, Role role, String provider, String providerId) {
        this.nickName = nickName;
        this.username = username;
        this.email = email;
        this.tel = tel;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }
}
