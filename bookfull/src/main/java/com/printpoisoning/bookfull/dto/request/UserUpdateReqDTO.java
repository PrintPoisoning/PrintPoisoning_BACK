package com.printpoisoning.bookfull.dto.request;  
import java.time.LocalDate;

import jakarta.validation.constraints.Past;
import lombok.Data;  
  
@Data  
public class UserUpdateReqDTO {  
    
    private String nickname;    

    private Boolean isPublic;  

    // Getters  
    public String getNickname() {  
        return nickname;  
    }  

    public Boolean getIsPublic() {  
        return isPublic;  
    }  

    // Setters  
    public void setNickname(String nickname) {  
        this.nickname = nickname;  
    }  

    public void setIsPublic(Boolean isPublic) {  
        this.isPublic = isPublic;  
    }  

}  