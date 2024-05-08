package com.coupon.issuecouponservice.domain.coupon;

import com.coupon.issuecouponservice.domain.common.Timestamped;
import com.coupon.issuecouponservice.dto.request.coupon.CouponCreationParam;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends Timestamped {

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

    @Column(name = "expired_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime expiredAt;

    @Builder
    public Coupon(String couponName, int totalQuantity, LocalDateTime expiredAt) {
        this.couponName = couponName;
        this.totalQuantity = totalQuantity;
        this.remainQuantity = totalQuantity; // 생성 시점에서 초기 잔여 수량은 전체 수량이다.
        this.expiredAt = expiredAt;
    }

    public static Coupon CreateCoupon(CouponCreationParam param) {
        return Coupon.builder()
                .couponName(param.getCouponName())
                .totalQuantity(param.getTotalQuantity())
                .expiredAt(param.getExpiredAt())
                .build();
    }
}
