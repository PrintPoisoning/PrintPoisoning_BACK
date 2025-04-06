package com.printpoisoning.bookfull.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.printpoisoning.bookfull.entity.UserBook;

@Repository
public interface UserBookRepository extends JpaRepository<UserBook, Long>  {
    // 다중 필터링  
    UserBook findByUserIdAndBookId(String userId, int isbn);  
  }