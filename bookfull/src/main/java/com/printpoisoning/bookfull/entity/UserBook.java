package com.printpoisoning.bookfull.entity;  

import java.time.LocalDateTime; 

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
  
@Entity  
@Table(name = "user_books")  
public class UserBook {  
  
    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;  
  
    @Column(nullable = false, length = 100)  
    private String userId;  
  
    @Column(nullable = false)  
    private int bookId;  
  
    @Column(nullable = false, length = 50)  
    private String readStatus;  
  
    @Column(nullable = false, length = 100)  
    private String createdBy;  
  
    @Column(nullable = false)  
    private LocalDateTime createdDate;  
  
    // Getters and Setters  
    public Long getId() {  
        return id;  
    }  
    
    public void setId(Long id) {  
        this.id = id;  
    }  

    public String getUserId() {  
        return userId;  
    }  
    
    public void setUserId(String userId) {  
        this.userId = userId;  
    }  
    
    public int getBookId() {  
        return bookId;  
    }  
    
    public void setBookId(int bookId) {  
        this.bookId = bookId;  
    }

    public String getReadStatus() {  
        return readStatus;  
    }  
    
    public void setReadStatus(String readStatus) {  
        this.readStatus = readStatus;  
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
    
    @PrePersist  
    protected void onCreate() {  
        LocalDateTime nowKST = LocalDateTime.now();    
        createdDate = nowKST;  
    }  

}  
