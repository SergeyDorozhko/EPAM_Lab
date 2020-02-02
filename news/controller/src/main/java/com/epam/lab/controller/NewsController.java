package com.epam.lab.controller;

import com.epam.lab.dto.NewsDTO;
import com.epam.lab.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@RestController
@RequestMapping("/news")
public class NewsController {
    private NewsService service;

    @Autowired
    public NewsController(NewsService service) {
        this.service = service;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public NewsDTO findBy(@PathVariable long id) {
        return service.findById(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public NewsDTO createNews(@RequestBody NewsDTO newsDTO) {
        return service.create(newsDTO);
    }
}
