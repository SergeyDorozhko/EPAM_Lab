package com.epam.lab.service.impl;

import com.epam.lab.configuration.ServiceConfig;
import com.epam.lab.dto.NewsDTO;
import com.epam.lab.dto.mapper.NewsMapper;
import com.epam.lab.dto.mapper.SearchCriteriaMapper;
import com.epam.lab.model.News;
import com.epam.lab.repository.AuthorRepository;
import com.epam.lab.repository.NewsRepository;
import com.epam.lab.repository.TagRepository;
import com.epam.lab.repository.impl.AuthorRepositoryImpl;
import com.epam.lab.repository.impl.NewsRepositoryImpl;
import com.epam.lab.repository.impl.TagRepositoryImpl;
import com.epam.lab.service.NewsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class})
@Transactional
public class NewsServiceImplTransactionTest {




    @Autowired
    private NewsService newsService;

    private NewsDTO newsDTO;

    @Before
    public void init() {

    }

    @Test
    public void findNewsByIdTest() {
        System.out.println(newsService.findById(2));
    }
}
