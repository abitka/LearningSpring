package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.example.web.dto.BookFieldsToRemove;
import org.example.web.dto.BookFilter;
import org.example.web.dto.BookIdToRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
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
//        model.addAttribute("bookFilter", new BookFilter());
        model.addAttribute("bookList", books);
        return "book_shelf";

//        return "redirect:/books/shelf";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        bookService.upload(file);

        return "redirect:/books/shelf";
    }
}
