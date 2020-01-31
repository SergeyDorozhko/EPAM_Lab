package com.epam.lab.repository;

import com.epam.lab.model.Author;

public interface AuthorInterfaceRepository extends InterfaceRepository<Author> {

    Author findAuthor(Author author);
}
