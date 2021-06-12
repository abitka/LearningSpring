package org.example.app.repositories;

import org.apache.log4j.Logger;
import org.example.web.dto.AccountForm;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountRepositoryImpl implements AccountRepository<AccountForm> {

    private final Logger logger = Logger.getLogger(AccountRepositoryImpl.class);
    private final List<AccountForm> repo = new ArrayList<>();

    {
        repo.add(new AccountForm("root", "123"));
    }

    @Override
    public List<AccountForm> retrieveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(AccountForm accountForm) {
        for (AccountForm user : retrieveAll()) {
            if (user.getUsername().equalsIgnoreCase(accountForm.getUsername())) {
                logger.info("Such user already registered!");
                return;
            }
        }

        repo.add(accountForm);
    }

    @Override
    public boolean authenticate(AccountForm accountForm) {
        logger.info("try auth with user-form: " + accountForm);
        for (AccountForm account : retrieveAll()) {
            if (account.getUsername().equals(accountForm.getUsername()) &&
                    account.getPassword().equals(accountForm.getPassword()))
                return true;
        }
        return false;
    }
}
