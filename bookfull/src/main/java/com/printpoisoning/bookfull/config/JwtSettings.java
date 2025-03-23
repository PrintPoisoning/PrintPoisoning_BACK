package com.printpoisoning.bookfull.config;  
  
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;  
import org.springframework.context.annotation.Primary;  
  
@Configuration  
@ConfigurationProperties(prefix = "jwt")  
@Primary  
public class JwtSettings {  
  
    private String secretKey;  
    private String refreshSecretKey;  
    private long accessTokenExpireMinutes;  
    private long refreshTokenExpireMinutes;  
    private String algorithm;  
  
    // Getters and Setters  
  
    public String getSecretKey() {  
        return secretKey;  
    }  
  
    public void setSecretKey(String secretKey) {  
        this.secretKey = secretKey;  
    }  
  
    public String getRefreshSecretKey() {  
        return refreshSecretKey;  
    }  
  
    public void setRefreshSecretKey(String refreshSecretKey) {  
        this.refreshSecretKey = refreshSecretKey;  
    }  
  
    public long getAccessTokenExpireMinutes() {  
        return accessTokenExpireMinutes;  
    }  
  
    public void setAccessTokenExpireMinutes(long accessTokenExpireMinutes) {  
        this.accessTokenExpireMinutes = accessTokenExpireMinutes;  
    }  
  
    public long getRefreshTokenExpireMinutes() {  
        return refreshTokenExpireMinutes;  
    }  
  
    public void setRefreshTokenExpireMinutes(long refreshTokenExpireMinutes) {  
        this.refreshTokenExpireMinutes = refreshTokenExpireMinutes;  
    }  
  
    public String getAlgorithm() {  
        return algorithm;  
    }  
  
    public void setAlgorithm(String algorithm) {  
        this.algorithm = algorithm;  
    }  
}  