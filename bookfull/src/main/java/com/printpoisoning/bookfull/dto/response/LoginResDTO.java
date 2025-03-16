package com.printpoisoning.bookfull.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Data;  

@Data  
public class LoginResDTO {
    @NotNull  
    private Boolean isMember;   
    private String accessToken;   
    private String refreshToken;  
    
    // Getters  
    public String getAccessToken() {  
        return accessToken;  
    } 

    // Getters  
    public String getRefreshToken() {  
        return refreshToken;  
    } 

    // Getters  
    public Boolean getIsMember() {  
        return isMember;  
    } 

    // Setters  
    public void setAccessToken(String accessToken) {  
        this.accessToken = accessToken;  
    } 

    // Setters  
    public void setRefreshToken(String refreshToken) {  
        this.refreshToken = refreshToken;  
    } 

    // Setters  
    public void setIsMember(Boolean isMember) {  
        this.isMember = isMember;  
    } 
}
