package com.printpoisoning.bookfull.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.printpoisoning.bookfull.dto.request.UserAddReqDTO;
import com.printpoisoning.bookfull.dto.request.UserUpdateReqDTO;
import com.printpoisoning.bookfull.dto.response.KakaoUserResponseDTO;
import com.printpoisoning.bookfull.dto.response.UserAddResDTO;
import com.printpoisoning.bookfull.entity.User;
import com.printpoisoning.bookfull.service.TokenService;
import com.printpoisoning.bookfull.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
@Tag(name = "USER", description = "사용자 관리 API")  
public class userController {
    
    @Autowired  
    private UserService userService; 
    
    @Autowired  
    private TokenService tokenService; 

    @PostMapping("")  
    @Operation(summary = "addUser", description = "회원 가입 API")
    public ResponseEntity<UserAddResDTO> addUser(@RequestHeader("Authorization") String authorizationHeader, @Validated @RequestBody UserAddReqDTO userAddReqDTO) {  
        // Bearer 토큰 값 추출  
        String token = authorizationHeader.substring(7); // "Bearer " 이후의 토큰 값 추출  
        
        // Kakao API를 사용하기 위해 RestTemplate 객체 생성  
        RestTemplate restTemplate = new RestTemplate();  
  
        String url = "https://kapi.kakao.com/v2/user/me";  
          
        // HTTP headers 설정  
        HttpHeaders headers = new HttpHeaders();  
        headers.set("Authorization", "Bearer " + token);  
          
        HttpEntity<String> entity = new HttpEntity<>(headers);  
  
        // Kakao API 호출 및 응답 받기  
        ResponseEntity<KakaoUserResponseDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, KakaoUserResponseDTO.class);  
          
        // 응답을 바탕으로 사용자 정보를 UserAddReqDTO 객체에 매핑  
        KakaoUserResponseDTO kakaoUser = response.getBody();  
          
        if (kakaoUser == null) {  
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);  
        }  

        String email = kakaoUser.getKakao_account().getEmail();

        User user = userService.createUser(userAddReqDTO, email);

        UserAddResDTO userAddResDTO = new UserAddResDTO();
        userAddResDTO.setUserId(user.getEmail());
        userAddResDTO.setNickname(user.getNickname());
        userAddResDTO.setBirthdate(user.getBirthdate());
        userAddResDTO.setIsPublic(user.getIsPublic());
        userAddResDTO.setGender(user.getGender());

        return new ResponseEntity<>(userAddResDTO, HttpStatus.CREATED);
    }

    @PutMapping("")  
    @Operation(summary = "updateUser", description = "회원 수정 API")  
    public ResponseEntity<UserAddResDTO> updateUser(HttpServletRequest request, @Validated @RequestBody UserUpdateReqDTO userUpdateReqDTO) {  
        
        String email = tokenService.getUserEmailFromAccessToken(request);
           
        User updatedUser = userService.updateUser(userUpdateReqDTO, email);  
      
        UserAddResDTO userAddResDTO = new UserAddResDTO();  
        userAddResDTO.setUserId(updatedUser.getEmail());  
        userAddResDTO.setNickname(updatedUser.getNickname());  
        userAddResDTO.setBirthdate(updatedUser.getBirthdate());  
        userAddResDTO.setIsPublic(updatedUser.getIsPublic());  
        userAddResDTO.setGender(updatedUser.getGender());  
          
        return new ResponseEntity<>(userAddResDTO, HttpStatus.OK);  
    }  

    // @RequestHeader("Authorization") String token
    @DeleteMapping("")  
    @Operation(summary = "deleteUser", description = "회원 탈퇴 API")
    public String deleteUser(HttpServletRequest request) {  
        
        String email = tokenService.getUserEmailFromAccessToken(request);

        // 이메일로 사용자를 조회  
        User user = userService.getUserByEmail(email); 
        
        // 사용자 삭제  
        userService.deleteUser(user);  

        // 삭제된 사용자 정보를 담은 응답 생성  
        UserAddResDTO userAddResDTO = new UserAddResDTO();  
        userAddResDTO.setUserId(user.getEmail());  
        userAddResDTO.setNickname(user.getNickname());  
        userAddResDTO.setBirthdate(user.getBirthdate());  
        userAddResDTO.setIsPublic(user.getIsPublic());  
        userAddResDTO.setGender(user.getGender());  
  
        return "삭제 완료";
    } 
  
    @GetMapping("")  
    @Operation(summary = "getMe", description = "내 정보 확인 API")  
    public ResponseEntity<UserAddResDTO> getMe(HttpServletRequest request) {
        
        String email = tokenService.getUserEmailFromAccessToken(request);

        // 이메일로 사용자를 조회  
        User user = userService.getUserByEmail(email);

        UserAddResDTO userAddResDTO = new UserAddResDTO();
        userAddResDTO.setUserId(user.getEmail());
        userAddResDTO.setNickname(user.getNickname());
        userAddResDTO.setBirthdate(user.getBirthdate());
        userAddResDTO.setIsPublic(user.getIsPublic());
        userAddResDTO.setGender(user.getGender());

        return new ResponseEntity<>(userAddResDTO, HttpStatus.CREATED);
    }  
}  