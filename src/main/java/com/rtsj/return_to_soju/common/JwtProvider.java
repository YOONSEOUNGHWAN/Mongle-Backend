package com.rtsj.return_to_soju.common;


import com.rtsj.return_to_soju.common.auth.CustomJwtUserDetailsService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Random;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtProvider {
    private final CustomJwtUserDetailsService customUserDetailsService;
    @Value("${jwt.access-token.expire-length}")
    private long accessTokenValidityInMilliseconds;
    @Value("${jwt.refresh-token.expire-length}")
    private long refreshTokenValidityInMilliseconds;
    @Value("${jwt.token.secret-key}")
    private String secretKey;

    public String createAccessToken(String payload){
        return createToken(payload, accessTokenValidityInMilliseconds);
    }

    public String createRefreshToken(){
        byte[] array = new byte[7];
        new Random().nextBytes(array);
        String generatedString = new String(array, StandardCharsets.UTF_8);
        return createToken(generatedString, refreshTokenValidityInMilliseconds);
    }

    public String createToken(String payload, long expireLength){
        Claims claims = Jwts.claims().setSubject(payload);
        Date now = new Date();
        Date validity = new Date(now.getTime() + expireLength);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
    }

    public String getPayload(String token){
        try{
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        }catch (ExpiredJwtException e){
            return e.getClaims().getSubject();
        }catch (JwtException e){
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }
    }

    public boolean validateToken(String token){
        try{
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        }catch (JwtException | IllegalArgumentException exception){
            return false;
        }
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(this.getPayload(token));
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    public Long getUserIdByToken(HttpServletRequest request) {
        String token = request.getHeader("X-AUTH-TOKEN");
        String userId = this.getPayload(token);
        return Long.parseLong(userId);
    }

}
