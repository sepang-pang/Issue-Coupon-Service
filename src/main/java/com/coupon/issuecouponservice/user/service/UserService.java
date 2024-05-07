package com.coupon.issuecouponservice.user.service;

import com.coupon.issuecouponservice.user.dto.UserParam;
import com.coupon.issuecouponservice.user.entity.User;
import com.coupon.issuecouponservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void createUser(UserParam param) {
        User user = User.createUser(param);
        userRepository.save(user);
    }
}
