package com.epam.lab.repository.impl;

import com.epam.lab.exception.RepositoryException;
import com.epam.lab.model.Author;
import com.epam.lab.repository.AbstractRepository;
import com.epam.lab.repository.AuthorRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository("authorRepository")
public class AuthorRepositoryImpl extends AbstractRepository implements AuthorRepository {

    private static final String CREATE_QUERY
            = "INSERT INTO author (name, surname) VALUES (?, ?)";
    private static final String CHECK_AUTHOR
            = "SELECT id, name, surname FROM author WHERE id = ? AND name = ? AND surname = ?;";
    private static final String FIND_AUTHOR_BY_ID
            = "SELECT id, name, surname FROM author WHERE id = ? ;";
    private static final String DELETE_AUTHOR_BY_ID
            = "DELETE FROM author WHERE id = ?";
    private static final String UPDATE_AUTHOR_NAME_SURNAME_BY_ID
            = "UPDATE author SET name = ?, surname = ? WHERE id = ?;";
    private static final String AUTHOR_ID = "id";



    @Override
    public Author create(Author bean) {
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(CREATE_QUERY, new String[]{AUTHOR_ID});
            int counter = 1;
            ps.setString(counter++, bean.getName());
            ps.setString(counter, bean.getSurname());
            return ps;
        }, keyHolder);
        setGeneratedIdToAuthor(bean, keyHolder);
        return bean;
    }

    private void setGeneratedIdToAuthor(Author author, KeyHolder keyHolder) {
        author.setId(keyHolder.getKey().longValue());
    }

    @Override
    public boolean delete(long id) {
        int result = jdbcTemplate.update(
                DELETE_AUTHOR_BY_ID,
                id);
        return isDeleted(result);
    }

    private boolean isDeleted(int numberOfDeletedLines) {
        return numberOfDeletedLines != 0;
    }

    @Override
    public Author update(Author bean) {
        int result = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    UPDATE_AUTHOR_NAME_SURNAME_BY_ID, Statement.RETURN_GENERATED_KEYS);
            int counter = 1;
            ps.setString(counter++, bean.getName());
            ps.setString(counter++, bean.getSurname());
            ps.setLong(counter, bean.getId());
            return ps;
        }, keyHolder);

        boolean noUpdates = result == 0;
        if (noUpdates) {
            throw new RepositoryException("Error author id");
        }
        return bean;
    }

    @Override
    public Author findBy(long id) {
        return jdbcTemplate.queryForObject(FIND_AUTHOR_BY_ID,
                new Object[]{id},
                new BeanPropertyRowMapper<>(Author.class));
    }

    @Override
    public Author findBy(Author author) {
        return jdbcTemplate.queryForObject(CHECK_AUTHOR,
                new Object[]{author.getId(), author.getName(), author.getSurname()},
                new BeanPropertyRowMapper<>(Author.class));
    }
}
