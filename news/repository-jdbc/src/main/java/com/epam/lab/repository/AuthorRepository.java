package com.epam.lab.repository;

import com.epam.lab.model.Author;

public interface AuthorRepository extends Repository<Author> {

    Author findBy(Author author);
}
