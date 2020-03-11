package com.epam.lab.repository;


import com.epam.lab.model.Author;

import java.util.List;

public interface AuthorRepository extends Repository<Author> {

    Author findBy(Author author);

    List<Author> findAll();
}