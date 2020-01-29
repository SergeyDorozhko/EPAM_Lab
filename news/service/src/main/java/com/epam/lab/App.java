package com.epam.lab;

import com.epam.lab.model.Author;
import com.epam.lab.service.InterfaceService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext("com.epam.lab");

        InterfaceService service = (InterfaceService) context.getBean("authorService");

        Author author = (Author) context.getBean("author");

        author.setName("dsf");
        author.setSurname("dsfdsf");
        author = (Author) service.create(author);
        System.out.println(author);

        System.out.println("Hello World!");
    }
}
