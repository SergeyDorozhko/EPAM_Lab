package com.epam.lab.repository;

import com.epam.lab.model.Author;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("authorRepository")
public class AuthorRepository extends AbstractRepository implements AuthorInterfaceRepository {

    private final static String CREATE_QUERY = "INSERT INTO author (name, surname) VALUES (";


    @Override
    public Author create(Author bean) {
        String query = CREATE_QUERY + "'" +bean.getName() + "', '"+ bean.getSurname() + "');";
        jdbcTemplate.execute(query);
        return bean;
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
