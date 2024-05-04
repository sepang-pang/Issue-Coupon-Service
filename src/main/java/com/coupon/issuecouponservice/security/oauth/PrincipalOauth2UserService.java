package com.coupon.issuecouponservice.security.oauth;

import com.coupon.issuecouponservice.domain.user.Role;
import com.coupon.issuecouponservice.domain.user.User;
import com.coupon.issuecouponservice.repository.user.UserRepository;
import com.coupon.issuecouponservice.security.GoogleUserInfo;
import com.coupon.issuecouponservice.security.KakaoUserInfo;
import com.coupon.issuecouponservice.security.OAuth2UserInfo;
import com.coupon.issuecouponservice.security.userdetails.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "PrincipalOauth2UserService")
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        }

        String username = oAuth2UserInfo.getUsername();
        String nickName = oAuth2UserInfo.getNickName();
        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();


        User user = userRepository.findByUsername(username)
                .orElseGet(() -> {
                    log.info("최초 로그인 및 회원가입 진행");
                    User newUser = User.builder()
                            .nickName(nickName)
                            .username(username)
                            .email(email)
                            .role(Role.USER)
                            .provider(provider)
                            .providerId(providerId)
                            .build();
                    userRepository.save(newUser);
                    log.info("회원가입 완료");
                    return newUser;
                });
        return new UserDetailsImpl(user, oAuth2User.getAttributes());
    }
}
