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
    @Past  
    private LocalDate birthdate;  
  
    private String gender;
  
    @NotNull  
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