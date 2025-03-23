package com.printpoisoning.bookfull.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserDeleteResDTO {

    @NotNull 
    private Boolean isSuccess; 

    @NotBlank  
    private String message;

    
    // Getters  
    public Boolean getIsSuccess() {  
        return isSuccess;  
    }  

    public String getMessage() {  
        return message;  
    }  

     // Setters  
     public void setIsSuccess(Boolean isSuccess) {  
        this.isSuccess = isSuccess;  
    }  

    public void setMessage(String message) {  
        this.message = message;  
    }  
}
