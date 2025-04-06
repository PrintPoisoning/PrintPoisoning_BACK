package com.printpoisoning.bookfull.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.printpoisoning.bookfull.dto.response.BookSearchResDTO;
import com.printpoisoning.bookfull.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/books")
@Tag(name = "BOOK", description = "책 관련 API")  
public class bookController {
    
    @Autowired  
    private BookService bookService;

    @GetMapping("/search")
    @Operation(summary = "searchBooks", description = "책 검색 API")  
    public ResponseEntity<BookSearchResDTO> searchBooks(
            @RequestParam(name = "query") String query,
            @RequestParam(name = "start", defaultValue = "1") int start,
            @RequestParam(name = "display", defaultValue = "10") int display,
            @RequestParam(name = "sort", defaultValue = "sim") String sort) {
        
        BookSearchResDTO response = bookService.searchBooks(query, start, display, sort);
        return ResponseEntity.ok(response);
    }

}