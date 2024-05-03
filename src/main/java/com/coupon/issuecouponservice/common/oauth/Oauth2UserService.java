package com.coupon.issuecouponservice.common.oauth;

import com.coupon.issuecouponservice.user.entity.User;
import com.coupon.issuecouponservice.user.entity.UserRoleEnum;
import com.coupon.issuecouponservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Oauth2UserService extends DefaultOAuth2UserService {

    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2UserInfo userInfo = null;
        System.out.println(oAuth2User.getAttributes());
        System.out.println(userRequest.getClientRegistration().getRegistrationId());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        System.out.println("registrationId = " + registrationId);
        if (registrationId.equals("kakao")) {
            userInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        } else {
            System.out.println("로그인 실패");
        }
        String provider = userInfo.getProvider();
        String providerId = userInfo.getProviderId();
        String oauth2Id = provider + "_" + providerId; //중복이 발생하지 않도록 provider와 providerId를 조합
        String username = userInfo.getName();
        String email = userInfo.getEmail();
        UserRoleEnum role = UserRoleEnum.USER; //일반 유저
        System.out.println(oAuth2User.getAttributes());
        Optional<User> findUser = userRepository.findByOauth2Id(oauth2Id);
        User user=null;
        if (findUser.isEmpty()) { //찾지 못했다면
            user = User.builder()
                    .oauth2Id(oauth2Id)
                    .name(username)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId).build();
            userRepository.save(user);
        }
        else{
            user=findUser.get();
        }
        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }

}
