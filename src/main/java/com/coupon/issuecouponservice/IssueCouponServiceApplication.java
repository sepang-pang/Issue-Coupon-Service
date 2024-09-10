package com.coupon.issuecouponservice;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.util.TimeZone;

@EnableJpaAuditing
@SpringBootApplication
public class IssueCouponServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IssueCouponServiceApplication.class, args);
	}

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}

}
