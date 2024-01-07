package com.nurikov.spring.repositories;

import com.nurikov.spring.Entity.Book;
import com.nurikov.spring.Entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface BooksRepositories extends JpaRepository<Book,Integer> {
    List<Book> findByNameStartingWith(String name);
}
