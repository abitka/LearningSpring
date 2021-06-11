package org.example.app.repositories;

import java.util.List;

public interface BookRepository<T> {

    List<T> retrieveAll();

    void store(T object);

    void removeItemById(String idToRemove);
    void removeItems(String bookAuthorToRemove, String bookTitleToRemove, Integer bookSizeToRemove);

    List<T> filterBooks(String author, String title, Integer size);
}
