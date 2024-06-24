package com.coupon.issuecouponservice.domain.coupon;

public enum ValidityStatus {
    VALID("유효"),
    EXPIRED("만료"),
    EXPIRING_SOON("만료 임박");

    private final String description;

    ValidityStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}