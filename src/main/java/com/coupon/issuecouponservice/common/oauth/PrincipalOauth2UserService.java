package com.coupon.issuecouponservice.common.oauth;

import com.coupon.issuecouponservice.user.User;
import com.coupon.issuecouponservice.user.UserRoleEnum;
import com.coupon.issuecouponservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oAuth2User.getAttribute("sub");
        String username = provider + "_" + providerId; //중복이 발생하지 않도록 provider와 providerId를 조합
        String email = oAuth2User.getAttribute("email");
        UserRoleEnum role = UserRoleEnum.GUEST; //일반 유저

        Optional<User> findUsername = userRepository.findByUsername(username);
        User user=null;
        if (findUsername.isEmpty()) { //찾지 못했다면
            user = User.builder()
                    .username(username)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId).build();
        }

        return new UserDetailsImpl(user, oAuth2User.getAttributes());
    }

}