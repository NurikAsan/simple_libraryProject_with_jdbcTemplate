package com.nurikov.spring.Entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Person {
    private int id;
    @NotEmpty(message = "surname must be not empty")
    @Size(min = 2,max = 50,message = "surname length must be more then 2 and less then 50")
    private String surName;
    @Min(value = 1900,message = "year must be more then 1900")
    private int year;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
