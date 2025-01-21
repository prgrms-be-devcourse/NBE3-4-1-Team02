package com.example.nbe341team02.config.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
 
    private final JwtTokenProvider jwtTokenProvider;

    // HTTP 중복 요청 방지
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(request);
        
        // 헤더에서 토큰을 찾지 못한 경우 파라미터에서 찾기
        if (token == null) {
            token = request.getParameter("token");
        }
        
        log.debug("수신된 토큰: {}", token);

        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                
                // 디버깅을 위한 로그 추가
                log.debug("Request URI: {}", request.getRequestURI());
                log.debug("User Authorities: {}", auth.getAuthorities());

                // 블랙리스트 토큰 체크
                if (jwtTokenProvider.isTokenBlacklisted(token)) {
                    log.warn("블랙리스트에 등록된 토큰으로 접근 시도");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write("{\"message\": \"로그아웃된 토큰입니다. 다시 로그인해주세요.\"}");
                    return;
                }
                
                // admin/** 경로에 대한 ROLE_ADMIN 권한 검증
                if (request.getRequestURI().startsWith("/admin/") && 
                    !request.getRequestURI().equals("/admin/login")) {
                    boolean hasAdminRole = auth.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
                    
                    log.debug("Has Admin Role: {}", hasAdminRole);
                    
                    if (!hasAdminRole) {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        response.getWriter().write("접근 권한이 없습니다.");
                        return;
                    }
                }
                
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e) {
            log.error("JWT 처리 중 오류 발생: ", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
