package com.epam.lab.controller;

import com.epam.lab.dto.TagDTO;
import com.epam.lab.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/tag")
@Validated
@CrossOrigin(origins = { "http://localhost:3000"})
public class TagController {

    private static final String DON_T_DELETED_MESSAGE = "don't deleted";
    private static final String OK_MESSAGE = "OK";
    private static final String ID = "id";
    public static final String ID_MUST_BE_POSITIVE = "Id must be positive.";


    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping(value = "/")
    @ResponseBody
    public TagDTO createTag(@RequestBody @Valid TagDTO tagDTO) {

        return tagService.create(tagDTO);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public TagDTO findTagBy(@Valid @PathVariable(ID) @Positive Long id) {
        TagDTO tagDTO = null;
            tagDTO = tagService.findById(id);
        return tagDTO;
    }

    @PutMapping(value = "/")
    @ResponseBody
    public TagDTO updateTag(@RequestBody @Valid TagDTO tagDTO) {
        return tagService.update(tagDTO);
    }

    @DeleteMapping(value = "/{id}")
    public String deleteTag(@Valid @PathVariable(ID) @Positive(message = ID_MUST_BE_POSITIVE) long id) {
        if(tagService.delete(id)){
            return OK_MESSAGE;
        }
        return DON_T_DELETED_MESSAGE;
    }
}
