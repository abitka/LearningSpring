package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.app.repositories.AccountRepository;
import org.example.web.dto.AccountForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    final private Logger logger = Logger.getLogger(AccountService.class);
    final private AccountRepository<AccountForm> accountRepo;

    @Autowired
    public AccountService(AccountRepository<AccountForm> accountRepo) {
        this.accountRepo = accountRepo;
    }

    public boolean authenticate(AccountForm accountForm) {
        return accountRepo.authenticate(accountForm);
    }

    public List<AccountForm> getAllAccount() {
        return accountRepo.retrieveAll();
    }

    public void saveAccount(AccountForm account) {
        accountRepo.store(account);
    }
}
