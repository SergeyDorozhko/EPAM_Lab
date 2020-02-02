package com.epam.lab.repository;

import com.epam.lab.model.Tag;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class TagRepositoryImpl extends AbstractRepository implements TagRepository {


    public static final String INSERT_INTO_TAG = "INSERT INTO tag (name) VALUES (?);";
    public static final String DELETE_TAG_BY_ID = "DELETE FROM tag WHERE id = ?";
    public static final String UPDATE_TAG_SET_NAME_WHERE_ID = "UPDATE tag SET name = ? WHERE id = ?";
    public static final String SELECT_TAG_BY_ID = "SELECT id, name FROM tag WHERE id = ?";
    public static final String SELECT_FIND_BY_NAME = "SELECT id, name FROM tag WHERE name = ?";
    public static final String SELECT_TAG_BY_ID_AND_NAME = "SELECT id, name FROM tag WHERE id = ? AND name = ?";

    @Override
    public Tag create(Tag bean) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_INTO_TAG, new String[]{"id"});
            ps.setString(1, bean.getName());
            return ps;
        }, keyHolder);
        bean.setId(keyHolder.getKey().longValue());
        return bean;
    }

    @Override
    public boolean delete(long id) {
        int result = this.jdbcTemplate.update(
                DELETE_TAG_BY_ID,
                id);
        System.out.println(result);
        return result != 0;
    }

    @Override
    public Tag update(Tag bean) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_TAG_SET_NAME_WHERE_ID,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, bean.getName());
            ps.setLong(2, bean.getId());
            return ps;
        }, keyHolder);

        bean.setId((long) keyHolder.getKeys().get("id"));
        bean.setName((String) keyHolder.getKeys().get("name"));

        return bean;
    }

    @Override
    public Tag findBy(long id) {
        return jdbcTemplate.queryForObject(SELECT_TAG_BY_ID,
                new Object[]{id},
                new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Tag findBy(String name) {
        return jdbcTemplate.queryForObject(SELECT_FIND_BY_NAME,
                new Object[] {name},
                new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Tag findBy(Tag tag) {
        return jdbcTemplate.queryForObject(SELECT_TAG_BY_ID_AND_NAME,
                new Object[]{tag.getId(), tag.getName()},
                new BeanPropertyRowMapper<>(Tag.class));
    }
}
