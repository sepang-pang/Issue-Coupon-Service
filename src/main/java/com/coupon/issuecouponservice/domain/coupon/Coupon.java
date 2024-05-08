package com.coupon.issuecouponservice.domain.coupon;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id
    @Tsid
    private Long id;

    @Column(name = "coupon_name", nullable = false)
    private String couponName;

    @Column(name = "total_quantity", nullable = false)
    private int totalQuantity;

    @Column(name = "remain_quantity", nullable = false)
    private int remainQuantity;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Builder
    public Coupon(String couponName, int totalQuantity, int remainQuantity) {
        this.couponName = couponName;
        this.totalQuantity = totalQuantity;
        this.remainQuantity = remainQuantity;
    }
}
