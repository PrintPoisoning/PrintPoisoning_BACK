package com.printpoisoning.bookfull.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/test")
@Tag(name = "TEST", description = "테스트용 API")  
public class testController {  
  
    @GetMapping("")  
    @Operation(summary = "test", description = "테스트용 API") 
    public String test() {  
        return "TEST 입니다.";
    }  
}  