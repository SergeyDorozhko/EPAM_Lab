package com.epam.lab.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {
    private static final String WELCOME = "Welcome page";

    @GetMapping(value = "/")
    @ResponseBody
    public String welcome() {
        return WELCOME;
    }
}
