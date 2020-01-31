package com.epam.lab.controller;

import com.epam.lab.dto.AuthorDTO;
import com.epam.lab.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
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
    public AuthorDTO welcome() {

        AuthorDTO  authorDTO = new AuthorDTO();
        authorDTO.setName("authorname");
        authorDTO.setSurname("surname");
        return authorDTO;
    }



    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AuthorDTO createAuthor(@RequestBody AuthorDTO authorDTO){

        return authorService.create(authorDTO);
    }


}
