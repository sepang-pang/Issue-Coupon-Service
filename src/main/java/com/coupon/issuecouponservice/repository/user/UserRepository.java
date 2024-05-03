package com.coupon.issuecouponservice.repository.user;

import com.coupon.issuecouponservice.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
