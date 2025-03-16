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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.printpoisoning.bookfull.dto.request.LoginReqDTO;
import com.printpoisoning.bookfull.dto.response.KakaoUserResponseDTO;
import com.printpoisoning.bookfull.dto.response.LoginResDTO;
import com.printpoisoning.bookfull.entity.User;
import com.printpoisoning.bookfull.service.TokenService;
import com.printpoisoning.bookfull.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag; 

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
        ResponseEntity<KakaoUserResponseDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, KakaoUserResponseDTO.class);  
          
        // 응답을 바탕으로 사용자 정보를 UserAddReqDTO 객체에 매핑  
        KakaoUserResponseDTO kakaoUser = response.getBody();  
          
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

}  