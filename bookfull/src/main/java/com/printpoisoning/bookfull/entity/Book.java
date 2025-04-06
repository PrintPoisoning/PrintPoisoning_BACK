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
@Table(name = "books")  
public class Book {  
  
    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;  
  
    @Column(nullable = false)  
    private int isbn;  
  
    @Column(nullable = false, length = 255)  
    private String title;  

    @Column(nullable = false, length = 255)  
    private String author;  
  
    @Column(nullable = false, length = 255)  
    private String publisher;  

    @Column(nullable = false)  
    private String pubdate;  
    
    @Column(nullable = false, length = 4000)  
    private String description;  
    
    @Column(nullable = false, length = 4000)  
    private String image; 

    @Column(nullable = false)  
    private int discount; 
  
    @Column(nullable = false)  
    private LocalDateTime createdDate;  
  
    // Getters and Setters  
    public Long getId() {  
        return id;  
    }  
    
    public void setId(Long id) {  
        this.id = id;  
    }  

    public int getIsbn() {  
        return isbn;  
    }  
    
    public void setIsbn(int isbn) {  
        this.isbn = isbn;  
    }  

    public String getTitle() {  
        return title;  
    }  
    
    public void setTitle(String title) {  
        this.title = title;  
    }  

    public String getAuthor() {  
        return author;  
    }  
    
    public void setAuthor(String author) {  
        this.author = author;  
    }  

    public String getPublisher() {  
        return publisher;  
    }  
    
    public void setPublisher(String publisher) {  
        this.publisher = publisher;  
    }  

    public String getPubdate() {  
        return pubdate;  
    }  
    
    public void setPubdate(String pubdate) {  
        this.pubdate = pubdate;  
    }  
    
    public String getImage() {  
        return image;  
    }  
    
    public void setImage(String image) {  
        this.image = image;  
    }  

    public int getDiscount() {  
        return discount;  
    }  
    
    public void setDiscount(int discount) {  
        this.discount = discount;  
    }  

    public String getDescription() {  
        return description;  
    }  
    
    public void setDescription(String description) {  
        this.description = description;  
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
