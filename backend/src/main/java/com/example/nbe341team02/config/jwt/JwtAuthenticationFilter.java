package com.example.nbe341team02.config.jwt;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    // HTTP 중복 요청 방지
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(request); // HTTP 요청 헤더에서 JWT 토큰 추출 작업

        if (token != null && jwtTokenProvider.validateToken(token)) { // 토큰 O , 유효성 검사 O
            Authentication auth = jwtTokenProvider.getAuthentication(token); // 토큰에서 사용자 정보 추출 후 Authentication 객체를 반환
            SecurityContextHolder.getContext().setAuthentication(auth); // 인증 된 사용자 정보를 SecurityContext 에 저장
        }

        filterChain.doFilter(request, response); // 다음 필터로 넘어가기
    }
}
