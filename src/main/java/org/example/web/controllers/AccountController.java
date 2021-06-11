package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.AccountService;
import org.example.web.dto.AccountForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/login")
public class AccountController {

    private final Logger logger = Logger.getLogger(AccountController.class);
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public String login(Model model) {
        logger.info("GET /login returns login_page.html");
        model.addAttribute("accountForm", new AccountForm());
        model.addAttribute("accountList", accountService.getAllAccount());
        return "login_page";
    }

    @GetMapping("/registration")
    public String reg(Model model) {
        model.addAttribute("accountForm", new AccountForm());
        logger.info("GET /registration returns registration_page.html");
        return "registration_page";
    }

    @PostMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("accountForm", new AccountForm());
        logger.info("POST /registration redirect:/registration");
        return "redirect:/login/registration";
    }

    @PostMapping("/auth")
    public String authenticate(AccountForm accountForm) {
        if (accountService.authenticate(accountForm)) {
            logger.info("login OK! redirect to book shelf");
            return "redirect:/books/shelf";
        } else {
            logger.info("login FAIL! redirect to registration");
            return "redirect:/login/registration";
        }
    }

    @PostMapping("/registration/save")
    public String saveAccount(AccountForm account) {
        accountService.saveAccount(account);
        logger.info("account repository size: " + accountService.getAllAccount().size());
        return "redirect:/login";
    }
}
