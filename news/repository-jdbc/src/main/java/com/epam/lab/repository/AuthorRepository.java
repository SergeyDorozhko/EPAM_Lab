package com.epam.lab.repository;

import com.epam.lab.model.Author;

public interface AuthorRepository extends InterfaceRepository<Author> {

    Author findBy(Author author);
}
