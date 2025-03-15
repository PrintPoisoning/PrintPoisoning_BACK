package com.printpoisoning.bookfull;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;  

@SpringBootApplication  
@OpenAPIDefinition(info = @Info(title = "BookFull API Documentation",  
                                description = "북풀 애플리케이션의 API 문서입니다.",  
                                version = "1.0.0"))  
public class BookfullApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookfullApplication.class, args);
	}

}
