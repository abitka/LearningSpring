package org.example.app.repositories;

import org.example.web.dto.BookFieldsToRemove;
import org.example.web.dto.BookFilter;

import java.util.List;

public interface BookRepository<T> {

    List<T> retrieveAll();

    void store(T object);

    void removeItemById(Integer idToRemove);
    void removeItemsByFields(BookFieldsToRemove book);

    List<T> filterBooks(BookFilter book);
}
