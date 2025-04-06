package com.printpoisoning.bookfull.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;  

@Data  
public class BookRegisterReqDTO {
    @NotNull  
    private int isbn;   

    @NotBlank  
    private String title;  

    @NotBlank  
    private String author;   

    @NotBlank  
    private String publisher;   

    @NotBlank  
    private String pubdate;   

    @NotBlank  
    private String description;   

    @NotBlank  
    private String image;   

    @NotNull 
    private int discount;   
    
    private String readStatus;   
    
    
}

