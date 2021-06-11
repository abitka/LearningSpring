package org.example.web.dto;

import javax.validation.constraints.NotEmpty;

public class BookIdToRemove {

    @NotEmpty
    private String id;

    public BookIdToRemove() {
    }

    public BookIdToRemove(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
