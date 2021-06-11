package org.example.app.services;

import org.example.web.dto.AccountForm;

import java.util.List;

public interface IAccountRepository<T> {

    List<T> retrieveAll();

    void store(T account);

    boolean authenticate(T account);
}
