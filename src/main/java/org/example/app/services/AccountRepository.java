package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.AccountForm;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountRepository implements IAccountRepository<AccountForm> {

    private final Logger logger = Logger.getLogger(AccountRepository.class);
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
        if (!accountForm.getUserName().isEmpty() && !accountForm.getPassword().isEmpty())
            repo.add(accountForm);
    }

    @Override
    public boolean authenticate(AccountForm accountForm) {
        logger.info("try auth with user-form: " + accountForm);
        for (AccountForm account : retrieveAll()) {
            if (account.getUserName().equals(accountForm.getUserName()) &&
                    account.getPassword().equals(accountForm.getPassword()))
                return true;
        }
        return false;
    }
}
