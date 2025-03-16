package com.printpoisoning.bookfull.entity;  
  
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;  
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;  
  
@Entity  
@Table(name = "users")  
public class User {  
  
    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;  
  
    @Column(nullable = false, length = 50)  
    private String nickname;  
  
    @Column(nullable = false, length = 100)  
    private String email;  
  
    @Column(nullable = false)  
    private LocalDate birthdate;  
  
    @Column(nullable = false, length = 6)  
    private String gender;  
  
    @Column(nullable = false)  
    private Boolean isPublic;  
  
    @Column(nullable = false, length = 100)  
    private String createdBy;  
  
    @Column(nullable = false)  
    private LocalDateTime createdDate;  
  
    @Column(nullable = false)  
    private LocalDateTime updatedDate;  
  
    @Column(length = 512, nullable = true)  
    private String accessToken;  
  
    @Column(length = 512, nullable = true)  
    private String refreshToken; 
  
    public Long getId() {  
        return id;  
    }  
  
    public void setId(Long id) {  
        this.id = id;  
    }  
  
    public String getNickname() {  
        return nickname;  
    }  
  
    public void setNickname(String nickname) {  
        this.nickname = nickname;  
    }  
  
    public String getEmail() {  
        return email;  
    }  
  
    public void setEmail(String email) {  
        this.email = email;  
    }  
  
    public LocalDate getBirthdate() {  
        return birthdate;  
    }  
  
    public void setBirthdate(LocalDate birthdate) {  
        this.birthdate = birthdate;  
    }  
  
    public String getGender() {  
        return gender;  
    }  
  
    public void setGender(String gender) {  
        this.gender = gender;  
    }  
  
    public Boolean getIsPublic() {  
        return isPublic;  
    }  
  
    public void setIsPublic(Boolean isPublic) {  
        this.isPublic = isPublic;  
    }  
  
    public String getCreatedBy() {  
        return createdBy;  
    }  
  
    public void setCreatedBy(String createdBy) {  
        this.createdBy = createdBy;  
    }  
  
    public LocalDateTime getCreatedDate() {  
        return createdDate;  
    }  
  
    public void setCreatedDate(LocalDateTime createdDate) {  
        this.createdDate = createdDate;  
    }  
  
    public LocalDateTime getUpdatedDate() {  
        return updatedDate;  
    }  
  
    public void setUpdatedDate(LocalDateTime updatedDate) {  
        this.updatedDate = updatedDate;  
    }  
  
    public String getAccessToken() {  
        return accessToken;  
    }  
  
    public void setAccessToken(String accessToken) {  
        this.accessToken = accessToken;  
    }  
  
    public String getRefreshToken() {  
        return refreshToken;  
    }  
  
    public void setRefreshToken(String refreshToken) {  
        this.refreshToken = refreshToken;  
    }  
  
    @PrePersist  
    protected void onCreate() {  
        LocalDateTime nowKST = LocalDateTime.now();  
        createdDate = nowKST;  
        updatedDate = nowKST;  
    }  
  
    @PreUpdate  
    protected void onUpdate() {  
        updatedDate = LocalDateTime.now();  
    }  
}  