package com.example.nbe341team02.global.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
<<<<<<< HEAD:backend/src/main/java/com/example/nbe341team02/config/SecurityConfig.java
=======
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
>>>>>>> main:backend/src/main/java/com/example/nbe341team02/global/config/SecurityConfig.java
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // CSRF 보호 비활성화 (H2 콘솔에 필요)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/h2-console/**").permitAll()  // H2 콘솔 경로 허용
                        .anyRequest().permitAll()  // 다른 모든 요청 허용
                )
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin())  // H2 콘솔에 iframe을 사용할 수 있도록 설정
                );
        return http.build();
    }
<<<<<<< HEAD:backend/src/main/java/com/example/nbe341team02/config/SecurityConfig.java
}
=======

    @Bean
    @ConditionalOnProperty(name = "spring.h2.console.enabled",havingValue = "true")
    public WebSecurityCustomizer configureH2ConsoleEnable() {
        return web -> web.ignoring()
          .requestMatchers(PathRequest.toH2Console());
    }
}
>>>>>>> main:backend/src/main/java/com/example/nbe341team02/global/config/SecurityConfig.java
