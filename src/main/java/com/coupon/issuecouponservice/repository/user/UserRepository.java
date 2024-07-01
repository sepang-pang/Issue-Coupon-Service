package com.coupon.issuecouponservice.repository.user;

import com.coupon.issuecouponservice.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUserImageAndId(String userFile, Long id);
}
