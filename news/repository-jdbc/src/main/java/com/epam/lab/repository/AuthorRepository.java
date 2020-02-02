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

    private static final String CREATE_QUERY = "INSERT INTO author (name, surname) VALUES (?, ?)";
    private static final String CHECK_AUTHOR = "SELECT id, name, surname FROM author WHERE id = ? AND name = ? AND surname = ?;";
    private static final String FIND_AUTHOR_BY_ID = "SELECT id, name, surname FROM author WHERE id = ? ;";
    public static final String DELETE_AUTHOR_BY_ID = "DELETE FROM author WHERE id = ?";
    public static final String UPDATE_AUTHOR_NAME_SURNAME_BY_ID = "UPDATE author SET name = ?, surname = ? WHERE id = ?;";


    private KeyHolder keyHolder;

    @Autowired
    public AuthorRepository(KeyHolder keyHolder) {
        this.keyHolder = keyHolder;
    }

    @Override
    public Author create(Author bean) {
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(CREATE_QUERY, new String[]{"id"});
            ps.setString(1, bean.getName());
            ps.setString(2, bean.getSurname());
            return ps;
        }, keyHolder);
        bean.setId(keyHolder.getKey().longValue());
        return bean;
    }

    @Override
    public boolean delete(int id) {
        int result = this.jdbcTemplate.update(
                DELETE_AUTHOR_BY_ID,
                id);
        System.out.println(result);
        return result != 0;
    }

    @Override
    public Author update(Author bean) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_AUTHOR_NAME_SURNAME_BY_ID, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, bean.getName());
            ps.setString(2, bean.getSurname());
            ps.setLong(3, bean.getId());
            return ps;
        }, keyHolder);
        bean.setId((long) keyHolder.getKeys().get("id"));
        bean.setName((String) keyHolder.getKeys().get("name"));
        bean.setSurname((String) keyHolder.getKeys().get("surname"));

        return bean;
    }

    @Override
    public Author findBy(int id) {
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
