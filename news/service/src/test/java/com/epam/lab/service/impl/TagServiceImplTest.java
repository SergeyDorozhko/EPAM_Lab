package com.epam.lab.service.impl;

import com.epam.lab.dto.mapper.TagMapper;
import com.epam.lab.dto.TagDTO;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.TagRepository;
import com.epam.lab.repository.impl.TagRepositoryImpl;
import com.epam.lab.exception.ServiceException;
import com.epam.lab.service.TagService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TagServiceImplTest {

    private static final String TEST_TAG = "TestTag";
    private TagRepository tagRepository;

    private TagService tagService;
    private TagDTO expextedTag;
    private Tag tag;

    @Before
    public void init() {

        this.tagRepository = Mockito.mock(TagRepositoryImpl.class);
        TagMapper tagMapper = new TagMapper(new ModelMapper());
        this.tagService = new TagServiceImpl(tagMapper, tagRepository);

        tag = new Tag();
        tag.setName(TEST_TAG);

        expextedTag = new TagDTO();
        expextedTag.setName(TEST_TAG);
        when(tagRepository.create(any(Tag.class))).thenReturn(tag);

        when(tagRepository.delete(110L)).thenReturn(true);

        when(tagRepository.update(any(Tag.class))).thenReturn(tag);

    }


    @Test
    public void createTagPositiveTest() {

        when(tagRepository.findBy(TEST_TAG)).thenThrow(EmptyResultDataAccessException.class);

        TagDTO tagDTOforTest = new TagDTO();
        tagDTOforTest.setName(TEST_TAG);

        Assert.assertEquals(expextedTag, tagService.create(tagDTOforTest));
        verify(tagRepository, Mockito.times(1)).create(tag);
    }

    @Test(expected = ServiceException.class)
    public void createTagNegotiveTest() {
        when(tagRepository.findBy(any(String.class))).thenReturn(new Tag());
        tagService.create(new TagDTO());
    }


    @Test
    public void testTagDelete() {
        Assert.assertEquals(true, tagService.delete(110L));
        Assert.assertEquals(false, tagService.delete(11L));

        verify(tagRepository, Mockito.times(1)).delete(110L);
        verify(tagRepository, Mockito.times(1)).delete(11L);


    }


    @Test
    public void updateTagPositiveTest() {
        doThrow(EmptyResultDataAccessException.class).when(tagRepository).findBy(TEST_TAG);

        TagDTO tagDTOforTest = new TagDTO();
        tagDTOforTest.setId(1);
        tagDTOforTest.setName(TEST_TAG);

        Assert.assertEquals(expextedTag, tagService.update(tagDTOforTest));
        verify(tagRepository, Mockito.times(1)).update(tag);
    }

    @Test(expected = ServiceException.class)
    public void updateTagNegotiveTest() {
        when(tagRepository.findBy(any(String.class))).thenReturn(new Tag());
        tagService.update(new TagDTO());
    }

    @Test
    public void findTagByIdPositiveTest(){
        when(tagRepository.findBy(anyLong())).thenReturn(tag);
        Assert.assertEquals(expextedTag, tagService.findById(anyLong()));
        verify(tagRepository, times(1)).findBy(anyLong());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void findTagByIdNegativeTest(){
        when(tagRepository.findBy(anyLong())).thenThrow(EmptyResultDataAccessException.class);
        tagService.findById(anyLong());

    }
}
