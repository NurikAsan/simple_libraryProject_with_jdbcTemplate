package com.nurikov.spring.Entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {
    private int id;
    @NotEmpty(message = "name must be not empty")
    @Size(min = 2, max = 50,message = "author length must be more then 2 and less then 50")
    private String name;
    @NotEmpty(message = "author must be not empty")
    @Size(min = 2, max = 50,message = "author length must be more then 2 and less then 50")
    private String author;
    @Min(value = 1900,message = "year must be more then 1900")
    private int year_of_product;

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

    public int getYear_of_product() {
        return year_of_product;
    }

    public void setYear_of_product(int year_of_product) {
        this.year_of_product = year_of_product;
    }
}
