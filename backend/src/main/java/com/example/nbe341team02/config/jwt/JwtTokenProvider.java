package com.example.nbe341team02.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.nbe341team02.admin.service.AdminService;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret}") // application.yml 에서 jwt.secret 값 가져와야 하니까 yml 에 비밀 키 추가하기 (까먹지 말고 추가)
    private String secretKey;

    private final AdminService adminService;
    private Key key; // 서명 키 저장

    private long tokenValidTime = 30 * 60 * 1000L; // 일단 토큰 유효시간 30분으로 설정

    @PostConstruct
    protected void init() { // 초기화 후 비밀키를 사용하여 JWT 서명 키를 생성하기
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String createToken(String username, String roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims) // 페이로드 설정
                .setIssuedAt(now) // 토큰 발급 시간 설정
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // 토큰 만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS256) // 대칭키 생성 (SHA-256)
                .compact(); // String 방식으로 반환 (String compact())
    }
    // 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = adminService.loadUserByUsername(this.getUserPk(token)); // 사용자 식별자 추출
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject(); // JWT 토큰을 파싱 한 뒤 페이로드 추출
    }

    // Request의 Header에서 token 값을 가져오는 부분
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) { // Bearer = JWT 토큰을 사용하기 위한 규약
            return bearerToken.substring(7); // Bearer 부분 제외 실제 JWT 토큰 반환
        }
        return null;
    }

    // 토큰의 유효성 + 만료일자 체크
    public boolean validateToken(String jwtToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken); // JWT 토큰 파싱 , 검증 수행 후 페이로드 추출
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}