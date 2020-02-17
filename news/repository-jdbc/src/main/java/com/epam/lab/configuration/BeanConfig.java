package com.epam.lab.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@Configuration
@ComponentScan(basePackages = "com.epam.lab")
public class BeanConfig {


    @Bean
    public KeyHolder keyHolder() {
        return new GeneratedKeyHolder();
    }
}
