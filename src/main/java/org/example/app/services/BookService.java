package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.app.repositories.BookRepository;
import org.example.web.dto.Book;
import org.example.web.dto.BookFieldsToRemove;
import org.example.web.dto.BookFilter;
import org.example.web.dto.UploadFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
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

    public void removeBookItems(BookFieldsToRemove book) {
        bookRepo.removeItemsByFields(book);
    }

    public void removeBookId(Integer bookIdToRemove) {
        bookRepo.removeItemById(bookIdToRemove);
    }

    public List<Book> filterBooks(BookFilter book) {
        return bookRepo.filterBooks(book);
    }

    public void upload(MultipartFile file) {
        bookRepo.upload(file);
    }

    public byte[] download(UploadFiles files) {
        return bookRepo.download(files);
    }

    public Path download2(UploadFiles files) {
        return bookRepo.download2(files);
    }
}
