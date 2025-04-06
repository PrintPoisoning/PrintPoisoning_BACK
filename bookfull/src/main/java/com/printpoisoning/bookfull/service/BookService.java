package com.printpoisoning.bookfull.service;  

import com.printpoisoning.bookfull.client.NaverBookApiClient;
import org.springframework.stereotype.Service;

import com.printpoisoning.bookfull.dto.response.BookSearchResDTO;

import lombok.RequiredArgsConstructor;


@Service  
@RequiredArgsConstructor
public class BookService {  

    private final NaverBookApiClient naverBookApiClient;

    public BookSearchResDTO searchBooks(String query, int start, int display, String sort) {
        return naverBookApiClient.searchBooks(query, start, display, sort);
    }

    
}  