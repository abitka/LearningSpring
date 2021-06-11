package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.example.web.dto.BookIdToRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
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
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        }
        bookService.removeBookId(bookIdToRemove.getId());
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove/all")
    public String removeBooksAll(
            @RequestParam(value = "bookAuthorToRemove") String bookAuthorToRemove,
            @RequestParam(value = "bookTitleToRemove") String bookTitleToRemove,
            @RequestParam(value = "bookSizeToRemove") Integer bookSizeToRemove) {
        logger.info("removeBookOther:\n" +
                "bookAuthorToRemove: " + bookAuthorToRemove +
                " | bookTitleToRemove: " + bookTitleToRemove +
                " | bookSizeToRemove: " + bookSizeToRemove);
        bookService.removeBookItems(bookAuthorToRemove, bookTitleToRemove, bookSizeToRemove);
        return "redirect:/books/shelf";
    }

    @PostMapping(value = "/filter")
    public String filterBooks(
            @RequestParam(value = "bookAuthorToFilter") String bookAuthorToFilter,
            @RequestParam(value = "bookTitleToFilter") String bookTitleToFilter,
            @RequestParam(value = "bookSizeToFilter") Integer bookSizeToFilter, Model model) {
        logger.info("filterBooks:\n " +
                "bookAuthorToFilter: " + bookAuthorToFilter +
                " | bookTitleToFilter: " + bookTitleToFilter +
                " | bookSizeToFilter: " + bookSizeToFilter);
        bookService.filterBooks(bookAuthorToFilter, bookTitleToFilter, bookSizeToFilter);

        List<Book> filterBooks = bookService.filterBooks(bookAuthorToFilter, bookTitleToFilter, bookSizeToFilter);

        model.addAttribute("bookList", filterBooks);
        return "redirect:/books/shelf";
//        return "book_shelf";
    }
}
