package com.nurikov.spring.repositories;

import com.nurikov.spring.Entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PeopleRepositories extends JpaRepository<Person,Integer> {
    Optional<Person> findBySurName(String name);
}
