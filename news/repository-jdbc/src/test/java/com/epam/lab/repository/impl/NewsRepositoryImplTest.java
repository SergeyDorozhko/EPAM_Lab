package com.epam.lab.repository.impl;

import com.epam.lab.configuration.BeanConfig;
import com.epam.lab.configuration.DataConfig;
import com.epam.lab.exception.RepositoryException;
import com.epam.lab.model.News;
import com.epam.lab.model.SearchCriteria;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataConfig.class, BeanConfig.class})
@Transactional
public class NewsRepositoryImplTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    KeyHolder keyHolder;

    NewsRepositoryImpl newsRepository;

    @Before
    public void init() {
        newsRepository = new NewsRepositoryImpl();
        newsRepository.setJdbcTemplate(jdbcTemplate);
        newsRepository.setKeyHolder(keyHolder);
    }


    @Test
    @Rollback
    public void createPositive() {

        News expectedNews = new News();
        expectedNews.setTitle("title");
        expectedNews.setShortText("short");
        expectedNews.setFullText("full");
        expectedNews.setCreationDate(LocalDate.now());
        expectedNews.setModificationDate(LocalDate.now());

        News result = newsRepository.create(expectedNews);
        expectedNews.setId(result.getId());

        News actual = newsRepository.findBy(result.getId());
        Assert.assertEquals(expectedNews, actual);

    }

    @Test(expected = RuntimeException.class)
    @Rollback
    public void createNegative() {
        newsRepository.create(new News());
    }

    @Test
    @Rollback
    public void linkAuthorWithNews() {
        newsRepository.linkAuthorWithNews(1, 1);
        long actual = newsRepository.findAuthorIdByNewsId(1);
        Assert.assertEquals(1L, actual);
    }

    @Test
    @Rollback
    public void deletePositive() {
        Assert.assertEquals(true, newsRepository.delete(1));
    }

    @Test
    public void deleteNegative() {
        Assert.assertEquals(false, newsRepository.delete(-1));
    }


    @Test
    @Rollback
    public void updatePositive() {
        News testNews = newsRepository.findBy(5);
        testNews.setFullText("Modificated");
        newsRepository.update(testNews);
        News actualNews = newsRepository.findBy(5);
        Assert.assertEquals(testNews, actualNews);
    }

    @Test(expected = RepositoryException.class)
    public void updateNegative() {

        News news = new News();
        news.setTitle("dfsds");
        news.setFullText("dgfsg");
        news.setShortText("dfdsfds");
        news.setModificationDate(LocalDate.now());
        newsRepository.update(news);

    }

    @Test
    @Rollback
    public void findAuthorIdByNewsIdPositive() {
        newsRepository.linkAuthorWithNews(1, 1);
        long actual = newsRepository.findAuthorIdByNewsId(1);
        Assert.assertEquals(1L, actual);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void findAuthorIdByNewsIdNegative() {
        long actual = newsRepository.findAuthorIdByNewsId(1);
        Assert.assertEquals(1L, actual);
    }

    @Test
    @Rollback
    public void findNewsIdByAuthor() {
        newsRepository.linkAuthorWithNews(1, 1);
        newsRepository.linkAuthorWithNews(1, 8);
        newsRepository.linkAuthorWithNews(1, 9);
        Assert.assertEquals(3, newsRepository.findNewsIdByAuthor(1).size()); //If test fail first check Fill_tables.sql
    }

    @Test
    public void findByPositive() {
        int id = 1;
        News expectedNews = newsRepository.findBy(id);
        Assert.assertEquals(id, expectedNews.getId());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void findByNegative() {
        newsRepository.findBy(0);
    }



    @Test
    public void countAllNewsTest() {
    Assert.assertEquals(20, newsRepository.countAllNews()); //If test fail first check Fill_tables.sql
    }

    @Test
    public void findAllNewsAndSortByQuery() {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setAuthorName("Igor");
        searchCriteria.setAuthorSurname("Bikov");
        List<News> newsList = newsRepository.findAllNewsAndSortByQuery(searchCriteria);
        Assert.assertEquals(4, newsList.size());

        searchCriteria = new SearchCriteria();
        newsList = newsRepository.findAllNewsAndSortByQuery(searchCriteria);
        Assert.assertEquals(20, newsList.size());


        searchCriteria = new SearchCriteria();
        searchCriteria.setAuthorName("Igorghjugh");
        searchCriteria.setAuthorSurname("Bikov");
        newsList = newsRepository.findAllNewsAndSortByQuery(searchCriteria);
        Assert.assertEquals(0, newsList.size());
    }


}