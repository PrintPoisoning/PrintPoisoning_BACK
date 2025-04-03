package com.printpoisoning.bookfull.config;

import io.swagger.v3.oas.models.OpenAPI;  
import io.swagger.v3.oas.models.info.Info;  
import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.Configuration;  
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;  
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;  

@Configuration
public class OpenAPIConfiguration implements WebMvcConfigurer {

    private static final String API_NAME = "Book Full Application";
    private static final String API_VERSION = "1.0.0"; 
    private static final String API_DESCRIPTION = "책풀 애플리케이션입니다.";
    
    public OpenAPIConfiguration() {  
    }  

    @Bean
    public OpenAPI OpenAPIConfig(){
        return new OpenAPI()
                .info(new Info()
                        .title(API_NAME)
                        .description(API_DESCRIPTION)
                        .version(API_VERSION));
    }

    @Override  
    public void addViewControllers(ViewControllerRegistry registry) {  
        registry.addRedirectViewController("/", "/swagger-ui/index.html#/");  
    }  
}