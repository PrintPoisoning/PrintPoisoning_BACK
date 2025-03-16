package com.printpoisoning.bookfull.service;  
  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.printpoisoning.bookfull.dto.request.UserAddReqDTO;
import com.printpoisoning.bookfull.dto.request.UserUpdateReqDTO;
import com.printpoisoning.bookfull.dto.response.LoginResDTO;
import com.printpoisoning.bookfull.entity.User;
import com.printpoisoning.bookfull.repository.UserRepository;
@Service  
public class UserService {  

    @Autowired  
    private UserRepository userRepository;  
  
    public User createUser(UserAddReqDTO userDTO, String email) {  
        
        User user = new User();  
        user.setNickname(userDTO.getNickname());  
        user.setEmail(email);  
        user.setBirthdate(userDTO.getBirthdate());  
        user.setIsPublic(userDTO.getIsPublic());  
        user.setCreatedBy(email);  
  
        // Gender가 null이거나 빈 문자열인 경우 'other'로 설정  
        if (userDTO.getGender() == null || userDTO.getGender().trim().isEmpty()) {  
            user.setGender("other");  
        } else {  
            user.setGender(userDTO.getGender());  
        }  
  
        // User Entity의 @PrePersist 메서드에 의해 생성일이 현재 시간으로 자동 설정됩니다.  
        return userRepository.save(user);  
    }

    public User updateUser(UserUpdateReqDTO userUpdateReqDTO, String email) {  
        User user = userRepository.findByEmail(email);  
        if (user != null) {  
            if (userUpdateReqDTO.getNickname() != null) {  
                user.setNickname(userUpdateReqDTO.getNickname());  
            }  
            if (userUpdateReqDTO.getBirthdate() != null) {  
                user.setBirthdate(userUpdateReqDTO.getBirthdate());  
            }  
            if (userUpdateReqDTO.getIsPublic() != null) {  
                user.setIsPublic(userUpdateReqDTO.getIsPublic());  
            }  
            if (userUpdateReqDTO.getGender() != null) {  
                user.setGender(userUpdateReqDTO.getGender());  
            }  
            return userRepository.save(user);  
        } else {  
            // 예외 처리 또는 오류 응답 리턴  
            throw new RuntimeException("User not found with email: " + email);  
        } 
    } 

    public User updateUser(LoginResDTO loginResDTO, String email) {  
        User user = userRepository.findByEmail(email);  
        if (user != null) {  
            if (loginResDTO.getAccessToken() != null) {  
                user.setAccessToken(loginResDTO.getAccessToken());  
            }  
            if (loginResDTO.getRefreshToken() != null) {  
                user.setRefreshToken(loginResDTO.getRefreshToken());  
            }  
            return userRepository.save(user);  
        } else {  
            // 예외 처리 또는 오류 응답 리턴  
            throw new RuntimeException("User not found with email: " + email);  
        } 
    } 

    public User getUserByEmail(String email) {  
        return userRepository.findByEmail(email);  
    } 

    public void deleteUser(User user) {  
        userRepository.delete(user);  
    }  
}  