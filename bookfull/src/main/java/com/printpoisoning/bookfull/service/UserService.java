package com.printpoisoning.bookfull.service;  
  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.printpoisoning.bookfull.dto.request.UserAddReqDTO;
import com.printpoisoning.bookfull.entity.User;
import com.printpoisoning.bookfull.repository.UserRepository;
@Service  
public class UserService {  

    @Autowired  
    private UserRepository userRepository;  
  
    public User createUser(UserAddReqDTO userDTO) {  
        String mail = "jhseo@gmail.com";
        User user = new User();  
        user.setNickname(userDTO.getNickname());  
        user.setEmail(mail);  
        user.setBirthdate(userDTO.getBirthdate());  
        user.setIsPublic(userDTO.getIsPublic());  
        user.setCreatedBy(mail);  
  
        // Gender가 null이거나 빈 문자열인 경우 'other'로 설정  
        if (userDTO.getGender() == null || userDTO.getGender().trim().isEmpty()) {  
            user.setGender("other");  
        } else {  
            user.setGender(userDTO.getGender());  
        }  
  
        // User Entity의 @PrePersist 메서드에 의해 생성일이 현재 시간으로 자동 설정됩니다.  
        return userRepository.save(user);  
    }  
}  