package com.nurikov.spring.DAO;

import com.nurikov.spring.Entity.Book;
import com.nurikov.spring.Entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getAllPerson(){
        return jdbcTemplate.query("select * from person", new PersonMapper());
    }

    public Person getPerson(int id){
        return jdbcTemplate.query("select * from person where id=?",new Object[]{id},new PersonMapper())
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("insert into person(surname,year) values (?,?)",person.getSurName(),person.getYear());
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("update person set surname=?,year=? where id=?",person.getSurName(),
                person.getYear(),person.getId());
    }

    public void remove(int id){
        jdbcTemplate.update("delete from person where id=?",id);
    }

    public List<Book> getBookByPersonId(int id) {
        return jdbcTemplate.query("select * from book where person_id=?",new Object[]{id},
                new BookMapper());
    }

    public Optional<Person> getPersonByFullName(String name){
        return jdbcTemplate.query("select * from person where surname=?", new Object[]{name}, new PersonMapper())
                .stream().findAny();
    }
}