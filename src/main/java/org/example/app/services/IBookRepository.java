package org.example.app.services;

import java.util.List;

public interface IBookRepository<T> {

    List<T> retrieveAll();

    void store(T object);

    void removeItemById(Integer idToRemove);
    void removeItems(String bookAuthorToRemove, String bookTitleToRemove, Integer bookSizeToRemove);

    List<T> sortBooks(String param);
}
