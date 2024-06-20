package com.coupon.issuecouponservice.service.user;

import com.coupon.issuecouponservice.domain.user.User;
import com.coupon.issuecouponservice.dto.request.user.UserModificationParam;
import com.coupon.issuecouponservice.repository.user.UserRepository;
import com.coupon.issuecouponservice.security.userdetails.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "User Service")
@Transactional
public class UserService {

    private final UserRepository userRepository;

    // 프로필 수정
    public void modifyUserProfile(User user, UserModificationParam param) {
        User findUser = getUser(user);

        findUser.modifyUserDetails(param);

        // 현재 인증 객체 가져오기
        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) currentAuth.getPrincipal();

        // UserDetailsImpl 내부의 User 정보 업데이트
        userDetails.updateUser(findUser);

        // 새로운 Authentication 객체 생성
        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                userDetails,
                currentAuth.getCredentials(),
                currentAuth.getAuthorities()
        );

        // SecurityContext에 업데이트된 Authentication 객체 재설정
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    private User getUser(User user) {
        return userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
    }
}
