package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Controller
@RequestMapping(value = "/books")
@Scope("singleton")
public class BookShelfController {

    private final Logger logger = Logger.getLogger(BookShelfController.class);
    private final BookService bookService;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info("GET /shelf returns book_shelf.html");
        model.addAttribute("book", new Book());
        model.addAttribute("bookIdToRemove", new BookIdToRemove());
        model.addAttribute("bookFieldsToRemove", new BookFieldsToRemove());
        model.addAttribute("bookFilter", new BookFilter());
        model.addAttribute("uploadFiles", new UploadFiles());
        model.addAttribute("uploadList", bookService.getAllFiles());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", book);
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        }
        bookService.saveBook(book);
        logger.info("current repository size: " + bookService.getAllBooks().size());
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove")
    public String removeBook(@Valid BookIdToRemove bookIdToRemove, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", new Book());
            model.addAttribute("bookFieldsToRemove", new BookFieldsToRemove());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        }
        bookService.removeBookId(bookIdToRemove.getId());
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove/all")
    public String removeBooksAll(@Valid BookFieldsToRemove book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", new Book());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookFilter", new BookFilter());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        }
        logger.info("removeBooksAll:\n" +
                book.getAuthor() + " | " +
                book.getTitle() + " | " +
                book.getSize());
        bookService.removeBookItems(book);
        return "redirect:/books/shelf";
    }

    @PostMapping(value = "/filter")
    public String filterBooks(@Valid BookFilter bookFilter, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", new Book());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookFieldsToRemove", new BookFieldsToRemove());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        }
        List<Book> books = bookService.filterBooks(bookFilter);
        model.addAttribute("book", new Book());
        model.addAttribute("bookIdToRemove", new BookIdToRemove());
        model.addAttribute("bookFieldsToRemove", new BookFieldsToRemove());
        model.addAttribute("bookList", books);
        return "book_shelf";

//        return "redirect:/books/shelf";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        bookService.upload(file);

        return "redirect:/books/shelf";
    }

    @GetMapping("/download")
    @ResponseBody
    public void downloadFile(@RequestParam("filename") String files, HttpServletResponse response) {
        Path filepath = bookService.download(files);
        logger.info("download file: " + filepath);
        response.setHeader("Content-disposition", "attachment;filename=" + files);
        response.setContentType("application/ostet-stream");
        try {
            Files.copy(filepath, response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException ioException) {
            logger.error("Download file failed!" + ioException);
        }
    }
}
