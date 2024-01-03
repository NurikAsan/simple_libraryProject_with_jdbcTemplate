package com.nurikov.spring.DAO;

import com.nurikov.spring.Entity.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet resultSet, int i) throws SQLException {
        Person person = new Person();

        person.setId(resultSet.getInt("id"));
        person.setSurName(resultSet.getString("surname"));
        person.setYear(resultSet.getInt("year"));
        return person;
    }
}
