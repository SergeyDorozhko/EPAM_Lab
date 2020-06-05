package com.epam.lab.service.impl;

import com.epam.lab.model.News;
import com.epam.lab.repository.impl.InitNewsRepositoryImpl;
import com.epam.lab.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class DbFromFilesInitializerImpl implements ApplicationRunner {

    private static final String BASE_FOLDER = "D:\\java\\JsonNewsFiles\\";
    private InitNewsRepositoryImpl repository;
    private NewsService newsService;


    @Autowired
    public DbFromFilesInitializerImpl(InitNewsRepositoryImpl repository, NewsService newsService) {
        this.repository = repository;
        this.newsService = newsService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //   while (true)
        initialize();
    }

    public void initialize() {
        List<String> files = repository.findAllFiles(BASE_FOLDER);
        System.out.println(files.size() + "START");
        if (!files.isEmpty()) {
            readFiles(files);
        }
    }

    private void readFiles(List<String> files) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (String file : files) {
            executorService.submit(() -> {
                try {
                    List<News> news = repository.readCatalog(file);
//                    System.err.println(news.size() + "-------------" + "\n" + file + "\n" + Thread.currentThread().getName());
//                    System.err.println("DATA --------- " + news);
                    newsService.batchCreate(news);
                    repository.deleteFile(file);
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                    repository.restoreFile(file, BASE_FOLDER);
                }
            });
        }
        executorService.shutdown();
    }

}
