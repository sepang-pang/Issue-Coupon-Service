package com.coupon.issuecouponservice.common.oauth;

import com.coupon.issuecouponservice.user.dto.UserParam;
import com.coupon.issuecouponservice.user.entity.User;
import com.coupon.issuecouponservice.user.entity.UserRoleEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        User user = ((UserDetailsImpl)authentication.getPrincipal()).getUser();

        if(user.getRole().equals(UserRoleEnum.GUEST)){
            UserParam userParam = new UserParam(user.getUsername(), user.getEmail(), user.getProvider(), user.getProviderId());
            request.getSession().setAttribute("USER_DATA", userParam);

            String redirectURL = UriComponentsBuilder.fromUriString("http://localhost:8080/oauth2/signup")
                    .build()
                    .encode(StandardCharsets.UTF_8)
                    .toUriString();
            getRedirectStrategy().sendRedirect(request, response, redirectURL);
        }else{
            String redirectURL = UriComponentsBuilder.fromUriString("http://localhost:8080/login-success")
                    .build()
                    .encode(StandardCharsets.UTF_8)
                    .toUriString();
            getRedirectStrategy().sendRedirect(request, response, redirectURL);
        }

    }
}
