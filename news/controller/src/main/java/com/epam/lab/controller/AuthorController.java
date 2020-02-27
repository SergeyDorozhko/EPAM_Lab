package com.epam.lab.controller;

import com.epam.lab.dto.AuthorDTO;
import com.epam.lab.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/author")
@Validated
public class AuthorController {

    private static final String DON_T_DELETED_MESSAGE = "don't deleted";
    private static final String OK_MESSAGE = "OK";
    public static final String ID_MUST_BE_POSITIVE = "Id must be positive.";
    private AuthorService authorService;


    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AuthorDTO findAuthorBy(@Valid @PathVariable("id") @Positive(message = ID_MUST_BE_POSITIVE) long id, HttpServletResponse response) {
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
    public AuthorDTO createAuthor(@RequestBody @Valid AuthorDTO authorDTO) {

        return authorService.create(authorDTO);
    }


    @RequestMapping(value = "/", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String deleteAuthor(@RequestBody @Valid @Positive(message = ID_MUST_BE_POSITIVE) long id) {
        if (authorService.delete(id)) {
            return OK_MESSAGE;
        }
        return DON_T_DELETED_MESSAGE;
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AuthorDTO updateAuthor(@RequestBody @Valid AuthorDTO authorDTO) {
        return authorService.update(authorDTO);
    }
}
