package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class BookRepository implements IBookRepository<Book> {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();

    {
        for (int i = 0; i < 5; i++) {
            repo.add(new Book(i, "Author_" + i, "Title_" + i, 100 + i));
            repo.add(new Book(i + 10, "Author_" + i, "Title_" + i, 100 + i));
        }
    }

    @Override
    public List<Book> retrieveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        if (!book.getAuthor().isEmpty() || !book.getTitle().isEmpty() || book.getSize() != null) {
            book.setId(book.hashCode());
            logger.info("store new book: " + book);
            repo.add(book);
        }
    }

    @Override
    public void removeItemById(Integer bookIdToRemove) {
        for (Book book : retrieveAll()) {
            if (book.getId().equals(bookIdToRemove)) {
                logger.info("remove book completed: " + book);
                repo.remove(book);
                break;
            }
        }
    }

    @Override
    public void removeItems(String bookAuthorToRemove, String bookTitleToRemove, Integer bookSizeToRemove) {
        for (Book book : retrieveAll()) {
            if (book.getAuthor().equalsIgnoreCase(bookAuthorToRemove)) {
                repo.remove(book);
            }
            if (book.getTitle().equalsIgnoreCase(bookTitleToRemove)) {
                repo.remove(book);
            }
            if (bookSizeToRemove == null) continue;
            if (book.getSize().equals(bookSizeToRemove)) {
                repo.remove(book);
            }
        }
    }

    @Override
    public List<Book> sortBooks(String param) {
        if (param.equalsIgnoreCase("id"))
            return sortId();
        else if (param.equalsIgnoreCase("author"))
            return sortAuthor();
        else if (param.equalsIgnoreCase("title"))
            return sortTitle();
        else if (param.equalsIgnoreCase("pages"))
            return sortSize();

        return retrieveAll();
    }

    private List<Book> sortId() {
        repo.sort(Comparator.comparing(Book::getId));
        return retrieveAll();
    }

    private List<Book> sortAuthor() {
        repo.sort(Comparator.comparing(Book::getAuthor));
        return retrieveAll();
    }

    private List<Book> sortTitle() {
        repo.sort(Comparator.comparing(Book::getTitle));
        return retrieveAll();
    }

    private List<Book> sortSize() {
        try {
            repo.sort(Comparator.comparing(Book::getSize));
            return retrieveAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
