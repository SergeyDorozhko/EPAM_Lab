package com.epam.lab;

import com.epam.lab.model.Author;
import com.epam.lab.model.User;
import com.epam.lab.repository.AuthorRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws SQLException {
//        ApplicationContext context = new AnnotationConfigApplicationContext("com.epam.lab");
//
//        User user = (User) context.getBean("user");
//        user.setName("asd");
//        AuthorRepository rep = (AuthorRepository) context.getBean("authorRepository");
//        Author author = (Author) context.getBean("author");
//
//        author.setName("Ivan");
//        author.setSurname("Petrov");
//
//        rep.create(author);
//
//        System.out.println(user.getName());
//
//        System.out.println("Hello World!");
    }
}
