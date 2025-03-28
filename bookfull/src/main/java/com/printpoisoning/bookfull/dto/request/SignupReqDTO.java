package com.printpoisoning.bookfull.dto.request;  

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;  
  
@Data  
public class SignupReqDTO {  
  
    @NotBlank  
    private String nickname;    

    @NotNull  
    private Boolean isPublic;  

    @NotBlank  
    private String kakaoToken;

    // Getters  
    public String getNickname() {  
        return nickname;  
    } 

    public Boolean getIsPublic() {  
        return isPublic;  
    }  

    public String getKakaoToken(){
        return kakaoToken;
    }

    // Setters  
    public void setNickname(String nickname) {  
        this.nickname = nickname;  
    }  

    public void setIsPublic(Boolean isPublic) {  
        this.isPublic = isPublic;  
    }  

    public void setKakaoToken(String kakaoToken) {
        this.kakaoToken = kakaoToken;
    }

}  