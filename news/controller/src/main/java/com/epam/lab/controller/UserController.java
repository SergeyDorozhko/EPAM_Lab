package com.epam.lab.controller;

import com.epam.lab.dto.TagDTO;
import com.epam.lab.dto.UserDTO;
import com.epam.lab.model.User_;
import com.epam.lab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/user")
@Validated
@CrossOrigin(origins = { "http://localhost:3000"})
public class UserController {

    private static final String DON_T_DELETED_MESSAGE = "don't deleted";
    private static final String OK_MESSAGE = "OK";
    private static final String ID = "id";
    public static final String ID_MUST_BE_POSITIVE = "Id must be positive.";

    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public UserDTO createUser(@RequestBody @Valid UserDTO userDTO) {
        return service.create(userDTO);
    }

    @DeleteMapping(value = "/{id}")
    public String delete(@Valid @PathVariable(ID) @Positive(message = ID_MUST_BE_POSITIVE) long id) {
        if(service.delete(id)){
            return OK_MESSAGE;
        }
        return DON_T_DELETED_MESSAGE;
    }

    @PutMapping
    public UserDTO update(@RequestBody @Valid UserDTO userDTO) {
        return service.update(userDTO);
    }

    @GetMapping(value = "/{id}")
    public UserDTO findBy(@Valid @PathVariable(ID) @Positive Long id) {

        return service.findById(id);
    }

    @PostMapping(value = "/singIn")
    public UserDTO singIn(@RequestBody @Valid UserDTO user) {
        return service.singIn(user);
    }
}
