package com.epam.lab.controller;

import com.epam.lab.dto.AbstractDTO;
import com.epam.lab.dto.AuthorDTO;
import com.epam.lab.dto.NewsDTO;
import com.epam.lab.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthorController {

    private AuthorService authorService;

    private AuthorDTO authorDTO;
    @Autowired
    public AuthorController (AuthorService authorService, AuthorDTO authorDTO){
        this.authorService = authorService;
        this.authorDTO = authorDTO;
    }



    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String welcome() {
        return "Welcome to RestTemplate Example.";
    }



    @RequestMapping(value = "/author", method = RequestMethod.POST)
    @ResponseBody
    public AuthorDTO createAuthor(@RequestBody AuthorDTO authorDTO){

        return authorService.create(authorDTO);
    }


}
