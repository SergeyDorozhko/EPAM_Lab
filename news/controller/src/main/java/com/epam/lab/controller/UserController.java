package com.epam.lab.controller;

import com.epam.lab.dto.UserDTO;
import com.epam.lab.dto.UserDetailsImpl;
import com.epam.lab.security.JwtResponse;
import com.epam.lab.security.JwtUtils;
import com.epam.lab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@Validated
@CrossOrigin(origins = {"http://localhost:3000"})
public class UserController {


    private static final String DON_T_DELETED_MESSAGE = "don't deleted";
    private static final String OK_MESSAGE = "OK";
    private static final String ID = "id";
    private static final String ID_MUST_BE_POSITIVE = "Id must be positive.";
    private static final String REGISTERED_SUCCESSFULLY = "User registered successfully!";

    private UserService service;

    AuthenticationManager authenticationManager;

    PasswordEncoder encoder;

    JwtUtils jwtUtils;


    @Autowired
    public UserController(UserService service,
                          AuthenticationManager authenticationManager,
                          PasswordEncoder encoder,
                          JwtUtils jwtUtils) {
        this.service = service;
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }


    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@Valid
                         @PathVariable(ID)
                         @Positive(message = ID_MUST_BE_POSITIVE) long id) {
        if (service.delete(id)) {
            return OK_MESSAGE;
        }
        return DON_T_DELETED_MESSAGE;
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public UserDTO update(@RequestBody
                          @Valid UserDTO userDTO) {
        return service.update(userDTO);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public UserDTO findBy(@Valid
                          @PathVariable(ID)
                          @Positive Long id) {

        return service.findById(id);
    }


    @PostMapping(value = "/signin",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticateUser(@Valid
                                              @RequestBody UserDTO loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }

    @PostMapping(value = "/signup",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@Valid
                                          @RequestBody UserDTO signUpUser) {
        signUpUser.setPassword(encoder.encode(signUpUser.getPassword()));
        service.create(signUpUser);
        return ResponseEntity.ok(REGISTERED_SUCCESSFULLY);
    }
}
