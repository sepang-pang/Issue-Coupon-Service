package com.coupon.issuecouponservice.common.config;

import com.coupon.issuecouponservice.common.sercurity.oauth.OAuth2LoginSuccessHandler;
import com.coupon.issuecouponservice.common.sercurity.oauth.PrincipalOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final PrincipalOAuth2UserService principalOauth2UserService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) //이놈!
                .authorizeHttpRequests((authorizationRequests) -> authorizationRequests
                        .requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .sessionManagement((sessionManagement) ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                .maximumSessions(1) // 최대 허용 세션 개수
                                .maxSessionsPreventsLogin(false) // 중복 로그인 방지, false => 이전 세션 만료 및 최근 세션 허용
                )
                .oauth2Login(oauth ->
                        oauth
                                .loginPage("/login")
                                .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint.userService(principalOauth2UserService)) // 로그인 완료 후 회원 정보 받기
                                .successHandler(oAuth2LoginSuccessHandler)
                                .permitAll()
                );

        return http.build();
    }

}
