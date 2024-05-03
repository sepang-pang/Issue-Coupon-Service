package com.coupon.issuecouponservice.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String oauth2Id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    private String provider;

    private String providerId;

    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    @Builder
    public User(String oauth2Id, String name, String email, UserRoleEnum role, String provider, String providerId) {
        this.oauth2Id = oauth2Id;
        this.username = name;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }

}
