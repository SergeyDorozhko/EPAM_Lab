package com.epam.lab.service;

import com.epam.lab.dto.AuthorDTO;
import com.epam.lab.dto.Mapper.NewsMapper;
import com.epam.lab.dto.NewsDTO;
import com.epam.lab.dto.TagDTO;
import com.epam.lab.model.Author;
import com.epam.lab.model.News;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.*;
import com.epam.lab.service.exception.ServiceException;
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

    private AuthorRepository authorRepository;
    private NewsRepository newsRepository;
    private TagRepository tagRepository;
    private NewsMapper newsMapper;

    private NewsService newsService;

    private NewsDTO newsDTO;
    private News news;

    @Before
    public void init() {
        authorRepository = Mockito.mock(AuthorRepositoryImpl.class);
        newsRepository = Mockito.mock(NewsRepositoryImpl.class);
        tagRepository = Mockito.mock(TagRepositoryImpl.class);
        newsMapper = new NewsMapper(new ModelMapper());

        newsService = new NewsServiceImpl(newsMapper, newsRepository, authorRepository, tagRepository);


    }

    @Test
    public void createNewsWithoutAuthorWithoutTagTest() {
        newsDTO = new NewsDTO();
        newsDTO.setTitle("Title");
        newsDTO.setShortText("ShortText");
        newsDTO.setFullText("FullText");
        newsDTO.setCreationDate(LocalDate.now());
        newsDTO.setModificationDate(LocalDate.now());

        news = new News();
        news.setTitle("Title");
        news.setShortText("ShortText");
        news.setFullText("FullText");

        when(newsRepository.create(any(News.class))).thenReturn(news);

        Assert.assertEquals(newsDTO, newsService.create(newsDTO));
        verify(authorRepository, times(0)).findBy(any(Author.class));
        verify(authorRepository, times(0)).create(any(Author.class));
        verify(newsRepository, times(1)).create(any(News.class));
        verify(tagRepository, times(0)).findBy(anyString());
        verify(tagRepository, times(0)).create(any(Tag.class));
        verify(newsRepository, times(0)).linkAuthorWithNews(anyLong(), anyLong());
        verify(tagRepository, times(0)).linkTagWithNews(anyLong(), anyLong());
    }


    @Test
    public void createNewsWithAuthorWithoutIdWithoutTagTest() {
        newsDTO = new NewsDTO();
        newsDTO.setTitle("Title");
        newsDTO.setShortText("ShortText");
        newsDTO.setFullText("FullText");
        newsDTO.setCreationDate(LocalDate.now());
        newsDTO.setModificationDate(LocalDate.now());
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("name");
        authorDTO.setSurname("surname");
        newsDTO.setAuthor(authorDTO);

        news = new News();
        news.setTitle("Title");
        news.setShortText("ShortText");
        news.setFullText("FullText");
        Author author = new Author();
        author.setName("name");
        author.setSurname("surname");
        news.setAuthor(author);
        when(newsRepository.create(any(News.class))).thenReturn(news);
        when(authorRepository.create(author)).thenReturn(author);


        Assert.assertEquals(newsDTO, newsService.create(newsDTO));
        verify(authorRepository, times(0)).findBy(any(Author.class));
        verify(authorRepository, times(1)).create(author);
        verify(newsRepository, times(1)).create(any(News.class));
        verify(tagRepository, times(0)).findBy(anyString());
        verify(tagRepository, times(0)).findBy(new Tag());
        verify(tagRepository, times(0)).create(any(Tag.class));
        verify(newsRepository, times(1)).linkAuthorWithNews(anyLong(), anyLong());
        verify(tagRepository, times(0)).linkTagWithNews(anyLong(), anyLong());
    }

    @Test
    public void createNewsWithAuthorWithoutTagTest() {
        newsDTO = new NewsDTO();
        newsDTO.setTitle("Title");
        newsDTO.setShortText("ShortText");
        newsDTO.setFullText("FullText");
        newsDTO.setCreationDate(LocalDate.now());
        newsDTO.setModificationDate(LocalDate.now());
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(2);
        authorDTO.setName("name");
        authorDTO.setSurname("surname");
        newsDTO.setAuthor(authorDTO);

        news = new News();
        news.setTitle("Title");
        news.setShortText("ShortText");
        news.setFullText("FullText");
        Author author = new Author();
        author.setId(2);
        author.setName("name");
        author.setSurname("surname");
        news.setAuthor(author);
        when(newsRepository.create(any(News.class))).thenReturn(news);
        when(authorRepository.findBy(news.getAuthor())).thenReturn(author);


        Assert.assertEquals(newsDTO, newsService.create(newsDTO));
        verify(authorRepository, times(1)).findBy(any(Author.class));
        verify(authorRepository, times(0)).create(author);
        verify(newsRepository, times(1)).create(any(News.class));
        verify(tagRepository, times(0)).findBy(anyString());
        verify(tagRepository, times(0)).findBy(new Tag());
        verify(tagRepository, times(0)).create(any(Tag.class));
        verify(newsRepository, times(1)).linkAuthorWithNews(anyLong(), anyLong());
        verify(tagRepository, times(0)).linkTagWithNews(anyLong(), anyLong());
    }


    @Test
    public void createNewsWithAuthorWithTagTest() {
        newsDTO = new NewsDTO();
        newsDTO.setTitle("Title");
        newsDTO.setShortText("ShortText");
        newsDTO.setFullText("FullText");
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(2);
        authorDTO.setName("name");
        authorDTO.setSurname("surname");
        newsDTO.setAuthor(authorDTO);
        TagDTO oneTagDTO = new TagDTO();
        oneTagDTO.setName("one_tag");
        TagDTO twoTagDTO = new TagDTO();
        twoTagDTO.setId(2);
        twoTagDTO.setName("two_tag");
        TagDTO threeTagDTO = new TagDTO();
        threeTagDTO.setId(5);
        threeTagDTO.setName("three");
        TagDTO fourTagDTO = new TagDTO();
        fourTagDTO.setName("four");
        List<TagDTO> tagsDTOList = new ArrayList<>();
        tagsDTOList.add(oneTagDTO);
        tagsDTOList.add(twoTagDTO);
        tagsDTOList.add(threeTagDTO);
        tagsDTOList.add(fourTagDTO);
        newsDTO.setListOfTags(tagsDTOList);

        news = new News();
        Author author = new Author();
        author.setId(2);
        author.setName("name");
        author.setSurname("surname");
        news.setAuthor(author);
        Tag oneTag = new Tag();
        oneTag.setName("one_tag");
        Tag twoTag = new Tag();
        twoTag.setId(2);
        twoTag.setName("two_tag");
        Tag threeTag = new Tag();
        threeTag.setId(5);
        threeTag.setName("three");
        Tag fourTag = new Tag();
        fourTag.setName("four");


        when(newsRepository.create(any(News.class))).thenReturn(news);
        when(authorRepository.findBy(news.getAuthor())).thenReturn(author);
        when(tagRepository.findBy(twoTag)).thenReturn(twoTag);
        when(tagRepository.findBy(threeTag)).thenThrow(EmptyResultDataAccessException.class);
        when(tagRepository.findBy("one_tag")).thenThrow(EmptyResultDataAccessException.class);
        when(tagRepository.findBy("four")).thenReturn(fourTag);

        Assert.assertEquals(newsDTO.getListOfTags().size() - 1, newsService.create(newsDTO).getListOfTags().size());
        verify(authorRepository, times(1)).findBy(any(Author.class));
        verify(authorRepository, times(0)).create(author);
        verify(newsRepository, times(1)).create(any(News.class));
        verify(tagRepository, times(2)).findBy(anyString()); // 1 tag one; 2 tag four;
        verify(tagRepository, times(1)).findBy(twoTag);//1 tag two;
        verify(tagRepository, times(1)).findBy(threeTag);//1 tag three;
        verify(tagRepository, times(1)).create(any(Tag.class));//1 tag one;
        verify(newsRepository, times(1)).linkAuthorWithNews(anyLong(), anyLong());
        verify(tagRepository, times(3)).linkTagWithNews(anyLong(), anyLong());
    }

    @Test(expected = ServiceException.class)
    public void createNewsNegativeByContentTest(){
        newsDTO = new NewsDTO();
        newsService.create(newsDTO);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void createNewsNegativeByAuthorTest(){
        newsDTO = new NewsDTO();
        newsDTO.setTitle("Title");
        newsDTO.setShortText("ShortText");
        newsDTO.setFullText("FullText");
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(2);
        authorDTO.setName("name");
        authorDTO.setSurname("surname");
        newsDTO.setAuthor(authorDTO);

        Author author = new Author();
        author.setId(2);
        author.setName("name");
        author.setSurname("surname");
        when(authorRepository.findBy(author)).thenThrow(EmptyResultDataAccessException.class);

        newsService.create(newsDTO);
    }

    @Test
    public void deleteNewsTest(){
        when(newsRepository.delete(1L)).thenReturn(true);
        when(newsRepository.delete(2L)).thenReturn(false);

        Assert.assertEquals(true, newsService.delete(1));
        Assert.assertEquals(false, newsService.delete(2));
    }


    @Test
    public void findNewsWithAuthorByIdTest(){
        news = new News();
        news.setTitle("title");
        news.setShortText("ShortText");
        news.setFullText("FullText");
        Author author = new Author();
        author.setId(1);
        news.setAuthor(author);

        Author authorFromData = new Author();
        authorFromData.setId(1);
        authorFromData.setName("author");
        authorFromData.setSurname("authorSurname");

        List<Tag> listTag = new ArrayList<>();
        Tag oneTag = new Tag();
        oneTag.setId(1);
        oneTag.setName("one_tag");
        Tag twoTag = new Tag();
        twoTag.setId(2);
        twoTag.setName("two_tag");
        Tag threeTag = new Tag();
        threeTag.setId(5);
        threeTag.setName("three");
        listTag.add(oneTag);
        listTag.add(twoTag);
        listTag.add(threeTag);

        when(newsRepository.findBy(any(Long.class))).thenReturn(news);
        when(authorRepository.findBy(any(Long.class))).thenReturn(authorFromData);
        when(tagRepository.findBy(any(News.class))).thenReturn(listTag);

        newsDTO = new NewsDTO();
        newsDTO.setTitle("title");
        newsDTO.setShortText("ShortText");
        newsDTO.setFullText("FullText");
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(1);
        authorDTO.setName("author");
        authorDTO.setSurname("authorSurname");
        newsDTO.setAuthor(authorDTO);
        TagDTO oneTagDTO = new TagDTO();
        oneTagDTO.setId(1);
        oneTagDTO.setName("one_tag");
        TagDTO twoTagDTO = new TagDTO();
        twoTagDTO.setId(2);
        twoTagDTO.setName("two_tag");
        TagDTO threeTagDTO = new TagDTO();
        threeTagDTO.setId(5);
        threeTagDTO.setName("three");
        List<TagDTO> tagsDTOList = new ArrayList<>();
        tagsDTOList.add(oneTagDTO);
        tagsDTOList.add(twoTagDTO);
        tagsDTOList.add(threeTagDTO);
        newsDTO.setListOfTags(tagsDTOList);

        Assert.assertEquals(newsDTO, newsService.findById(anyLong()));

        verify(newsRepository, times(1)).findBy(anyLong());
        verify(authorRepository, times(1)).findBy(1);
        verify(tagRepository, times(1)).findBy(news);
    }

    @Test
    public void findNewsWithoutAuthorByIdTest(){
        news = new News();
        news.setTitle("title");
        news.setShortText("ShortText");
        news.setFullText("FullText");

        List<Tag> listTag = new ArrayList<>();
        Tag oneTag = new Tag();
        oneTag.setId(1);
        oneTag.setName("one_tag");
        Tag twoTag = new Tag();
        twoTag.setId(2);
        twoTag.setName("two_tag");
        Tag threeTag = new Tag();
        threeTag.setId(5);
        threeTag.setName("three");
        listTag.add(oneTag);
        listTag.add(twoTag);
        listTag.add(threeTag);

        when(newsRepository.findBy(any(Long.class))).thenReturn(news);
        when(authorRepository.findBy(any(Long.class))).thenReturn(new Author());
        when(tagRepository.findBy(any(News.class))).thenReturn(listTag);

        newsDTO = new NewsDTO();
        newsDTO.setTitle("title");
        newsDTO.setShortText("ShortText");
        newsDTO.setFullText("FullText");

        TagDTO oneTagDTO = new TagDTO();
        oneTagDTO.setId(1);
        oneTagDTO.setName("one_tag");
        TagDTO twoTagDTO = new TagDTO();
        twoTagDTO.setId(2);
        twoTagDTO.setName("two_tag");
        TagDTO threeTagDTO = new TagDTO();
        threeTagDTO.setId(5);
        threeTagDTO.setName("three");
        List<TagDTO> tagsDTOList = new ArrayList<>();
        tagsDTOList.add(oneTagDTO);
        tagsDTOList.add(twoTagDTO);
        tagsDTOList.add(threeTagDTO);
        newsDTO.setListOfTags(tagsDTOList);

        Assert.assertEquals(newsDTO, newsService.findById(anyLong()));

        verify(newsRepository, times(1)).findBy(anyLong());
        verify(authorRepository, times(0)).findBy(any(Author.class));
        verify(tagRepository, times(1)).findBy(news);
    }

    @Test
    public void countAllNewsTest(){
        when(newsRepository.countAllNews()).thenReturn(5L);
        Assert.assertEquals(5, newsService.countAllNews());
        verify(newsRepository, times(1)).countAllNews();
    }
}
