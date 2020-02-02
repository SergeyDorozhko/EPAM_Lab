package com.epam.lab.service;

import com.epam.lab.dto.AuthorDTO;
import com.epam.lab.dto.Mapper.AbstractMapper;
import com.epam.lab.dto.Mapper.AuthorMapper;
import com.epam.lab.dto.NewsDTO;
import com.epam.lab.model.Author;
import com.epam.lab.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service("authorService")
public class AuthorServiceImpl implements AuthorService {

    private AuthorMapper mapper;

    private AuthorRepository repository;

    @Autowired
    public AuthorServiceImpl(AuthorMapper mapper, AuthorRepository repository){
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public AuthorDTO create(AuthorDTO bean) {
        return mapper.toDTO(repository.create(mapper.toBean(bean)));

    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id);
    }

    @Override
    public AuthorDTO update(AuthorDTO bean) {
        return mapper.toDTO(repository.update(mapper.toBean(bean)));
    }


    @Override
    public AuthorDTO findById(int id) {
        return mapper.toDTO(repository.findBy(id));
    }
}
