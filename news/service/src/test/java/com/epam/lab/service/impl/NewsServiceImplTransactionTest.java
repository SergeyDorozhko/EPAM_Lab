package com.epam.lab.service.impl;

import com.epam.lab.configuration.RepositoryConfig;
import com.epam.lab.configuration.ServiceConfig;
import com.epam.lab.dto.AuthorDTO;
import com.epam.lab.dto.NewsDTO;
import com.epam.lab.dto.TagDTO;
import com.epam.lab.dto.mapper.NewsMapper;
import com.epam.lab.dto.mapper.SearchCriteriaMapper;
import com.epam.lab.exception.RepositoryException;
import com.epam.lab.repository.NewsRepository;
import com.epam.lab.repository.impl.AuthorRepositoryImpl;
import com.epam.lab.repository.impl.TagRepositoryImpl;
import com.epam.lab.service.AuthorService;
import com.epam.lab.service.NewsService;
import com.epam.lab.service.TagService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RepositoryConfig.class)
public class NewsServiceImplTransactionTest {

    public static int ID = 20;
    @Autowired
    private NewsService newsService;

    @Autowired
    private AuthorService authorService;


    @Test(expected = RepositoryException.class)
    public void createNegative() {

        NewsDTO newsDTO = new NewsDTO();
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("TestName2");
        authorDTO.setSurname("TestSurname");
        newsDTO.setAuthor(authorDTO);
        newsDTO.setTitle("TestTitle");
        newsDTO.setShortText("TestShortTest");
        try {
            newsService.create(newsDTO);
        } catch (PersistenceException ex) {
            System.out.println(ex.getMessage());
        }
        authorService.findById(++ID);
    }

    @Test
    @Transactional
    @Rollback
    public void createPositive() {
        NewsDTO newsDTO = new NewsDTO();
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("TestName2");
        authorDTO.setSurname("TestSurname");
        newsDTO.setAuthor(authorDTO);
        newsDTO.setTitle("TestTitle");
        newsDTO.setShortText("TestShortTest");
        newsDTO.setFullText("TestFullTest");

        newsDTO = newsService.create(newsDTO);
        Assert.assertNotEquals(0, newsDTO.getAuthor().getId());
        Assert.assertEquals(authorDTO.getName(), authorService.findById(++ID).getName());

    }

    @Test(expected = RepositoryException.class)
    public void updateNegative() {

        NewsDTO update = new NewsDTO();
        update.setId(20);
        update.setTitle("TestTitle");
        update.setShortText("TestShortTest");
        update.setFullText("TestFullTest");
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("TestName2kkk");
        authorDTO.setSurname("TestSurname");
        update.setAuthor(authorDTO);
        List<TagDTO> tags = new ArrayList<>();
        tags.add(new TagDTO());
        update.setTags(tags);

        try {
           newsService.update(update);
        } catch (PersistenceException ex) {
            System.out.println(ex.getMessage());
        }

        AuthorDTO  byId = authorService.findById(++ID);

    }

    @Test
    @Transactional
    @Rollback
    public void updatePositive() {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setTitle("TestTitle");
        newsDTO.setShortText("TestShortTest");
        newsDTO.setFullText("TestFullTest");
        newsService.create(newsDTO);

        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("TestName2");
        authorDTO.setSurname("TestSurname");
        newsDTO.setAuthor(authorDTO);
        newsDTO.setCreationDate(LocalDate.now());
        try {
            newsDTO = newsService.update(newsDTO);
        } catch (PersistenceException ex) {
            System.out.println(ex.getMessage());
        }

        Assert.assertNotEquals(0,newsDTO.getAuthor().getId());
        Assert.assertEquals(authorDTO.getName(), authorService.findById(++ID).getName());

    }

}
