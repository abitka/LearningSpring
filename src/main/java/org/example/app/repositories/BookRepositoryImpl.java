package org.example.app.repositories;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class BookRepositoryImpl implements BookRepository<Book> {

    private final Logger logger = Logger.getLogger(BookRepositoryImpl.class);
    private final List<Book> repo = new ArrayList<>();
    private final List<Book> filterBooks = new ArrayList<>();

    {
        for (int i = 0; i < 5; i++) {
            repo.add(new Book(i, "Author_" + i, "Title_" + i, 100 + i));
            repo.add(new Book(i + 10, "Author_" + i, "Title_" + i, 100 + i));
        }
    }

    @Override
    public List<Book> retrieveAll() {
        if (!filterBooks.isEmpty())
            return new ArrayList<>(filterBooks);

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
    public List<Book> filterBooks(String author, String title, Integer size) {
        filterBooks.clear();

        if (author.isEmpty() && title.isEmpty() && size == null)
            return retrieveAll();

        for (Book book : retrieveAll()) {
            if (book.getAuthor().equalsIgnoreCase(author))
                filterBooks.add(book);
            if (book.getTitle().equalsIgnoreCase(title))
                filterBooks.add(book);
            if (size == null) continue;
            if (book.getSize().equals(size))
                filterBooks.add(book);
        }

        return filterBooks;
    }
}
