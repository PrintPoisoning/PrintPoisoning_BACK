package com.printpoisoning.bookfull.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NicknameCheckResDTO {

    @NotNull 
    private Boolean isAvailable; 

    @NotBlank  
    private String nickname;

    
    // Getters  
    public Boolean getIsAvailable() {  
        return isAvailable;  
    }  

    public String getNickname() {  
        return nickname;  
    }  

     // Setters  
     public void setIsAvailable(Boolean isAvailable) {  
        this.isAvailable = isAvailable;  
    }  

    public void setNickname(String nickname) {  
        this.nickname = nickname;  
    }  
}
