package org.example.web.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

public class Book {
    private Integer id;
    @Size(min = 1, max = 250)
    private String author;
    @Size(min = 1, max = 250)
    private String title;

    @Digits(integer = 5, fraction = 0)
    private Integer size;

    public Book() {
    }

    public Book(Integer id, String author, String title, Integer size) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.size = size;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
