package com.coupon.issuecouponservice.domain.image;

import com.coupon.issuecouponservice.domain.coupon.Coupon;
import com.coupon.issuecouponservice.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Image(Coupon coupon, String fileUrl) {
        this.coupon = coupon;
        this.imageUrl = fileUrl;
    }
}