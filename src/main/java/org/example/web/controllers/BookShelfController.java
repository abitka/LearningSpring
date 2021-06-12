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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        String name = file.getOriginalFilename();
        byte[] bytes = file.getBytes();

        if (bytes.length <= 0) //TODO remake this if
            return "redirect:/books/shelf";

        String rootPath = System.getProperty("catalina.home");
        File dir = new File(rootPath + File.separator + "uploads");
        if (!dir.exists())
            dir.mkdirs();

        File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
            outputStream.write(bytes);
        }
        logger.info("File saved at: " + serverFile.getAbsolutePath());

        return "redirect:/books/shelf";
    }
}
