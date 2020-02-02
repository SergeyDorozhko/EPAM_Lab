package com.epam.lab.controller;

import com.epam.lab.dto.AuthorDTO;
import com.epam.lab.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/author")
public class AuthorController {

    public static final String DON_T_DELETED_MESSAGE = "don't deleted";
    public static final String OK_MESSAGE = "OK";
    private AuthorService authorService;


    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }


    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AuthorDTO findAuthorBy(@RequestBody int id, HttpServletResponse response) {
        AuthorDTO authorDTO = null;
        try {
             authorDTO = authorService.findById(id);
        } catch (Exception ex) {
            response.setStatus(204);
        }
        return authorDTO;
    }


    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AuthorDTO createAuthor(@RequestBody AuthorDTO authorDTO) {

        return authorService.create(authorDTO);
    }


    @RequestMapping(value = "/", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String deleteAuthor(@RequestBody int id) {
        if (authorService.delete(id)) {
            return OK_MESSAGE;
        }
        return DON_T_DELETED_MESSAGE;
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AuthorDTO updateAuthor(@RequestBody AuthorDTO authorDTO) {
        return authorService.update(authorDTO);
    }
}
