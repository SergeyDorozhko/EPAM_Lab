package com.epam.lab.service.impl;

import com.epam.lab.dto.AuthorDTO;
import com.epam.lab.dto.mapper.NewsMapper;
import com.epam.lab.dto.NewsDTO;
import com.epam.lab.dto.SearchCriteriaDTO;
import com.epam.lab.dto.TagDTO;
import com.epam.lab.dto.mapper.SearchCriteriaMapper;
import com.epam.lab.model.Author;
import com.epam.lab.model.News;
import com.epam.lab.model.SearchCriteria;
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

    private static final String AUTHOR_NAME = "authorName";
    private static final String AUTHOR_SURNAME = "authorSurname";
    private static final String NEWS_TITLE = "title";
    private static final String NEWS_SHORT_TEXT = "shortText";
    private static final String NEWS_FULL_TEXT = "fullText";
    private static final String AUTHOR_NAME_COLUMN = "author_name";
    private static final String EMPTY_FIELD = "";
    private static final String TAG_NAME_NEWS = "news";
    private static final String TAG_NAME_SPORT = "sport";
    private SearchCriteriaDTO testQuery;
    private NewsDTO expectedNews;
    private News actualNews;


    private NewsRepository newsRepository;
    private TagRepository tagRepository;

    private NewsService newsService;

    public SearchCriteriaTest(SearchCriteriaDTO testQuery, News actualNews, NewsDTO expectedNews) {
        this.testQuery = testQuery;
        this.actualNews = actualNews;
        this.expectedNews = expectedNews;
    }


    @Before
    public void init() {
        AuthorRepository authorRepository = Mockito.mock(AuthorRepositoryImpl.class);
        newsRepository = Mockito.mock(NewsRepositoryImpl.class);
        tagRepository = Mockito.mock(TagRepositoryImpl.class);
        NewsMapper newsMapper = new NewsMapper(new ModelMapper());

        newsService = new NewsServiceImpl(newsMapper,new SearchCriteriaMapper(new ModelMapper()), newsRepository, authorRepository, tagRepository);


    }


    @Parameterized.Parameters(name = "{index}: Test with {0}, {1}, result = {2}")
    public static Collection<Object[]> dataForSearch() {
        return Arrays.asList(new Object[][]{
                {new SearchCriteriaDTO(AUTHOR_NAME, AUTHOR_SURNAME,
                        new LinkedHashSet<String>(), new LinkedHashSet<String>(), false),

                        new News(1, NEWS_TITLE, NEWS_SHORT_TEXT, NEWS_FULL_TEXT,
                                LocalDate.now(), LocalDate.now(),
                                new Author(6, AUTHOR_NAME, AUTHOR_SURNAME), new ArrayList<Tag>()),

                        new NewsDTO(1, NEWS_TITLE, NEWS_SHORT_TEXT, NEWS_FULL_TEXT,
                                LocalDate.now(), LocalDate.now(),
                                new AuthorDTO(6, AUTHOR_NAME, AUTHOR_SURNAME), new ArrayList<TagDTO>())},


                {new SearchCriteriaDTO(AUTHOR_NAME, EMPTY_FIELD,
                        new LinkedHashSet<String>(), new LinkedHashSet<String>(), true),

                        new News(2, NEWS_TITLE, NEWS_SHORT_TEXT, NEWS_FULL_TEXT,
                                LocalDate.now(), LocalDate.now(),
                                new Author(5, AUTHOR_NAME, AUTHOR_SURNAME), new ArrayList<Tag>()),

                        new NewsDTO(2, NEWS_TITLE, NEWS_SHORT_TEXT, NEWS_FULL_TEXT,
                                LocalDate.now(), LocalDate.now(),
                                new AuthorDTO(5, AUTHOR_NAME, AUTHOR_SURNAME), new ArrayList<TagDTO>())},

                {new SearchCriteriaDTO(AUTHOR_NAME, EMPTY_FIELD,
                        new LinkedHashSet<String>(),
                        new LinkedHashSet<String>(Arrays.asList(AUTHOR_NAME_COLUMN)),
                        true),

                        new News(2, NEWS_TITLE, NEWS_SHORT_TEXT, NEWS_FULL_TEXT,
                                LocalDate.now(), LocalDate.now(),
                                new Author(5, AUTHOR_NAME, AUTHOR_SURNAME), new ArrayList<Tag>()),

                        new NewsDTO(2, NEWS_TITLE, NEWS_SHORT_TEXT, NEWS_FULL_TEXT,
                                LocalDate.now(), LocalDate.now(),
                                new AuthorDTO(5, AUTHOR_NAME, AUTHOR_SURNAME), new ArrayList<TagDTO>())},

                {new SearchCriteriaDTO(AUTHOR_NAME, EMPTY_FIELD,
                        new LinkedHashSet<String>(),
                        new LinkedHashSet<String>(Arrays.asList(AUTHOR_NAME_COLUMN, NEWS_TITLE)),
                        true),

                        new News(2, NEWS_TITLE, NEWS_SHORT_TEXT, NEWS_FULL_TEXT,
                                LocalDate.now(), LocalDate.now(),
                                new Author(5, AUTHOR_NAME, AUTHOR_SURNAME), new ArrayList<Tag>()),

                        new NewsDTO(2, NEWS_TITLE, NEWS_SHORT_TEXT, NEWS_FULL_TEXT,
                                LocalDate.now(), LocalDate.now(),
                                new AuthorDTO(5, AUTHOR_NAME, AUTHOR_SURNAME), new ArrayList<TagDTO>())},

                {new SearchCriteriaDTO(AUTHOR_NAME, EMPTY_FIELD,
                        new LinkedHashSet<String>(Arrays.asList(TAG_NAME_NEWS, TAG_NAME_SPORT)),
                        new LinkedHashSet<String>(),
                        true),

                        new News(3, NEWS_TITLE, NEWS_SHORT_TEXT, NEWS_FULL_TEXT,
                                LocalDate.now(), LocalDate.now(),
                                new Author(5, AUTHOR_NAME, AUTHOR_SURNAME),
                                new ArrayList<Tag>(Arrays.asList(new Tag(1, TAG_NAME_NEWS), new Tag(2, TAG_NAME_SPORT)))),

                        new NewsDTO(3, NEWS_TITLE, NEWS_SHORT_TEXT, NEWS_FULL_TEXT,
                                LocalDate.now(), LocalDate.now(),
                                new AuthorDTO(5, AUTHOR_NAME, AUTHOR_SURNAME), new ArrayList<TagDTO>())}

        });
    }

    @Test
    public void findAllNewsByQueryTest() {
        List<News> returnedList = new ArrayList<>(Arrays.asList(actualNews));
        when(newsRepository.findAllNewsAndSortByQuery(any(SearchCriteria.class))).thenReturn(returnedList);
        when(tagRepository.findBy(any(News.class))).thenReturn(new ArrayList<Tag>());

//TODO update tests
        List<NewsDTO> actualNewsDTOes = newsService.findAllNewsByQuery(testQuery).getItems();

        Assert.assertEquals(expectedNews, actualNewsDTOes.get(0));
        Assert.assertEquals(1, actualNewsDTOes.size());
        verify(newsRepository, times(1)).findAllNewsAndSortByQuery(any(SearchCriteria.class));
        verify(tagRepository, times(1)).findBy(any(News.class));
    }
}
