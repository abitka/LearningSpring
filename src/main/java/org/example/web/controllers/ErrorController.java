package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

    private final Logger logger = Logger.getLogger(ErrorController.class);

    @GetMapping("/404")
    public String notFoundError() {
        return "/errors/404";
    }
}
