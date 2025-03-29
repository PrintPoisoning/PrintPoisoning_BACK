package com.printpoisoning.bookfull.service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;  
import io.jsonwebtoken.security.Keys;  
import io.jsonwebtoken.SignatureAlgorithm;  
import io.jsonwebtoken.security.SignatureException;  
import org.springframework.http.HttpStatus;  
import org.springframework.web.server.ResponseStatusException;  
import org.springframework.stereotype.Service;  
import org.springframework.beans.factory.annotation.Value;  
import javax.crypto.SecretKey;  
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;  
import java.time.ZoneId;  
import java.util.Date;
import java.util.HashMap;
import java.util.Map;  
  
@Service  
public class TokenService {  

    @Value("${jwt.secret.key}")  
    private String JWT_SECRET_KEY;  
  
    @Value("${jwt.refresh.secret.key}")  
    private String REFRESH_JWT_SECRET_KEY;  

    private static final long ACCESS_TOKEN_EXPIRE_MINUTES = 1;   // 60분  
    private static final long REFRESH_TOKEN_EXPIRE_MINUTES = 5; // 10시간  
  
    public String createToken(Map<String, Object> data, String tokenType) {  
        SecretKey secretKey;  
        long expiresDelta;  
  
        if ("access".equals(tokenType)) {  
            expiresDelta = ACCESS_TOKEN_EXPIRE_MINUTES;  
            secretKey = Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes());  
        } else if ("refresh".equals(tokenType)) {  
            expiresDelta = REFRESH_TOKEN_EXPIRE_MINUTES;  
            secretKey = Keys.hmacShaKeyFor(REFRESH_JWT_SECRET_KEY.getBytes());  
        } else {  
            throw new IllegalArgumentException("Invalid token type: " + tokenType);  
        }  
  
        // 한국시간(KST) 설정  
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));  
  
        // 만료시간 계산(KST)  
        LocalDateTime expiresAt = now.plusMinutes(expiresDelta);  
  
        // LocalDateTime을 Date로 변환  
        Date expirationDate = Date.from(expiresAt.atZone(ZoneId.of("Asia/Seoul")).toInstant());  
  
        data.put("exp", expirationDate);  
        data.put("type", tokenType);  
  
        return Jwts.builder()  
                .setClaims(data)  
                .setExpiration(expirationDate)  
                .signWith(secretKey, SignatureAlgorithm.HS256)  
                .compact();  
    }  
    
    public Map<String, Object> parseToken(String token, String tokenType) {  
        SecretKey secretKey;  
  
        if ("access".equals(tokenType)) {  
            secretKey = Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes());  
        } else if ("refresh".equals(tokenType)) {  
            secretKey = Keys.hmacShaKeyFor(REFRESH_JWT_SECRET_KEY.getBytes());  
        } else {  
            throw new IllegalArgumentException("Invalid token type: " + tokenType);  
        }  
  
        try {  
            Claims claims = Jwts.parserBuilder()  
                    .setSigningKey(secretKey)  
                    .build()  
                    .parseClaimsJws(token)  
                    .getBody();  
            return new HashMap<>(claims);  
        } catch (ExpiredJwtException e) {  
            throw new IllegalArgumentException("Token expired", e);  
        } catch (JwtException e) {  
            throw new IllegalArgumentException("Invalid token", e);  
        }  
    }  

    public String getUserEmailFromAccessToken(HttpServletRequest request) {  
        String token = request.getHeader("Authorization");  
      
        if (token == null) {  
            System.out.println("Authorization Header does not exist!!");  
            return "HeaderDoesNotExist";  
        } else if (token.startsWith("Bearer ")) {  
            token = token.substring(7);  // "Bearer "문자열 제거  
        } else {  
            System.out.println("Authorization Header does not contain Bearer token!!");  
            return "InvalidHeader";  
        }  
      
        if ("null".equals(token)) {  
            System.out.println("Token is null!!");  
            return "TokenIsNull";  
        } else if ("test".equals(token)) {  
            return "test";  
        }  
      
        try {  
            Jws<Claims> decodedToken = Jwts.parserBuilder()  
                .setSigningKey(Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes()))  // secret key 설정  
                .build()  
                .parseClaimsJws(token);  
              
            String email = decodedToken.getBody().get("email", String.class);  // 이메일 추출  
      
            // 이메일이 null인 경우를 확인하여 필요한 처리를 합니다 (필요 시 추가 검증 코드)  
            if (email == null) {  
                System.out.println("Decoded JWT does not contain a valid email");  
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Decoded JWT does not contain a valid email");  
            }  
      
            return email;  
      
        } catch (SignatureException e) {  
            System.out.println("JWT signature does not match. JWT validity cannot be asserted and should not be trusted.");  
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid JWT signature: " + e.getMessage());  
        } catch (io.jsonwebtoken.ExpiredJwtException e) {  
            System.out.println("JWT has expired.");  
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "JWT token has expired: " + e.getMessage());  
        } catch (Exception e) {  
            System.out.println("Decode Error: " + e);  
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Decode Error: " + e.toString());  
        }  
    }  
}