package com.printpoisoning.bookfull.client;

import com.printpoisoning.bookfull.dto.item.BookItemDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NaverBookApiResponse {

    @JsonProperty("total")
    private int total;
    
    @JsonProperty("start")
    private int start;
    
    @JsonProperty("display")
    private int display;
    
    @JsonProperty("items")
    private List<BookItemDTO> items;
    
}
