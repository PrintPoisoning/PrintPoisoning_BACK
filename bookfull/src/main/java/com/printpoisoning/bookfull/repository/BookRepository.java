package com.printpoisoning.bookfull.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.printpoisoning.bookfull.entity.Book;
@Repository
public interface BookRepository extends JpaRepository<Book, Long>  {
      Book findByIsbn(int isbn);
  }