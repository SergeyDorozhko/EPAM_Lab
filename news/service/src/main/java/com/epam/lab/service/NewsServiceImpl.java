package com.epam.lab.service;

import com.epam.lab.dto.Mapper.NewsMapper;
import com.epam.lab.dto.NewsDTO;
import com.epam.lab.dto.SearchCriteria;
import com.epam.lab.model.News;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.AuthorRepository;
import com.epam.lab.repository.NewsRepository;
import com.epam.lab.repository.TagRepository;
import com.epam.lab.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@Transactional
public class NewsServiceImpl implements NewsService {
    private NewsMapper mapper;
    private NewsRepository newsRepository;
    private AuthorRepository authorRepository;
    private TagRepository tagRepository;

    @Autowired
    public NewsServiceImpl(NewsMapper mapper, NewsRepository newsRepository, AuthorRepository authorRepository, TagRepository tagRepository) {
        this.mapper = mapper;
        this.newsRepository = newsRepository;
        this.authorRepository = authorRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public NewsDTO create(NewsDTO bean) {
        checkNews(bean);

        News news = mapper.toBean(bean);
        news.setCreationDate(LocalDate.now());
        news.setModificationDate(LocalDate.now());
        checkAndCreateAuthorIfNew(news);

        newsRepository.create(news);

        boolean hasTags = news.getListOfTags() != null && news.getListOfTags().size() > 0;
        if (hasTags) {
            for (int i = 0; i < news.getListOfTags().size(); i++) {
                i = checkAndCreateTagIfNew(news, i);
            }
        }

        if (news.getAuthor() != null) {
            newsRepository.linkAuthorWithNews(news.getAuthor().getId(), news.getId());
        }
        makeUniqueListOfTags(news);
        for (Tag tag : news.getListOfTags()) {
            tagRepository.linkTagWithNews(tag.getId(), news.getId());
        }
        return mapper.toDTO(news);
    }


    private void checkNews(NewsDTO newsDTO) {
        boolean isValidNews = newsDTO.getTitle() != null
                && newsDTO.getShortText() != null
                && newsDTO.getFullText() != null;
        if (!isValidNews) {
            throw new ServiceException("invalid news");
        }
    }

    private void checkAndCreateAuthorIfNew(News news) {
        boolean hasAuthorWithId = news.getAuthor() != null && news.getAuthor().getId() != 0;
        boolean hasAuthorWithoutId = news.getAuthor() != null && news.getAuthor().getId() == 0;

        if (hasAuthorWithId) {
            authorRepository.findBy(news.getAuthor());
        } else if (hasAuthorWithoutId) {
            news.setAuthor(authorRepository.create(news.getAuthor()));
        }
    }

    private int checkAndCreateTagIfNew(News news, int tagIndex) {
        try {
            if (news.getListOfTags().get(tagIndex).getId() != 0) {
                tagRepository.findBy(news.getListOfTags().get(tagIndex));
            } else {
                news.getListOfTags().set(tagIndex,
                        tagRepository.findBy(news.getListOfTags().get(tagIndex).getName()));
            }
        } catch (Exception ex) {
            if (news.getListOfTags().get(tagIndex).getId() != 0) {
                news.getListOfTags().remove(tagIndex);
                tagIndex--;
            } else {
                tagRepository.create(news.getListOfTags().get(tagIndex));
            }
        }
        return tagIndex;
    }

    private void makeUniqueListOfTags(News news) {
        Set<Tag> tagList = new HashSet<>(news.getListOfTags());
        List<Tag> list = new ArrayList<>(tagList);
        news.setListOfTags(list);
    }

    @Override
    public boolean delete(long id) {
        return newsRepository.delete(id);
    }

    @Override
    public NewsDTO update(NewsDTO bean) {
        News news = mapper.toBean(bean);
        if (!checkAuthorAndCreateIfNeed(news)) {
            throw new ServiceException("Error date");
        }
        news.setModificationDate(LocalDate.now());
        newsRepository.update(news);

        boolean hasTags = news.getListOfTags() != null && news.getListOfTags().size() > 0;
        if (hasTags) {
            for (int i = 0; i < news.getListOfTags().size(); i++) {
                i = checkAndCreateTagIfNew(news, i);
            }
            tagRepository.deleteTagNewsLinks(news.getId());
            makeUniqueListOfTags(news);
            for (Tag tag : news.getListOfTags()) {
                tagRepository.linkTagWithNews(tag.getId(), news.getId());
            }
        }

        return mapper.toDTO(news);
    }

    private boolean checkAuthorAndCreateIfNeed(News news) {
        boolean flag = false;
        Long authorId = null;
        try {
            authorId = newsRepository.findAuthorIdByNewsId(news.getId());
        } catch (Exception ex) {
            //TODO logger.
            System.out.println("Add author to news (update action)");
        }
        if (authorId != null && news.getAuthor() != null && authorId == news.getAuthor().getId()) {
            flag = true;
        } else if (authorId == null && news.getAuthor() != null) {
            authorRepository.findBy(news.getAuthor());
            newsRepository.linkAuthorWithNews(news.getAuthor().getId(), news.getId());
            flag = true;
        } else if (authorId == null && news.getAuthor() == null) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }


    @Override
    public NewsDTO findById(long id) {
        News news = newsRepository.findBy(id);
        if (news.getAuthor() != null) {
            news.setAuthor(authorRepository.findBy(news.getAuthor().getId()));
        }
        news.setListOfTags(tagRepository.findBy(news));
        return mapper.toDTO(news);
    }

    @Override
    public long countAllNews() {
        return newsRepository.countAllNews();
    }

    @Override
    public List<NewsDTO> findAllNewsByQuery(SearchCriteria searchCriteria) {

        List<News> newsList = newsRepository.findAllNewsAndSortByQuery(" ORDER BY modification_date, short_text;");
        List<NewsDTO> newsDTOList = new ArrayList<>();
        for (News news : newsList) {
            news.setListOfTags(tagRepository.findBy(news));
            newsDTOList.add(mapper.toDTO(news));
        }
        return newsDTOList;
    }
}
