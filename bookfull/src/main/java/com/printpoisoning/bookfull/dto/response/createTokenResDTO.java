package com.printpoisoning.bookfull.dto.response;
import java.time.ZonedDateTime; 

public class createTokenResDTO {
    private String token;  
    private ZonedDateTime expiresAt;  
  
    public createTokenResDTO(String token, ZonedDateTime expiresAt) {  
        this.token = token;  
        this.expiresAt = expiresAt;  
    }  
  
    public String getToken() {  
        return token;  
    }  
  
    public void setToken(String token) {  
        this.token = token;  
    }  
  
    public ZonedDateTime getExpiresAt() {  
        return expiresAt;  
    }  
  
    public void setExpiresAt(ZonedDateTime expiresAt) {  
        this.expiresAt = expiresAt;  
    }  
}
