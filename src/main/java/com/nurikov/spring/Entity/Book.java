package com.nurikov.spring.Entity;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @NotEmpty(message = "name must be not empty")
    @Size(min = 2, max = 50,message = "author length must be more then 2 and less then 50")
    @Column
    private String name;
    @NotEmpty(message = "author must be not empty")
    @Size(min = 2, max = 50,message = "author length must be more then 2 and less then 50")
    @Column
    private String author;
    @Min(value = 1900,message = "year must be more then 1900")
    @Column(name = "year_of_product")
    private int year;
    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "person_id")
    private Person person;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date taken_at;
    @Transient
    private boolean isExpired;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Date getTaken_at() {
        return taken_at;
    }

    public void setTaken_at(Date taken_at) {
        this.taken_at = taken_at;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }
}