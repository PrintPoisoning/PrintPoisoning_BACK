package com.printpoisoning.bookfull.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.printpoisoning.bookfull.dto.response.BookSearchResDTO;
import com.printpoisoning.bookfull.dto.response.BookRegisterResDTO;
import com.printpoisoning.bookfull.dto.request.BookRegisterReqDTO;
import com.printpoisoning.bookfull.service.BookService;
import com.printpoisoning.bookfull.service.TokenService;
import com.printpoisoning.bookfull.entity.Book;
import com.printpoisoning.bookfull.entity.UserBook;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/books")
@Tag(name = "BOOK", description = "책 관련 API")  
public class bookController {
    
    @Autowired  
    private BookService bookService;

    @Autowired  
    private TokenService tokenService; 

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

    @PostMapping("")
    @Operation(summary = "registerBook", description = "책 등록 API")  
    public ResponseEntity<BookRegisterResDTO> registerBook(HttpServletRequest request, @Validated @RequestBody BookRegisterReqDTO bookRegisterReqDTO) {
        
        String email = tokenService.getUserEmailFromAccessToken(request).toString();
        
        Book book = bookService.registerBook(bookRegisterReqDTO);
        UserBook userBook = bookService.registerUserBook(bookRegisterReqDTO, email);
        
        BookRegisterResDTO bookRegisterResDTO = new BookRegisterResDTO();
        bookRegisterResDTO.setSuccess(true);
        bookRegisterResDTO.setMessage("Book registered successfully"); 
        
        BookRegisterResDTO.Result result = new BookRegisterResDTO.Result();  
        result.setUserId(userBook.getUserId());
        result.setBookId(userBook.getBookId());
        result.setReadStatus(userBook.getReadStatus());
        result.setCreatedBy(userBook.getCreatedBy());
        result.setCreatedDate(userBook.getCreatedDate());
        
        bookRegisterResDTO.setResult(result);


        return ResponseEntity.ok(bookRegisterResDTO);
    }

}