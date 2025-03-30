package com.printpoisoning.bookfull.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.printpoisoning.bookfull.dto.request.LoginReqDTO;
import com.printpoisoning.bookfull.dto.request.SignupReqDTO;
import com.printpoisoning.bookfull.dto.response.ErrorCodeResDTO;
import com.printpoisoning.bookfull.dto.response.KakaoUserResDTO;
import com.printpoisoning.bookfull.dto.response.LoginResDTO;
import com.printpoisoning.bookfull.dto.response.SignupResDTO;
import com.printpoisoning.bookfull.dto.response.RefreshTokenResDTO;
import com.printpoisoning.bookfull.entity.User;
import com.printpoisoning.bookfull.service.TokenService;
import com.printpoisoning.bookfull.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest; 

import java.util.Collections; 

@RestController
@RequestMapping("/auth")
@Tag(name = "AUTH", description = "로그인, 토큰 발급 등 인증 관련 API")  
public class authController {
    
    @Autowired  
    private UserService userService; 
    
    @Autowired  
    private TokenService tokenService; 

    @PostMapping("/login")  
    @Operation(summary = "login", description = "카카오 로그인 API")
    public ResponseEntity<LoginResDTO> addUser(@Validated @RequestBody LoginReqDTO loginReqDTO) {  

  
        // Kakao API를 사용하기 위해 RestTemplate 객체 생성  
        RestTemplate restTemplate = new RestTemplate();  
  
        String url = "https://kapi.kakao.com/v2/user/me";  
          
        // HTTP headers 설정  
        HttpHeaders headers = new HttpHeaders();  
        headers.set("Authorization", "Bearer " + loginReqDTO.getAccessToken());  
          
        HttpEntity<String> entity = new HttpEntity<>(headers);  
  
        // Kakao API 호출 및 응답 받기  
        ResponseEntity<KakaoUserResDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, KakaoUserResDTO.class);  
          
        // 응답을 바탕으로 사용자 정보를 UserAddReqDTO 객체에 매핑  
        KakaoUserResDTO kakaoUser = response.getBody();  
          
        if (kakaoUser == null) {  
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);  
        }  

        String email = kakaoUser.getKakao_account().getEmail();

        // 카카오 이메일로 회원가입 여부 조회  
        User user = userService.getUserByEmail(email);

        LoginResDTO loginResDTO = new LoginResDTO();

        // 사용자 인증을 위한 데이터  
        Map<String, Object> data = new HashMap<>();  
        data.put("email", email); 
        
        if (user == null) {
            loginResDTO.setIsMember(false);
            loginResDTO.setAccessToken(null);
            loginResDTO.setRefreshToken(null);
        }
        else {
            // Access Token과 Refresh Token 생성  
            String accessToken = tokenService.createToken(data, "access");  
            String refreshToken = tokenService.createToken(data, "refresh");  
            loginResDTO.setIsMember(true);
            loginResDTO.setAccessToken(accessToken);
            loginResDTO.setRefreshToken(refreshToken);

            User updatedUser = userService.updateUser(loginResDTO, email); 
            
        }
        

        return new ResponseEntity<>(loginResDTO, HttpStatus.CREATED);
    }

    @PostMapping("")   
    @Operation(summary = "signup", description = "회원 가입 API")
    public ResponseEntity<SignupResDTO> signup(@Validated @RequestBody SignupReqDTO userAddReqDTO) {  
        // 토큰 값 추출  
        String token = userAddReqDTO.getKakaoToken(); 
        
        // Kakao API를 사용하기 위해 RestTemplate 객체 생성  
        RestTemplate restTemplate = new RestTemplate();  
  
        String url = "https://kapi.kakao.com/v2/user/me";  
          
        // HTTP headers 설정  
        HttpHeaders headers = new HttpHeaders();  
        headers.set("Authorization", "Bearer " + token);  
          
        HttpEntity<String> entity = new HttpEntity<>(headers);  
  
        // Kakao API 호출 및 응답 받기  
        ResponseEntity<KakaoUserResDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, KakaoUserResDTO.class);  
          
        // 응답을 바탕으로 사용자 정보를 UserAddReqDTO 객체에 매핑  
        KakaoUserResDTO kakaoUser = response.getBody();  
          
        if (kakaoUser == null) {  
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);  
        }  

        String email = kakaoUser.getKakao_account().getEmail();

        User user = userService.createUser(userAddReqDTO, email);
        
        SignupResDTO userAddResDTO = new SignupResDTO();
        userAddResDTO.setUserId(user.getEmail());
        userAddResDTO.setNickname(user.getNickname());
        userAddResDTO.setIsPublic(user.getIsPublic());

        return new ResponseEntity<>(userAddResDTO, HttpStatus.CREATED);
    } 


    @PostMapping("/refresh-token")  
    @Operation(summary = "refreshToken", description = "기존의 refreshToken을 사용하여 새로운 accessToken과 refreshToken을 발급받기 위한 API")  
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        
        String refreshToken = request.getHeader("Authorization"); 
        
        if (refreshToken == null || !refreshToken.startsWith("Bearer ")) {  
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Invalid refresh token format"));  
        }  
        // "Bearer " 접두사 제거  
        refreshToken = refreshToken.substring(7);  

        Map<String, Object> claims;  
        try {  
            claims = tokenService.parseToken(refreshToken, "refresh");  
        } catch (IllegalArgumentException e) { 
            ErrorCodeResDTO errorCodeResDTO = new ErrorCodeResDTO(); 
            if (e.getCause() instanceof io.jsonwebtoken.ExpiredJwtException) {  
                errorCodeResDTO.setErrorCode("2005");
                return new ResponseEntity<>(errorCodeResDTO, HttpStatus.OK);
            }  
            errorCodeResDTO.setErrorCode("2007");
            return new ResponseEntity<>(errorCodeResDTO, HttpStatus.OK);
        }  
        // 유효한 리프레시 토큰인 경우 새로운 액세스 토큰 발급  
        Map<String, Object> data = new HashMap<>();  
        String email = (String) claims.get("email"); // 원래 데이터에서 필요한 필드 복사  
        data.put("email", email);  

        String newAccessToken = tokenService.createToken(data, "access");
        String newRefreshToken = tokenService.createToken(data, "refresh");
        
        Map<String, String> response = new HashMap<>();  
        response.put("accessToken", newAccessToken); 
        response.put("refreshToken", newRefreshToken);
        
        LoginResDTO loginResDTO = new LoginResDTO();

        loginResDTO.setAccessToken(newAccessToken);
        loginResDTO.setRefreshToken(newRefreshToken);
        userService.updateUser(loginResDTO, email); 

        return ResponseEntity.ok(response);  
    } 
}  