package com.epam.lab.configuration;

import com.epam.lab.model.Author;
import com.epam.lab.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@Configuration
@ComponentScan(basePackages = "com.epam.lab")
public class BeanConfig {
    @Autowired
    private User getUser;

    @Autowired
    private Author getAuthor;


    @Bean
    public KeyHolder keyHolder() {
        return new GeneratedKeyHolder();
    }
}
