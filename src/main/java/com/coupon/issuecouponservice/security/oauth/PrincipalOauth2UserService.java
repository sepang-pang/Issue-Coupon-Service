package com.coupon.issuecouponservice.security.oauth;

import com.coupon.issuecouponservice.domain.user.Role;
import com.coupon.issuecouponservice.domain.user.User;
import com.coupon.issuecouponservice.repository.user.UserRepository;
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

        Role role = Role.USER;
        String email = oAuth2User.getAttribute("email");
        String provider = userRequest.getClientRegistration().getClientId();
        String providerId = oAuth2User.getAttribute("sub");
        String username = provider + "_" + providerId;
        String nickName = (String) oAuth2User.getAttribute("name");

        User user = userRepository.findByUsername(username);

        if (user == null) {
            log.info("최초 로그인 및 회원가입 진행");
            user = User.builder()
                    .nickName(nickName)
                    .username(username)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();

            userRepository.save(user);

            log.info("회원가입 완료");
        }

        return new UserDetailsImpl(user, oAuth2User.getAttributes());
    }


}
