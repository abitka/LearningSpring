package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.app.repositories.BookRepository;
import org.example.web.dto.Book;
import org.example.web.dto.BookFieldsToRemove;
import org.example.web.dto.BookFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
        try {
            String name = file.getOriginalFilename();
            byte[] bytes = file.getBytes();

            String rootPath = System.getProperty("catalina.home");
            File dir = new File(rootPath + File.separator + "uploads");
            if (!dir.exists())
                dir.mkdirs();

            File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
            try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
                outputStream.write(bytes);
            }
            logger.info("File saved at: " + serverFile.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Fail Upload file\n" + e);
        }
    }
}
