package com.coupon.issuecouponservice.user.repository;

import com.coupon.issuecouponservice.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByOauth2Id(String oauth2Id);
}
