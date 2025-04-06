package com.printpoisoning.bookfull.dto.response;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;  

@Data  
public class BookRegisterResDTO {
    @NotBlank  
    private Boolean success;   

    @NotBlank  
    private String message;  

    private Result result;

    @Data
    public static class Result {
        private String userId;
        private int bookId;
        private String readStatus;
        private String createdBy;
        private LocalDateTime createdDate;
    }
    
    
}

