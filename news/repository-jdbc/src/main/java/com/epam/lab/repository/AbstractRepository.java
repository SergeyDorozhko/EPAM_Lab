package com.epam.lab.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;

public abstract class AbstractRepository {
    protected JdbcTemplate jdbcTemplate;

    protected KeyHolder keyHolder;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public KeyHolder getKeyHolder() {
        return keyHolder;
    }

    @Autowired
    public void setKeyHolder(KeyHolder keyHolder) {
        this.keyHolder = keyHolder;
    }
}
