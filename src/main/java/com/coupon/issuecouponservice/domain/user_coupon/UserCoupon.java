package com.coupon.issuecouponservice.domain.user_coupon;

import com.coupon.issuecouponservice.domain.coupon.Coupon;
import com.coupon.issuecouponservice.domain.user.User;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_coupon")
public class UserCoupon {

    @Id
    @Tsid
    private Long id;

    @ManyToOne
    @JoinColumn(name="coupon_id")
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public UserCoupon(Coupon coupon, User user){
        this.coupon = coupon;
        this.user = user;
    }

}