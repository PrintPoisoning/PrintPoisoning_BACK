package com.printpoisoning.bookfull.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.printpoisoning.bookfull.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>  {  }