package org.example.app.services;

import org.example.app.repositories.BookRepository;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository<Book> bookRepo;

    @Autowired
    public BookService(BookRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.retrieveAll();
    }

    public void saveBook(Book book) {
        bookRepo.store(book);
    }

    public void removeBookItems(String bookAuthorToRemove, String bookTitleToRemove, Integer bookSizeToRemove) {
        bookRepo.removeItems(bookAuthorToRemove, bookTitleToRemove, bookSizeToRemove);
    }

    public void removeBookId(Integer bookIdToRemove) {
        bookRepo.removeItemById(bookIdToRemove);
    }

    public List<Book> filterBooks(String author, String title, Integer size) {
        return bookRepo.filterBooks(author, title, size);
    }
}
