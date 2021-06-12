package org.example.web.dto;

import javax.validation.constraints.NotNull;

public class BookIdToRemove {

    @NotNull
    private Integer id;

    public BookIdToRemove() {
    }

    public BookIdToRemove(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
