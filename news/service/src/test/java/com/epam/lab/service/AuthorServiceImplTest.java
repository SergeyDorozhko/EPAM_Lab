package com.epam.lab.service;

import com.epam.lab.dto.AuthorDTO;
import com.epam.lab.dto.Mapper.AuthorMapper;
import com.epam.lab.model.Author;
import com.epam.lab.repository.AuthorRepository;
import com.epam.lab.repository.AuthorRepositoryImpl;
import com.epam.lab.repository.NewsRepository;
import com.epam.lab.repository.NewsRepositoryImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

public class AuthorServiceImplTest {


    private static AuthorRepository authorRepository;
    private static NewsRepository newsRepository;
    private static AuthorMapper authorMapper;

    private static AuthorServiceImpl authorService;
    private AuthorDTO authorDTO;
    private static Author authorTestEntity;

    @Before
    public void init() {
        newsRepository = Mockito.mock(NewsRepositoryImpl.class);
        authorRepository = Mockito.mock(AuthorRepositoryImpl.class);
        authorMapper = new AuthorMapper(new ModelMapper());
        authorService = new AuthorServiceImpl(authorMapper, authorRepository, newsRepository);


        authorDTO = new AuthorDTO();
        authorDTO.setName("Test");
        authorDTO.setSurname("Test");
        authorTestEntity = new Author();
        authorTestEntity.setName("Test");
        authorTestEntity.setSurname("Test");
        Mockito.when(authorRepository.create(any(Author.class))).thenReturn(authorTestEntity);

        Mockito.when(authorRepository.delete(110L)).thenReturn(true);
        List<Long> testListOfNewsId = new ArrayList<>();
        testListOfNewsId.add(1L);
        testListOfNewsId.add(2L);
        testListOfNewsId.add(3L);
        Mockito.when(newsRepository.findNewsIdByAuthor(any(Long.class))).thenReturn(testListOfNewsId);
        Mockito.when(newsRepository.delete(any(Long.class))).thenReturn(true);

        Mockito.when(authorRepository.update(any(Author.class))).thenReturn(authorTestEntity);

        Mockito.when(authorRepository.findBy(10)).thenReturn(authorTestEntity);


    }

    @Test
    public void testCreateAuthor() {

        AuthorDTO expectedAuthor = authorService.create(new AuthorDTO());
        Assert.assertEquals(authorDTO, expectedAuthor);
    }

    @Test
    public void testAuthorDelete() {
        Assert.assertEquals(true, authorService.delete(110L));
        Mockito.verify(authorRepository, Mockito.times(1)).delete(110L);
        Mockito.verify(newsRepository, Mockito.times(1)).findNewsIdByAuthor(110L);
        Mockito.verify(newsRepository, Mockito.times(1)).delete(1L);
        Mockito.verify(newsRepository, Mockito.times(1)).delete(2L);
        Mockito.verify(newsRepository, Mockito.times(1)).delete(3L);
        Mockito.verify(newsRepository, Mockito.times(0)).delete(4L);
    }

    @Test
    public void testAuthorUpdate() {
        AuthorDTO actualAuthor = authorService.update(new AuthorDTO());
        Assert.assertEquals(authorDTO, actualAuthor);
        Mockito.verify(authorRepository, Mockito.times(1)).update(new Author());

    }

    @Test
    public void findAuthorByIdTest() {
        AuthorDTO expectedAuthor = authorService.findById(10);
        Assert.assertEquals(authorDTO, expectedAuthor);
        Mockito.verify(authorRepository, Mockito.times(1)).findBy(10);
    }
}
