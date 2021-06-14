package org.example.web.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BookFieldsToRemove {
    @Size(max = 250)
    private String author;

    @Size(max = 250)
    private String title;

    @NotNull
    @Digits(integer = 5, fraction = 0)
    private Integer size;

    public BookFieldsToRemove() {
    }

    public BookFieldsToRemove(String author, String title, Integer size) {
        this.author = author;
        this.title = title;
        this.size = size;
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
