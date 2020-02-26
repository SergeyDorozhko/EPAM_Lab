package com.epam.lab.controller;

import com.epam.lab.dto.NewsDTO;
import com.epam.lab.dto.SearchCriteriaDTO;
import com.epam.lab.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/news")
@Validated
public class NewsController {

    private static final String DON_T_DELETED_MESSAGE = "don't deleted";
    private static final String OK_MESSAGE = "OK";

    private NewsService service;

    @Autowired
    public NewsController(NewsService service) {
        this.service = service;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public NewsDTO findBy(@Valid @PathVariable("id") @Positive long id) {
        return service.findById(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public NewsDTO createNews(@RequestBody @Valid NewsDTO newsDTO) {
        return service.create(newsDTO);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteNews(@Valid @PathVariable("id") @Positive long id) {
        if(service.delete(id)){
            return OK_MESSAGE;
        }
        return DON_T_DELETED_MESSAGE;
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public NewsDTO updateNews(@RequestBody @Valid NewsDTO newsDTO) {
        return service.update(newsDTO);
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public long countNews() {
        return service.countAllNews();
    }


    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<NewsDTO> searchNews(@ModelAttribute @Valid SearchCriteriaDTO searchCriteriaDTO) {
        return service.findAllNewsByQuery(searchCriteriaDTO);
    }

}
