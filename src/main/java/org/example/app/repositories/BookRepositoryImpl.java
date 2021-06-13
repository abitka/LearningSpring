package org.example.app.repositories;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.example.web.dto.BookFieldsToRemove;
import org.example.web.dto.BookFilter;
import org.example.web.dto.UploadFiles;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository<Book>, ApplicationContextAware {

    private final Logger logger = Logger.getLogger(BookRepositoryImpl.class);
    private ApplicationContext context;
    private final List<UploadFiles> uploadFiles = new ArrayList<>();

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
    public void removeItemsByFields(BookFieldsToRemove book) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", book.getAuthor());
        parameterSource.addValue("title", book.getTitle());
        parameterSource.addValue("size", book.getSize());
        jdbcTemplate.update(
                "DELETE FROM books WHERE author = :author OR title = :title OR size = :size",
                parameterSource);
    }

    @Override
    public List<Book> filterBooks(BookFilter bookFilter) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", bookFilter.getAuthor());
        parameterSource.addValue("title", bookFilter.getTitle());
        parameterSource.addValue("size", bookFilter.getSize());

        List<Book> books = jdbcTemplate.query(
                "SELECT * FROM books WHERE author = :author OR title = :title OR size = :size",
                parameterSource,
                (ResultSet rs, int rowNum) -> {
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
    public List<Book> showAllFiles() {
        return null;
    }

    @Override
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

            uploadFiles.add(new UploadFiles(serverFile.getAbsolutePath()));
            logger.info("upload: " + uploadFiles.size());
            logger.info("upload: " + uploadFiles.get(0).getFilename());
        } catch (IOException e) {
            logger.error("Fail Upload file\n" + e);
        }
    }

    @Override
    public byte[] download(UploadFiles files) {
        try {
            logger.info("download: file name->" + files.getFilename());
            for (UploadFiles file : uploadFiles) {
                logger.info(file.getFilename());
            }
            int index = uploadFiles.indexOf(files);
            logger.info("download: index->" + index);
            Path path = Paths.get(uploadFiles.get(0).getFilename());
            logger.info("download: path->" + path);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            logger.error("Fail Download file\n" + e);
        }
        return null;
    }

    public Path download2(UploadFiles file) {
        return Paths.get(uploadFiles.get(0).getFilename());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
