package com.epam.lab.repository;

import com.epam.lab.model.Author;

public interface AuthorInterfaceRepository extends InterfaceRepository<Author> {

    Author findBy(Author author);
}
