package com.epam.lab.service.impl;

import com.epam.lab.dto.NewsDTO;
import com.epam.lab.dto.mapper.NewsMapper;
import com.epam.lab.model.News;
import com.epam.lab.repository.impl.InitNewsRepositoryImpl;
import com.epam.lab.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@PropertySource(value = "classpath:init.properties")
public class DbFromFilesInitializerImpl implements ApplicationRunner {
    @Value("${rootCatalog}")
    private String baseFolder;
    @Value("${threadsNumber}")
    private int threadsNumber;
    @Value("${scanDelay}")
    private long scanDelay;

    private InitNewsRepositoryImpl repository;
    private NewsService newsService;
    private NewsMapper mapper;


    @Autowired
    public DbFromFilesInitializerImpl(NewsMapper mapper, InitNewsRepositoryImpl repository, NewsService newsService) {
        this.repository = repository;
        this.newsService = newsService;
        this.mapper = mapper;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        while (true) {
            initialize();
            Thread.sleep(TimeUnit.MINUTES.toMillis(scanDelay));
        }
    }

    public void initialize() {
        List<String> files = repository.findAllFiles(baseFolder);
        if (!files.isEmpty()) {
            readFiles(files);
        }
    }

    private void readFiles(List<String> files) {
        ExecutorService executorService = Executors.newFixedThreadPool(threadsNumber);
        for (String file : files) {
            executorService.submit(() -> {
                try {
                    List<News> news = repository.readCatalog(file);
                    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                    Validator validator = factory.getValidator();
                    List<NewsDTO> newsDTO =
                            news.stream()
                                    .map((x) -> mapper.toDTO(x))
                                    .peek(x -> validator.validate(x))
                                    .collect(Collectors.toList());
                    newsService.batchCreate(newsDTO);
                    repository.deleteFile(file);
                } catch (Exception ex) {
                    repository.restoreFile(file, baseFolder);
                }
            });
        }
        executorService.shutdown();
    }
}
