package com.printpoisoning.bookfull.dto.request;  
import java.time.LocalDate;

import jakarta.validation.constraints.Past;
import lombok.Data;  
  
@Data  
public class UserUpdateReqDTO {  
  
    
    private String nickname;    

    @Past  
    private LocalDate birthdate;  
  
    private String gender;

    private Boolean isPublic;  

    // Getters  
    public String getNickname() {  
        return nickname;  
    }  
  
    public LocalDate getBirthdate() {  
        return birthdate;  
    }  
  
    public Boolean getIsPublic() {  
        return isPublic;  
    }  
  
    public String getGender() {  
        return gender;  
    }  
  
    // Setters  
    public void setNickname(String nickname) {  
        this.nickname = nickname;  
    }  
  
    public void setBirthdate(LocalDate birthdate) {  
        this.birthdate = birthdate;  
    }  
  
    public void setIsPublic(Boolean isPublic) {  
        this.isPublic = isPublic;  
    }  

    public void setGender(String gender) {  
        this.gender = gender;  
    }  
}  