package com.epam.lab.service;

import com.epam.lab.dto.Mapper.TagMapper;
import com.epam.lab.dto.TagDTO;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.TagRepository;
import com.epam.lab.repository.TagRepositoryImpl;
import com.epam.lab.service.exception.ServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.mockito.ArgumentMatchers.any;

public class TagServiceImplTest {

    private TagRepository tagRepository;
    private TagMapper tagMapper;

    private TagService tagService;
    private TagDTO expextedTag;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        this.tagRepository = Mockito.mock(TagRepositoryImpl.class);
        this.tagMapper = new TagMapper(new ModelMapper());
        this.tagService = new TagServiceImpl(tagMapper, tagRepository);

        Tag tag = new Tag();
        tag.setName("TestTag");

        expextedTag = new TagDTO();
        expextedTag.setName("TestTag");
        Mockito.when(tagRepository.create(any(Tag.class))).thenReturn(tag);

        Mockito.when(tagRepository.delete(110L)).thenReturn(true);

        Mockito.when(tagRepository.update(any(Tag.class))).thenReturn(tag);

    }


    //TODO Exception
//    @Test
//    public void createTagPositiveTest() {
//
//        Mockito.when(tagRepository.findBy(any(String.class))).thenThrow(EmptyResultDataAccessException.class);
//
//        Assert.assertEquals(expextedTag, tagService.create(new TagDTO()));
//        Mockito.verify(tagRepository, Mockito.times(1)).create(new Tag());
//    }

    @Test(expected = ServiceException.class)
    public void createTagNegotiveTest() {
        Mockito.when(tagRepository.findBy(any(String.class))).thenReturn(new Tag());
        tagService.create(new TagDTO());
    }


    @Test
    public void testTagDelete() {
        Assert.assertEquals(true, tagService.delete(110L));
        Assert.assertEquals(false, tagService.delete(11L));

        Mockito.verify(tagRepository, Mockito.times(1)).delete(110L);
        Mockito.verify(tagRepository, Mockito.times(1)).delete(11L);


    }


    //TODO EXCEPTION
//    @Test
//    public void updateTagPositiveTest() {
//        Mockito.doThrow(new EmptyResultDataAccessException(5)).when(tagRepository).findBy(any(String.class));
//        Assert.assertEquals(expextedTag, tagService.update(new TagDTO()));
//        Mockito.verify(tagRepository, Mockito.times(1)).create(new Tag());
//    }

    @Test(expected = ServiceException.class)
    public void updateTagNegotiveTest() {
        Mockito.when(tagRepository.findBy(any(String.class))).thenReturn(new Tag());
        tagService.update(new TagDTO());
    }
}
