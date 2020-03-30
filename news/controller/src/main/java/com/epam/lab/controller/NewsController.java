package com.epam.lab.controller;

import com.epam.lab.dto.AllNewsByQuery;
import com.epam.lab.dto.NewsDTO;
import com.epam.lab.dto.SearchCriteriaDTO;
import com.epam.lab.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/news")
@Validated
@CrossOrigin(origins = {"http://localhost:3000"})
public class NewsController {

    private static final String DON_T_DELETED_MESSAGE = "don't deleted";
    private static final String OK_MESSAGE = "OK";
    private static final String ID_MUST_BE_POSITIVE = "Id must be positive.";
    private static final String ID = "id";

    private NewsService service;

    @Autowired
    public NewsController(NewsService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public NewsDTO findBy(@Valid
                          @PathVariable(ID)
                          @Positive(message = ID_MUST_BE_POSITIVE) long id) {
        return service.findById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public NewsDTO createNews(@RequestBody
                              @Valid NewsDTO newsDTO) {
        return service.create(newsDTO);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteNews(@Valid
                             @PathVariable(ID)
                             @Positive(message = ID_MUST_BE_POSITIVE) long id) {
        if (service.delete(id)) {
            return OK_MESSAGE;
        }
        return DON_T_DELETED_MESSAGE;
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public NewsDTO updateNews(@RequestBody
                              @Valid NewsDTO newsDTO) {
        return service.update(newsDTO);
    }

    @GetMapping(value = "/count")
    public long countNews() {
        return service.countAllNews();
    }


    @GetMapping(value = "/search")
    public AllNewsByQuery searchNews(@Valid SearchCriteriaDTO searchCriteriaDTO,
                                     BindingResult bindingResult) {
        return service.findAllNewsByQuery(searchCriteriaDTO);
    }

}
