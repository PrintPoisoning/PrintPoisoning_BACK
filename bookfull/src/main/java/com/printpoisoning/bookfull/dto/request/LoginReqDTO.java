package com.printpoisoning.bookfull.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;  

@Data  
public class LoginReqDTO {
    @NotBlank  
    private String accessToken;   
    
    // Getters  
    public String getAccessToken() {  
        return accessToken;  
    } 

    // Setters  
    public void setAccessToken(String accessToken) {  
        this.accessToken = accessToken;  
    } 
}


