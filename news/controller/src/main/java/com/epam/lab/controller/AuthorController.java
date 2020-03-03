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


    @GetMapping(value = "/{id}")
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


    @PostMapping(value = "/")
    @ResponseBody
    public AuthorDTO createAuthor(@RequestBody @Valid AuthorDTO authorDTO) {

        return authorService.create(authorDTO);
    }


    @DeleteMapping(value = "/")
    @ResponseBody
    public String deleteAuthor(@RequestBody @Valid @Positive(message = ID_MUST_BE_POSITIVE) long id) {
        if (authorService.delete(id)) {
            return OK_MESSAGE;
        }
        return DON_T_DELETED_MESSAGE;
    }

    @PutMapping(value = "/")
    @ResponseBody
    public AuthorDTO updateAuthor(@RequestBody @Valid AuthorDTO authorDTO) {
        return authorService.update(authorDTO);
    }
}
