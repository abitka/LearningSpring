package org.example.app.repositories;

import org.example.web.dto.AccountForm;

import java.util.List;

public interface AccountRepository<T> {

    List<T> retrieveAll();

    void store(T account);

    boolean authenticate(T account);
}
