package com.printpoisoning.bookfull.dto.request;  
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;  
  
@Data  
public class UserAddReqDTO {  
  
    @NotBlank  
    private String nickname;    

    @NotNull  
    private Boolean isPublic;  

    @NotBlank  
    private String token;

    // Getters  
    public String getNickname() {  
        return nickname;  
    } 

    public Boolean getIsPublic() {  
        return isPublic;  
    }  

    public String getToken(){
        return token;
    }

    // Setters  
    public void setNickname(String nickname) {  
        this.nickname = nickname;  
    }  

    public void setIsPublic(Boolean isPublic) {  
        this.isPublic = isPublic;  
    }  

    public void setToken(String token) {
        this.token = token;
    }

}  