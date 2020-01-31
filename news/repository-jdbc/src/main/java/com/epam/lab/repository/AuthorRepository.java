package com.epam.lab.repository;

import com.epam.lab.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository("authorRepository")
public class AuthorRepository extends AbstractRepository implements AuthorInterfaceRepository {

    private KeyHolder keyHolder;

    @Autowired
    public AuthorRepository(KeyHolder keyHolder) {
        this.keyHolder = keyHolder;
    }

    private final static String CREATE_QUERY = "INSERT INTO author (name, surname) VALUES (?, ?)";

    private final static String CHECK_AUTHOR = "SELECT id, name, surname, FROM author WHERE id = ? AND name = ? AND surname = ?;";

    @Override
    public Author create(Author bean) {

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(CREATE_QUERY, new String[] {"id"});
            ps.setString(1, bean.getName());
            ps.setString(2, bean.getSurname());
            return ps;
        }, keyHolder);
        bean.setId(keyHolder.getKey().longValue());
        return bean;
    }

    @Override
    public boolean delete(int id) {
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
    public Author findById(int id) {
        return null;
    }

    @Override
    public Author findAuthor(Author author) {
        return jdbcTemplate.queryForObject(CHECK_AUTHOR,
                new Object[] {author.getId(), author.getName(), author.getSurname()},
                new BeanPropertyRowMapper<>(Author.class));
    }
}
