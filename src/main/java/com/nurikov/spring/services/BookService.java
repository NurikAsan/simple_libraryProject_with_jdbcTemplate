package com.nurikov.spring.services;

import com.nurikov.spring.Entity.Book;
import com.nurikov.spring.Entity.Person;
import com.nurikov.spring.repositories.BooksRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;
@Service
@Transactional(readOnly = true)
public class BookService {
    private final BooksRepositories booksRepositories;

    @Autowired
    public BookService(BooksRepositories booksRepositories) {
        this.booksRepositories = booksRepositories;
    }

    public List<Book> getAllBooks(boolean sort){
        if(sort)
            return booksRepositories.findAll(Sort.by("year"));
        else
            return booksRepositories.findAll();
    }

    public Book getOneBook(int id){
        return booksRepositories.findById(id).orElse(null);
    }

    public Person getBookOwner(int id) {
        return booksRepositories.findById(id).map(Book::getPerson).orElse(null);
    }

    public List<Book> withPagination(Integer page, Integer booksPerPage,
                                     boolean sort){
        if(sort) {
            return booksRepositories.findAll(PageRequest.of(page, booksPerPage,
                    Sort.by("year"))).getContent();
        }
        else{
            return booksRepositories.findAll(PageRequest.of(page, booksPerPage)).getContent();
        }
    }

    @Transactional
    public void save(Book book){
        booksRepositories.save(book);
    }

    @Transactional
    public void update(int id, Book book) {
        Book bookToBeUpdated = booksRepositories.findById(id).get();

        book.setId(id);
        book.setPerson(bookToBeUpdated.getPerson());
        booksRepositories.save(book);
    }

    @Transactional
    public void remove(int id) {
        booksRepositories.deleteById(id);
    }

    @Transactional
    public void assign(int id, Person person) {
        booksRepositories.findById(id).ifPresent(
                book -> {
                book.setPerson(person);
                book.setTaken_at(new Date());
                }
        );
    }
    @Transactional
    public void release(int id) {
        booksRepositories.findById(id).ifPresent(
                book -> {
                    book.setPerson(null);
                    book.setTaken_at(null);
                    book.setExpired(false);
                }
        );
    }

    public List<Book> getByName(String name){
        return booksRepositories.findByNameStartingWith(name);
    }
}