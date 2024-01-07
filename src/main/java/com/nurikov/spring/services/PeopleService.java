package com.nurikov.spring.services;

import com.nurikov.spring.Entity.Book;
import com.nurikov.spring.Entity.Person;
import com.nurikov.spring.repositories.PeopleRepositories;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepositories peopleRepositories;

    @Autowired
    public PeopleService(PeopleRepositories peopleRepositories) {
        this.peopleRepositories = peopleRepositories;
    }

    public List<Person> getAllPerson() {
        return peopleRepositories.findAll();
    }


    public Person getPerson(int id) {
        return peopleRepositories.findById(id).orElse(null);
    }

    public List<Book> getBookByPersonId(int id) {
        Optional<Person> person = peopleRepositories.findById(id);
        if(person.isPresent()){
            Hibernate.initialize(person.get().getBooks());
            person.get().getBooks().forEach(
                    book -> {
                        long diff = Math.abs(book.getTaken_at().getTime() - new Date().getTime());
                        if(diff > 864000000)
                            book.setExpired(true);
                    }
            );
            return person.get().getBooks();
        }
        else
            return Collections.emptyList();
    }
    @Transactional
    public void save(Person person) {
        peopleRepositories.save(person);
    }

    @Transactional
    public void update(int id, Person person) {
        person.setId(id);
        peopleRepositories.save(person);
    }

    @Transactional
    public void remove(int id) {
        peopleRepositories.deleteById(id);
    }

    public Optional<Person> getPersonByFullName(String name){
        return peopleRepositories.findBySurName(name);
    }
}