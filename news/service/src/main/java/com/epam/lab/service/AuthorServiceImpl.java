package com.epam.lab.service;

import com.epam.lab.dto.AuthorDTO;
import com.epam.lab.dto.Mapper.AuthorMapper;
import com.epam.lab.repository.AuthorRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("authorService")
public class AuthorServiceImpl implements AuthorService {

    private AuthorMapper mapper;

    private AuthorRepositoryImpl repository;

    @Autowired
    public AuthorServiceImpl(AuthorMapper mapper, AuthorRepositoryImpl repository){
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public AuthorDTO create(AuthorDTO bean) {
        return mapper.toDTO(repository.create(mapper.toBean(bean)));

    }

    @Override
    public boolean delete(long id) {
        return repository.delete(id);
    }

    @Override
    public AuthorDTO update(AuthorDTO bean) {
        return mapper.toDTO(repository.update(mapper.toBean(bean)));
    }


    @Override
    public AuthorDTO findById(long id) {
        return mapper.toDTO(repository.findBy(id));
    }
}
