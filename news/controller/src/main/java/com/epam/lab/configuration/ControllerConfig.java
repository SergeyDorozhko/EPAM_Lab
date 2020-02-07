package com.epam.lab.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ResourceBundle;

@Configuration
@ComponentScan("com.epam.lab")
public class ControllerConfig {

    @Bean
    ResourceBundle resourceBundle(){
        return ResourceBundle.getBundle("/exceptionMessages");
    }

}
