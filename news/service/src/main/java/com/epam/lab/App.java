package com.epam.lab;

import com.epam.lab.dto.AbstractDTO;
import com.epam.lab.dto.AuthorDTO;
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

        AuthorDTO authorDTO = (AuthorDTO) context.getBean("authorDTO");

        authorDTO.setName("dsf");
        authorDTO.setSurname("dsfdsf");
        AbstractDTO abstractDTO = service.create(authorDTO);
        System.out.println(abstractDTO);

        System.out.println("Hello World!");
    }
}
