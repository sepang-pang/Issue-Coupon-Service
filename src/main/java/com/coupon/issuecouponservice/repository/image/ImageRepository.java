package com.coupon.issuecouponservice.repository.image;

import com.coupon.issuecouponservice.domain.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Boolean existsByImageUrlAndId(String fileName, Long id);
}
