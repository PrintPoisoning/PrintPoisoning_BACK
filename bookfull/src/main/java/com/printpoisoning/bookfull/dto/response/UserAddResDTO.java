package com.printpoisoning.bookfull.dto.response;  
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data; 
  
@Data  
public class UserAddResDTO {  
  
    @NotBlank  
    private String userId; 

    @NotBlank  
    private String nickname;    

    @NotNull  
    private Boolean isPublic;  

    // Getters  
    public String getUserId() {  
        return userId;  
    }  

    public String getNickname() {  
        return nickname;  
    }  
  
    public Boolean getIsPublic() {  
        return isPublic;  
    }  
  
    // Setters  
    public void setUserId(String userId) {  
        this.userId = userId;  
    }  

    public void setNickname(String nickname) {  
        this.nickname = nickname;  
    }  
  
    public void setIsPublic(Boolean isPublic) {  
        this.isPublic = isPublic;  
    }  

}  