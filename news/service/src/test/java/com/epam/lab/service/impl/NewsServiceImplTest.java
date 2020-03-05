package com.epam.lab.service.impl;

import com.epam.lab.dto.AuthorDTO;
import com.epam.lab.dto.mapper.NewsMapper;
import com.epam.lab.dto.NewsDTO;
import com.epam.lab.dto.TagDTO;
import com.epam.lab.dto.mapper.SearchCriteriaMapper;
import com.epam.lab.exception.InvalidAuthorException;
import com.epam.lab.exception.ServiceException;
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
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class NewsServiceImplTest {

    private static final String NEWS_TITLE = "Title";
    private static final String NEWS_SHORT_TEXT = "ShortText";
    private static final String NEWS_FULL_TEXT = "FullText";
    private static final String AUTHOR_NAME = "name";
    private static final String AUTHOR_SURNAME = "surname";
    private static final String TAG_NAME_ONE = "one_tag";
    private static final String TAG_NAME_TWO = "two_tag";
    private static final String TAG_NAME_THREE = "three";
    private static final String TAG_NAME_FOUR = "four";
    private AuthorRepository authorRepository;
    private NewsRepository newsRepository;
    private TagRepository tagRepository;

    private NewsService newsService;

    private NewsDTO newsDTO;
    private News news;


    @Before
    public void init() {
        authorRepository = Mockito.mock(AuthorRepositoryImpl.class);
        newsRepository = Mockito.mock(NewsRepositoryImpl.class);
        tagRepository = Mockito.mock(TagRepositoryImpl.class);
        NewsMapper newsMapper = new NewsMapper(new ModelMapper());

        newsService = new NewsServiceImpl(newsMapper,new SearchCriteriaMapper(new ModelMapper()), newsRepository, authorRepository, tagRepository);


    }

    @Test
    public void createNewsWithoutAuthorWithoutTagTest() {
        newsDTO = new NewsDTO();
        newsDTO.setTitle(NEWS_TITLE);
        newsDTO.setShortText(NEWS_SHORT_TEXT);
        newsDTO.setFullText(NEWS_FULL_TEXT);
        newsDTO.setCreationDate(LocalDate.now());
        newsDTO.setModificationDate(LocalDate.now());

        news = new News();
        news.setTitle(NEWS_TITLE);
        news.setShortText(NEWS_SHORT_TEXT);
        news.setFullText(NEWS_FULL_TEXT);

        when(newsRepository.create(any(News.class))).thenReturn(news);

        Assert.assertEquals(newsDTO, newsService.create(newsDTO));
        verify(authorRepository, never()).findBy(any(Author.class));
        verify(authorRepository, never()).create(any(Author.class));
        verify(newsRepository).create(any(News.class));
        verify(tagRepository, never()).findBy(anyString());
        verify(tagRepository, never()).create(any(Tag.class));
        verify(newsRepository, never()).linkAuthorWithNews(anyLong(), anyLong());
        verify(tagRepository, never()).linkTagWithNews(anyLong(), anyLong());
    }


    @Test
    public void createNewsWithAuthorWithoutIdWithoutTagTest() {
        newsDTO = new NewsDTO();
        newsDTO.setTitle(NEWS_TITLE);
        newsDTO.setShortText(NEWS_SHORT_TEXT);
        newsDTO.setFullText(NEWS_FULL_TEXT);
        newsDTO.setCreationDate(LocalDate.now());
        newsDTO.setModificationDate(LocalDate.now());
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName(AUTHOR_NAME);
        authorDTO.setSurname(AUTHOR_SURNAME);
        newsDTO.setAuthor(authorDTO);

        news = new News();
        news.setTitle(NEWS_TITLE);
        news.setShortText(NEWS_SHORT_TEXT);
        news.setFullText(NEWS_FULL_TEXT);
        Author author = new Author();
        author.setName(AUTHOR_NAME);
        author.setSurname(AUTHOR_SURNAME);
        news.setAuthor(author);
        when(newsRepository.create(any(News.class))).thenReturn(news);
        when(authorRepository.create(author)).thenReturn(author);


        Assert.assertEquals(newsDTO, newsService.create(newsDTO));
        verify(authorRepository, never()).findBy(any(Author.class));
        verify(authorRepository).create(author);
        verify(newsRepository).create(any(News.class));
        verify(tagRepository, never()).findBy(anyString());
        verify(tagRepository, never()).findBy(new Tag());
        verify(tagRepository, never()).create(any(Tag.class));
        verify(newsRepository).linkAuthorWithNews(anyLong(), anyLong());
        verify(tagRepository, never()).linkTagWithNews(anyLong(), anyLong());
    }

    @Test
    public void createNewsWithAuthorWithoutTagTest() {
        newsDTO = new NewsDTO();
        newsDTO.setTitle(NEWS_TITLE);
        newsDTO.setShortText(NEWS_SHORT_TEXT);
        newsDTO.setFullText(NEWS_FULL_TEXT);
        newsDTO.setCreationDate(LocalDate.now());
        newsDTO.setModificationDate(LocalDate.now());
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(2);
        authorDTO.setName(AUTHOR_NAME);
        authorDTO.setSurname(AUTHOR_SURNAME);
        newsDTO.setAuthor(authorDTO);

        news = new News();
        news.setTitle(NEWS_TITLE);
        news.setShortText(NEWS_SHORT_TEXT);
        news.setFullText(NEWS_FULL_TEXT);
        Author author = new Author();
        author.setId(2);
        author.setName(AUTHOR_NAME);
        author.setSurname(AUTHOR_SURNAME);
        news.setAuthor(author);
        when(newsRepository.create(any(News.class))).thenReturn(news);
        when(authorRepository.findBy(news.getAuthor())).thenReturn(author);


        Assert.assertEquals(newsDTO, newsService.create(newsDTO));
        verify(authorRepository).findBy(any(Author.class));
        verify(authorRepository, never()).create(author);
        verify(newsRepository).create(any(News.class));
        verify(tagRepository, never()).findBy(anyString());
        verify(tagRepository, never()).findBy(new Tag());
        verify(tagRepository, never()).create(any(Tag.class));
        verify(newsRepository).linkAuthorWithNews(anyLong(), anyLong());
        verify(tagRepository, never()).linkTagWithNews(anyLong(), anyLong());
    }


    @Test
    public void createNewsWithAuthorWithTagTest() {
        newsDTO = new NewsDTO();
        newsDTO.setTitle(NEWS_TITLE);
        newsDTO.setShortText(NEWS_SHORT_TEXT);
        newsDTO.setFullText(NEWS_FULL_TEXT);
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(2);
        authorDTO.setName(AUTHOR_NAME);
        authorDTO.setSurname(AUTHOR_SURNAME);
        newsDTO.setAuthor(authorDTO);
        TagDTO oneTagDTO = new TagDTO();
        oneTagDTO.setName(TAG_NAME_ONE);
        TagDTO twoTagDTO = new TagDTO();
        twoTagDTO.setId(2);
        twoTagDTO.setName(TAG_NAME_TWO);
        TagDTO threeTagDTO = new TagDTO();
        threeTagDTO.setId(5);
        threeTagDTO.setName(TAG_NAME_THREE);
        TagDTO fourTagDTO = new TagDTO();
        fourTagDTO.setName(TAG_NAME_FOUR);
        List<TagDTO> tagsDTOList = new ArrayList<>();
        tagsDTOList.add(oneTagDTO);
        tagsDTOList.add(twoTagDTO);
        tagsDTOList.add(threeTagDTO);
        tagsDTOList.add(fourTagDTO);
        newsDTO.setTags(tagsDTOList);

        news = new News();
        Author author = new Author();
        author.setId(2);
        author.setName(AUTHOR_NAME);
        author.setSurname(AUTHOR_SURNAME);
        news.setAuthor(author);
        Tag oneTag = new Tag();
        oneTag.setName(TAG_NAME_ONE);
        Tag twoTag = new Tag();
        twoTag.setId(2);
        twoTag.setName(TAG_NAME_TWO);
        Tag threeTag = new Tag();
        threeTag.setId(5);
        threeTag.setName(TAG_NAME_THREE);
        Tag fourTag = new Tag();
        fourTag.setName(TAG_NAME_FOUR);


        when(newsRepository.create(any(News.class))).thenReturn(news);
        when(authorRepository.findBy(news.getAuthor())).thenReturn(author);
        when(tagRepository.findBy(twoTag)).thenReturn(twoTag);
        when(tagRepository.findBy(threeTag)).thenThrow(EmptyResultDataAccessException.class);
        when(tagRepository.findBy(TAG_NAME_ONE)).thenThrow(EmptyResultDataAccessException.class);
        when(tagRepository.findBy(TAG_NAME_FOUR)).thenReturn(fourTag);

        Assert.assertEquals(newsDTO.getTags().size() - 1L,
                newsService.create(newsDTO).getTags().size());
        verify(authorRepository).findBy(any(Author.class));
        verify(authorRepository, never()).create(author);
        verify(newsRepository).create(any(News.class));
        verify(tagRepository, times(2)).findBy(anyString()); // 1 tag one, 2 tag four
        verify(tagRepository).findBy(twoTag);//1 tag two
        verify(tagRepository).findBy(threeTag);//1 tag three
        verify(tagRepository).create(any(Tag.class));//1 tag one
        verify(newsRepository).linkAuthorWithNews(anyLong(), anyLong());
        verify(tagRepository, times(3)).linkTagWithNews(anyLong(), anyLong());
    }



    @Test(expected = InvalidAuthorException.class)
    public void createNewsNegativeByAuthorTest() {
        newsDTO = new NewsDTO();
        newsDTO.setTitle(NEWS_TITLE);
        newsDTO.setShortText(NEWS_SHORT_TEXT);
        newsDTO.setFullText(NEWS_FULL_TEXT);
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(2);
        authorDTO.setName(AUTHOR_NAME);
        authorDTO.setSurname(AUTHOR_SURNAME);
        newsDTO.setAuthor(authorDTO);

        Author author = new Author();
        author.setId(2);
        author.setName(AUTHOR_NAME);
        author.setSurname(AUTHOR_SURNAME);
        when(authorRepository.findBy(author)).thenThrow(EmptyResultDataAccessException.class);

        newsService.create(newsDTO);
    }

    @Test
    public void deleteNewsTest() {
        when(newsRepository.delete(1L)).thenReturn(true);
        when(newsRepository.delete(2L)).thenReturn(false);

        Assert.assertEquals(true, newsService.delete(1));
        Assert.assertEquals(false, newsService.delete(2));
    }


    @Test
    public void findNewsWithAuthorByIdTest() {
        news = new News();
        news.setTitle(NEWS_TITLE);
        news.setShortText(NEWS_SHORT_TEXT);
        news.setFullText(NEWS_FULL_TEXT);
        Author author = new Author();
        author.setId(1);
        news.setAuthor(author);

        Author authorFromData = new Author();
        authorFromData.setId(1);
        authorFromData.setName(AUTHOR_NAME);
        authorFromData.setSurname(AUTHOR_SURNAME);

        List<Tag> listTag = new ArrayList<>();
        Tag oneTag = new Tag();
        oneTag.setId(1);
        oneTag.setName(TAG_NAME_ONE);
        Tag twoTag = new Tag();
        twoTag.setId(2);
        twoTag.setName(TAG_NAME_TWO);
        Tag threeTag = new Tag();
        threeTag.setId(5);
        threeTag.setName(TAG_NAME_THREE);
        listTag.add(oneTag);
        listTag.add(twoTag);
        listTag.add(threeTag);

        when(newsRepository.findBy(any(Long.class))).thenReturn(news);
        when(authorRepository.findBy(any(Long.class))).thenReturn(authorFromData);
        when(tagRepository.findBy(any(News.class))).thenReturn(listTag);

        newsDTO = new NewsDTO();
        newsDTO.setTitle(NEWS_TITLE);
        newsDTO.setShortText(NEWS_SHORT_TEXT);
        newsDTO.setFullText(NEWS_FULL_TEXT);
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(1);
        authorDTO.setName(AUTHOR_NAME);
        authorDTO.setSurname(AUTHOR_SURNAME);
        newsDTO.setAuthor(authorDTO);
        TagDTO oneTagDTO = new TagDTO();
        oneTagDTO.setId(1);
        oneTagDTO.setName(TAG_NAME_ONE);
        TagDTO twoTagDTO = new TagDTO();
        twoTagDTO.setId(2);
        twoTagDTO.setName(TAG_NAME_TWO);
        TagDTO threeTagDTO = new TagDTO();
        threeTagDTO.setId(5);
        threeTagDTO.setName(TAG_NAME_THREE);
        List<TagDTO> tagsDTOList = new ArrayList<>();
        tagsDTOList.add(oneTagDTO);
        tagsDTOList.add(twoTagDTO);
        tagsDTOList.add(threeTagDTO);
        newsDTO.setTags(tagsDTOList);

        Assert.assertEquals(newsDTO, newsService.findById(anyLong()));

        verify(newsRepository).findBy(anyLong());
        verify(authorRepository).findBy(1);
        verify(tagRepository).findBy(news);
    }

    @Test
    public void findNewsWithoutAuthorByIdTest() {
        news = new News();
        news.setTitle(NEWS_TITLE);
        news.setShortText(NEWS_SHORT_TEXT);
        news.setFullText(NEWS_FULL_TEXT);

        List<Tag> listTag = new ArrayList<>();
        Tag oneTag = new Tag();
        oneTag.setId(1);
        oneTag.setName(TAG_NAME_ONE);
        Tag twoTag = new Tag();
        twoTag.setId(2);
        twoTag.setName(TAG_NAME_TWO);
        Tag threeTag = new Tag();
        threeTag.setId(5);
        threeTag.setName(TAG_NAME_THREE);
        listTag.add(oneTag);
        listTag.add(twoTag);
        listTag.add(threeTag);

        when(newsRepository.findBy(any(Long.class))).thenReturn(news);
        when(authorRepository.findBy(any(Long.class))).thenReturn(new Author());
        when(tagRepository.findBy(any(News.class))).thenReturn(listTag);

        newsDTO = new NewsDTO();
        newsDTO.setTitle(NEWS_TITLE);
        newsDTO.setShortText(NEWS_SHORT_TEXT);
        newsDTO.setFullText(NEWS_FULL_TEXT);

        TagDTO oneTagDTO = new TagDTO();
        oneTagDTO.setId(1);
        oneTagDTO.setName(TAG_NAME_ONE);
        TagDTO twoTagDTO = new TagDTO();
        twoTagDTO.setId(2);
        twoTagDTO.setName(TAG_NAME_TWO);
        TagDTO threeTagDTO = new TagDTO();
        threeTagDTO.setId(5);
        threeTagDTO.setName(TAG_NAME_THREE);
        List<TagDTO> tagsDTOList = new ArrayList<>();
        tagsDTOList.add(oneTagDTO);
        tagsDTOList.add(twoTagDTO);
        tagsDTOList.add(threeTagDTO);
        newsDTO.setTags(tagsDTOList);

        Assert.assertEquals(newsDTO, newsService.findById(anyLong()));

        verify(newsRepository).findBy(anyLong());
        verify(authorRepository, never()).findBy(any(Author.class));
        verify(tagRepository).findBy(news);
    }

    @Test
    public void countAllNewsTest() {
        when(newsRepository.countAllNews()).thenReturn(5L);
        Assert.assertEquals(5, newsService.countAllNews());
        verify(newsRepository).countAllNews();
    }


}
