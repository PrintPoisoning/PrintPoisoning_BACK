package com.printpoisoning.bookfull.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.printpoisoning.bookfull.dto.request.SignupReqDTO;
import com.printpoisoning.bookfull.dto.request.UserUpdateReqDTO;
import com.printpoisoning.bookfull.dto.response.KakaoUserResDTO;
import com.printpoisoning.bookfull.dto.response.SignupResDTO;
import com.printpoisoning.bookfull.dto.response.UserDeleteResDTO;
import com.printpoisoning.bookfull.dto.response.NicknameCheckResDTO;
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
    private TokenService tokenService; 

    @Autowired  
    private UserService userService; 
  

    @PutMapping("")  
    @Operation(summary = "updateUser", description = "회원 수정 API")  
    public ResponseEntity<SignupResDTO> updateUser(HttpServletRequest request, @Validated @RequestBody UserUpdateReqDTO userUpdateReqDTO) {  
        
        String email = tokenService.getUserEmailFromAccessToken(request);

        User updatedUser = userService.updateUser(userUpdateReqDTO, email);  
    
        SignupResDTO userAddResDTO = new SignupResDTO();  
        userAddResDTO.setUserId(updatedUser.getEmail());  
        userAddResDTO.setNickname(updatedUser.getNickname());  
        userAddResDTO.setIsPublic(updatedUser.getIsPublic());  
        return new ResponseEntity<>(userAddResDTO, HttpStatus.OK);  
    }  

    // @RequestHeader("Authorization") String token
    @DeleteMapping("")  
    @Operation(summary = "deleteUser", description = "회원 탈퇴 API")
    public ResponseEntity<UserDeleteResDTO> deleteUser(HttpServletRequest request) {  
        
        String email = tokenService.getUserEmailFromAccessToken(request);

        // 이메일로 사용자를 조회  
        User user = userService.getUserByEmail(email); 
        
        if (user == null) {
            UserDeleteResDTO userDeleteResDTO = new UserDeleteResDTO();
            userDeleteResDTO.setIsSuccess(false);
            userDeleteResDTO.setMessage("해당 사용자가 존재하지 않습니다.");
            return new ResponseEntity<>(userDeleteResDTO, HttpStatus.NOT_FOUND); 
        }
        // 사용자 삭제  
        userService.deleteUser(user);  

        UserDeleteResDTO userDeleteResDTO = new UserDeleteResDTO();
        userDeleteResDTO.setIsSuccess(true);
        userDeleteResDTO.setMessage("회원 삭제가 성공적으로 처리되었습니다.");
  
        return new ResponseEntity<>(userDeleteResDTO, HttpStatus.OK);
    } 
  
    @GetMapping("")  
    @Operation(summary = "getMe", description = "내 정보 확인 API")  
    public ResponseEntity<SignupResDTO> getMe(HttpServletRequest request) {
        
        String email = tokenService.getUserEmailFromAccessToken(request);

        // 이메일로 사용자를 조회  
        User user = userService.getUserByEmail(email);

        SignupResDTO userAddResDTO = new SignupResDTO();
        userAddResDTO.setUserId(user.getEmail());
        userAddResDTO.setNickname(user.getNickname());
        userAddResDTO.setIsPublic(user.getIsPublic());
        return new ResponseEntity<>(userAddResDTO, HttpStatus.OK);  
    } 
    
    @GetMapping("/check-nickname")  
    @Operation(summary = "checkNickname", description = "닉네임 중복 확인 API")  
    public ResponseEntity<NicknameCheckResDTO> checkNickname(HttpServletRequest request) {
        
        String nickname = request.getParameter("nickname");
        
        User userNickname = userService.getUserByNickname(nickname); 
        if (userNickname == null) {
            NicknameCheckResDTO nicknameCheckResDTO = new NicknameCheckResDTO();
            nicknameCheckResDTO.setNickname(nickname);
            nicknameCheckResDTO.setIsAvailable(true);
            return new ResponseEntity<>(nicknameCheckResDTO, HttpStatus.OK);
        }
        else {
            NicknameCheckResDTO nicknameCheckResDTO = new NicknameCheckResDTO();
            nicknameCheckResDTO.setNickname(nickname);
            nicknameCheckResDTO.setIsAvailable(false);
            return new ResponseEntity<>(nicknameCheckResDTO, HttpStatus.OK);
        }
    }  
}