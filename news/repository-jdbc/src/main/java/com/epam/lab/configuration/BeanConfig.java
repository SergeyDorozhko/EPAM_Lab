package com.epam.lab.configuration;

import com.epam.lab.model.Author;
import com.epam.lab.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Autowired
    private User getUser;

    @Autowired
    private Author getAuthor;
}
