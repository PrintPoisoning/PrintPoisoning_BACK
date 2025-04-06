package com.printpoisoning.bookfull.dto.item;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookItemDTO {
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("link")
    private String link;
    
    @JsonProperty("image")
    private String image;
    
    @JsonProperty("author")
    private String author;
    
    @JsonProperty("price")
    private int price;
    
    @JsonProperty("discount")
    private int discount;
    
    @JsonProperty("publisher")
    private String publisher;
    
    @JsonProperty("pubdate")
    private String pubdate;
    
    @JsonProperty("isbn")
    private String isbn;
    
    @JsonProperty("description")
    private String description;
}