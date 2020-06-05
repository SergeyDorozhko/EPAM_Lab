package com.epam.lab;


import com.epam.lab.service.NewsService;
import com.epam.lab.service.impl.DbFromFilesInitializerImpl;
import com.epam.lab.service.impl.NewsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NewsStarter {


    public static void main(String[] args) {
        SpringApplication.run(NewsStarter.class, args);

    }
}
