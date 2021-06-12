package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.app.repositories.BookRepository;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final Logger logger = Logger.getLogger(BookService.class);
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

    private void defaultInit() {
        logger.info("default INIT in service");
    }

    private void defaultDestroy() {
        logger.info("default DESTROY in service");
    }
}
