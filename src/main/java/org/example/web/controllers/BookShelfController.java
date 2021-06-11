package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "books")
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
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(Book book) {
        bookService.saveBook(book);
        logger.info("current repository size: " + bookService.getAllBooks().size());
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove")
    public String removeBook(@RequestParam(value = "bookIdToRemove") Integer bookIdToRemove) {
        bookService.removeBookId(bookIdToRemove);
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

    @GetMapping(value = "/sort/{param_id}")
    public String sortBooks(@PathVariable(name = "param_id") String param) {
        logger.info("sortBooks| param: " + param);
        bookService.sortBooks(param);

        return "redirect:/books/shelf";
    }
}
