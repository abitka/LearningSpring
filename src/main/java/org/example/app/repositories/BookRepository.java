package org.example.app.repositories;

import org.example.web.dto.BookFieldsToRemove;
import org.example.web.dto.BookFilter;
import org.example.web.dto.UploadFiles;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

public interface BookRepository<T> {

    List<T> retrieveAll();
    List<T> filterBooks(BookFilter book);

    List<T> showAllFiles();

    void store(T object);

    void removeItemById(Integer idToRemove);
    void removeItemsByFields(BookFieldsToRemove book);

    void upload(MultipartFile file);
    byte[] download(UploadFiles files);

    Path download2(UploadFiles file);
}
