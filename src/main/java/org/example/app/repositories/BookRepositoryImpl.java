package org.example.app.repositories;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository<Book>, ApplicationContextAware {

    private final Logger logger = Logger.getLogger(BookRepositoryImpl.class);
    private ApplicationContext context;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> retrieveAll() {
        List<Book> books = jdbcTemplate.query("SELECT * FROM books", (ResultSet rs, int rowNum) -> {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setSize(rs.getInt("size"));
            return book;
        });

        return new ArrayList<>(books);
    }

    @Override
    public void store(Book book) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", book.getAuthor());
        parameterSource.addValue("title", book.getTitle());
        parameterSource.addValue("size", book.getSize());
        jdbcTemplate.update(
                "INSERT INTO books (author, title, size) " +
                        "VALUES(:author, :title, :size)",
                parameterSource);
        logger.info("store new book: " + book);
    }

    @Override
    public void removeItemById(Integer bookIdToRemove) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", bookIdToRemove);
        jdbcTemplate.update(
                "DELETE FROM books WHERE id = :id",
                parameterSource);

        logger.info("remove book completed!");
    }

    @Override
    public void removeItems(String bookAuthorToRemove, String bookTitleToRemove, Integer bookSizeToRemove) {
        for (Book book : retrieveAll()) {
            if (book.getAuthor().equalsIgnoreCase(bookAuthorToRemove)) {
//                repo.remove(book);
            }
            if (book.getTitle().equalsIgnoreCase(bookTitleToRemove)) {
//                repo.remove(book);
            }
            if (bookSizeToRemove == null) continue;
            if (book.getSize().equals(bookSizeToRemove)) {
//                repo.remove(book);
            }
        }
    }

    @Override
    public List<Book> filterBooks(String author, String title, Integer size) {
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    private void defaultInit() {
        logger.info("default INIT in repository");
    }

    private void defaultDestroy() {
        logger.info("default DESTROY in repository");
    }
}
