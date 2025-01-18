package com.example.nbe341team02.config.jwt;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {

    private final Key key; 
    private final long tokenValidityInMilliseconds;
    private final UserDetailsService userDetailsService;
    private final Set<String> blacklist = new HashSet<>();

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.token-validity-in-milliseconds}") long tokenValidityInMilliseconds,
            UserDetailsService userDetailsService
    ) {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.tokenValidityInMilliseconds = tokenValidityInMilliseconds;
        this.userDetailsService = userDetailsService;
    }

    // 토큰 생성
    public String createToken(String username, String role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role); // "ROLE_" 접두사가 포함된 role 저장

        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenValidityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims) // 페이로드 설정
                .setIssuedAt(now) // 토큰 발급 시간 설정
                .setExpiration(validity) // 토큰 만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS256) // 대칭키 생성 (SHA-256)
                .compact(); // String 방식으로 반환 (String compact())
    }

    // 토큰에서 인증 정보 추출
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities = 
            Collections.singletonList(new SimpleGrantedAuthority((String) claims.get("role")));
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
        
        log.debug("Token claims - username: {}, role: {}", claims.getSubject(), claims.get("role"));
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    // HTTP 요청에서 토큰 추출
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            if (blacklist.contains(token)) {
                return false;
            }
            // JWT 토큰 검증
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token); // JWT 토큰 파싱 , 검증 수행 후 페이로드 추출
            return true;
        } catch (Exception e) {
            log.error("Token validation failed: ", e);
            return false;
        }
    }

    public void invalidateToken(String token) {
        blacklist.add(token);
    }
}

