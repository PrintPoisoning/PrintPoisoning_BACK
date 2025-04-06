package com.printpoisoning.bookfull.service;  

import com.printpoisoning.bookfull.client.NaverBookApiClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.printpoisoning.bookfull.dto.request.BookRegisterReqDTO;
import com.printpoisoning.bookfull.dto.response.BookSearchResDTO;
import com.printpoisoning.bookfull.repository.BookRepository;
import com.printpoisoning.bookfull.repository.UserBookRepository;
import com.printpoisoning.bookfull.entity.Book;
import com.printpoisoning.bookfull.entity.UserBook;
import lombok.RequiredArgsConstructor;


@Service  
@RequiredArgsConstructor
public class BookService {  

    @Autowired  
    private BookRepository bookRepository;  

    @Autowired  
    private UserBookRepository userBookRepository;  

    private final NaverBookApiClient naverBookApiClient;

    public BookSearchResDTO searchBooks(String query, int start, int display, String sort) {
        return naverBookApiClient.searchBooks(query, start, display, sort);
    }

    public Book registerBook(BookRegisterReqDTO bookRegisterReqDTO) {    
        
        Book book = bookRepository.findByIsbn(bookRegisterReqDTO.getIsbn());

        if (book == null){
            Book registerBook = new Book();  
            registerBook.setIsbn(bookRegisterReqDTO.getIsbn());
            registerBook.setTitle(bookRegisterReqDTO.getTitle());
            registerBook.setAuthor(bookRegisterReqDTO.getAuthor());
            registerBook.setPublisher(bookRegisterReqDTO.getPublisher());
            registerBook.setPubdate(bookRegisterReqDTO.getPubdate());
            registerBook.setDescription(bookRegisterReqDTO.getDescription());
            registerBook.setImage(bookRegisterReqDTO.getImage());
            registerBook.setDiscount(bookRegisterReqDTO.getDiscount());
            
            return bookRepository.save(registerBook);
        }
        else {
            return book;
        }

    }

    public UserBook registerUserBook(BookRegisterReqDTO bookRegisterReqDTO, String email) {    
        
        UserBook userBook = userBookRepository.findByUserIdAndBookId(email, bookRegisterReqDTO.getIsbn());

        if (userBook == null){
            UserBook registerUserBook = new UserBook();  
            registerUserBook.setUserId(email);
            registerUserBook.setBookId(bookRegisterReqDTO.getIsbn());

            String readStatus = "NotStarted";
            if (bookRegisterReqDTO.getReadStatus() != null) {
                readStatus = bookRegisterReqDTO.getReadStatus();
            }

            registerUserBook.setReadStatus(readStatus);
            registerUserBook.setCreatedBy(email);

            return userBookRepository.save(registerUserBook);
        }
        else {
            return userBook;
        }

    }

    
}  