package com.epam.lab.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class AbstractRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
}
