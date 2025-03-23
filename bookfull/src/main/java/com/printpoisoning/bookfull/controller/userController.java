package com.printpoisoning.bookfull.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.printpoisoning.bookfull.dto.request.UserAddReqDTO;
import com.printpoisoning.bookfull.dto.response.UserAddResDTO;
import com.printpoisoning.bookfull.entity.User;
import com.printpoisoning.bookfull.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/users")
@Tag(name = "USER", description = "사용자 관리 API")  
public class userController {
    
    @Autowired  
    private UserService userService; 
  
    @PostMapping("")  
    @Operation(summary = "addUser", description = "회원가입 API")
    public ResponseEntity<UserAddResDTO> addUser(@Validated @RequestBody UserAddReqDTO userAddReqDTO) {  
        User user = userService.createUser(userAddReqDTO);
        
        UserAddResDTO userAddResDTO = new UserAddResDTO();
        userAddResDTO.setUserId(user.getEmail());
        userAddResDTO.setNickname(user.getNickname());
        userAddResDTO.setBirthdate(user.getBirthdate());
        userAddResDTO.setIsPublic(user.getIsPublic());
        userAddResDTO.setGender(user.getGender());

        return new ResponseEntity<>(userAddResDTO, HttpStatus.CREATED);
    } 
  
    @GetMapping("")  
    @Operation(summary = "test", description = "이 API는 회원 가입, 회원 정보 수정, 회원 탈퇴, 내 정보 확인 등의 사용자 관리 기능을 지원합니다.")  
    public String test() {  
        return "TEST 입니다.";
    }  
}  