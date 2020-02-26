package com.epam.lab.controller;

import com.epam.lab.dto.TagDTO;
import com.epam.lab.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/tag")
@Validated
public class TagController {

    private static final String DON_T_DELETED_MESSAGE = "don't deleted";
    private static final String OK_MESSAGE = "OK";

    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TagDTO createTag(@RequestBody @Valid TagDTO tagDTO) {

        return tagService.create(tagDTO);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TagDTO findTagBy(@Valid @PathVariable("id") @Positive Long id) {
        TagDTO tagDTO = null;
        System.out.println(id);
            tagDTO = tagService.findById(id);
        System.out.println(id);

        return tagDTO;
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TagDTO updateTag(@RequestBody @Valid TagDTO tagDTO) {
        return tagService.update(tagDTO);
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteTag(@Valid @RequestBody @Positive long id) {
        if(tagService.delete(id)){
            return OK_MESSAGE;
        }
        return DON_T_DELETED_MESSAGE;
    }
}
