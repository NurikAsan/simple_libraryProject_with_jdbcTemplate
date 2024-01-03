package com.nurikov.spring.DAO;

import com.nurikov.spring.Entity.Book;
import com.nurikov.spring.Entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAllBooks(){
        return jdbcTemplate.query("select * from book",new BookMapper());
    }

    public Book getOneBook(int id){
        return jdbcTemplate.query("select * from book where id=?",new Object[]{id},new BookMapper()).stream()
                .findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("insert into book(name,author,year_of_product) values(?,?,?" +
                        ")",book.getName(),
                book.getAuthor(),book.getYear_of_product());
    }

    public void update(int id, Book book){
        jdbcTemplate.update("update book set name=?,author=?,year_of_product=? where id=?",
                book.getName(),book.getAuthor(),book.getYear_of_product(),id);
    }

    public void remove(int id){
        jdbcTemplate.update("delete from book where id=?",id);
    }

    public Optional<Person> getBookOwner(int id){
        return jdbcTemplate.query("select p.* from book b join person p on b.person_id=p.id where b.id=?",
                        new Object[]{id},new PersonMapper()).stream().findAny();
    }

    public void assign(int id, Person person) {
        jdbcTemplate.update("update book set person_id=? where id=?",person.getId(),id);
    }

    public void release(int id) {
        jdbcTemplate.update("update book set person_id=NULL where id=?",id);
    }
}