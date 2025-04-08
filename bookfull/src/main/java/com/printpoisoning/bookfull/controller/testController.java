package com.printpoisoning.bookfull.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.printpoisoning.bookfull.dto.response.ErrorCodeResDTO;

@RestController
@RequestMapping("/test")
@Tag(name = "TEST", description = "테스트용 API")  
public class testController {  
  
    @GetMapping("")  
    @Operation(summary = "test", description = "테스트용 API") 
    public ResponseEntity<ErrorCodeResDTO> test() {  
        ErrorCodeResDTO errorCodeResDTO = new ErrorCodeResDTO();
        errorCodeResDTO.setErrorCode("1002230");
        return new ResponseEntity<>(errorCodeResDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }  
}  