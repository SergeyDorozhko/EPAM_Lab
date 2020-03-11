package com.epam.lab.service;

import com.epam.lab.dto.AuthorDTO;

import java.util.List;

public interface AuthorService extends Service<AuthorDTO> {

    List<AuthorDTO> findAll();

}
