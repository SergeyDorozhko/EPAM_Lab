package com.epam.lab.service;

import com.epam.lab.model.Author;
import com.epam.lab.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
@Service("authorService")
public class AuthorServiceImpl implements InterfaceService<Integer, Author> {

    @Autowired
    AuthorRepository repository;

    @Override
    public Author create(Author bean) {
        return repository.create(bean);
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public Author update(Author bean) {
        return null;
    }

    @Override
    public List<Author> findAll() {
        return null;
    }

    @Override
    public Author findById(Integer id) {
        return null;
    }
}
