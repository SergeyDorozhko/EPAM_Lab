package com.epam.lab.service.impl;

import com.epam.lab.dto.AuthorDTO;
import com.epam.lab.dto.Mapper.NewsMapper;
import com.epam.lab.dto.NewsDTO;
import com.epam.lab.dto.SearchCriteria;
import com.epam.lab.dto.TagDTO;
import com.epam.lab.model.Author;
import com.epam.lab.model.News;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.AuthorRepository;
import com.epam.lab.repository.NewsRepository;
import com.epam.lab.repository.TagRepository;
import com.epam.lab.repository.impl.AuthorRepositoryImpl;
import com.epam.lab.repository.impl.NewsRepositoryImpl;
import com.epam.lab.repository.impl.TagRepositoryImpl;
import com.epam.lab.service.NewsService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(Parameterized.class)
public class SearchCriteriaTest {

    private SearchCriteria testQuery;
    private NewsDTO expectedNews;
    private News actualNews;


    private AuthorRepository authorRepository;
    private NewsRepository newsRepository;
    private TagRepository tagRepository;
    private NewsMapper newsMapper;

    private NewsService newsService;

    public SearchCriteriaTest(SearchCriteria testQuery, News actualNews, NewsDTO expectedNews) {
        this.testQuery = testQuery;
        this.actualNews = actualNews;
        this.expectedNews = expectedNews;
    }


    @Before
    public void init() {
        authorRepository = Mockito.mock(AuthorRepositoryImpl.class);
        newsRepository = Mockito.mock(NewsRepositoryImpl.class);
        tagRepository = Mockito.mock(TagRepositoryImpl.class);
        newsMapper = new NewsMapper(new ModelMapper());

        newsService = new NewsServiceImpl(newsMapper, newsRepository, authorRepository, tagRepository);


    }


    @Parameterized.Parameters(name = "{index}: Test with {0}, {1}, result = {2}")
    public static Collection<Object[]> dataForSearch() {
        return Arrays.asList(new Object[][]{
                {new SearchCriteria("authorName", "authorSurname",
                        new LinkedHashSet<String>(), new LinkedHashSet<String>(), false),

                        new News(1, "title", "shortText", "fullText",
                                LocalDate.now(), LocalDate.now(),
                                new Author(6, "authorName", "authorSurname"), new ArrayList<Tag>()),

                        new NewsDTO(1, "title", "shortText", "fullText",
                                LocalDate.now(), LocalDate.now(),
                                new AuthorDTO(6, "authorName", "authorSurname"), new ArrayList<TagDTO>())},


                {new SearchCriteria("authorName", "",
                        new LinkedHashSet<String>(), new LinkedHashSet<String>(), true),

                        new News(2, "title", "shortText", "fullText",
                                LocalDate.now(), LocalDate.now(),
                                new Author(5, "authorName", "authorSurname"), new ArrayList<Tag>()),

                        new NewsDTO(2, "title", "shortText", "fullText",
                                LocalDate.now(), LocalDate.now(),
                                new AuthorDTO(5, "authorName", "authorSurname"), new ArrayList<TagDTO>())},

                {new SearchCriteria("authorName", "",
                        new LinkedHashSet<String>(), new LinkedHashSet<String>(Arrays.asList(new String[] {"author_name"})), true),

                        new News(2, "title", "shortText", "fullText",
                                LocalDate.now(), LocalDate.now(),
                                new Author(5, "authorName", "authorSurname"), new ArrayList<Tag>()),

                        new NewsDTO(2, "title", "shortText", "fullText",
                                LocalDate.now(), LocalDate.now(),
                                new AuthorDTO(5, "authorName", "authorSurname"), new ArrayList<TagDTO>())},

                {new SearchCriteria("authorName", "",
                        new LinkedHashSet<String>(), new LinkedHashSet<String>(Arrays.asList(new String[] {"author_name", "title"})), true),

                        new News(2, "title", "shortText", "fullText",
                                LocalDate.now(), LocalDate.now(),
                                new Author(5, "authorName", "authorSurname"), new ArrayList<Tag>()),

                        new NewsDTO(2, "title", "shortText", "fullText",
                                LocalDate.now(), LocalDate.now(),
                                new AuthorDTO(5, "authorName", "authorSurname"), new ArrayList<TagDTO>())},

                {new SearchCriteria("authorName", "",
                        new LinkedHashSet<String>(Arrays.asList(new String[] {"news", "sport"})), new LinkedHashSet<String>(), true),

                        new News(3, "title", "shortText", "fullText",
                                LocalDate.now(), LocalDate.now(),
                                new Author(5, "authorName", "authorSurname"), new ArrayList<Tag>(Arrays.asList(new Tag[] {new Tag(1,"news"), new Tag(2,"sport")}))),

                        new NewsDTO(3, "title", "shortText", "fullText",
                                LocalDate.now(), LocalDate.now(),
                                new AuthorDTO(5, "authorName", "authorSurname"), new ArrayList<TagDTO>())}

        });
    }

    @Test
    public void findAllNewsByQueryTest() {
        List<News> returnedList = new ArrayList<News>(Arrays.asList(actualNews));
        when(newsRepository.findAllNewsAndSortByQuery(any(String.class))).thenReturn(returnedList);
        when(tagRepository.findBy(any(News.class))).thenReturn(new ArrayList<Tag>());


        List<NewsDTO> actualNewsDTOes = newsService.findAllNewsByQuery(testQuery);

        Assert.assertEquals(expectedNews, actualNewsDTOes.get(0));
        Assert.assertEquals(1, actualNewsDTOes.size());
        verify(newsRepository, times(1)).findAllNewsAndSortByQuery(anyString());
        verify(tagRepository, times(1)).findBy(any(News.class));
    }
}
