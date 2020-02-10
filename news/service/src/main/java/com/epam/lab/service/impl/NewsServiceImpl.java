package com.epam.lab.service.impl;

import com.epam.lab.dto.Mapper.NewsMapper;
import com.epam.lab.dto.NewsDTO;
import com.epam.lab.dto.SearchCriteria;
import com.epam.lab.model.News;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.AuthorRepository;
import com.epam.lab.repository.NewsRepository;
import com.epam.lab.repository.TagRepository;
import com.epam.lab.exception.ServiceException;
import com.epam.lab.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * This method check news data (title, short_text and full_text can't by null), then transfer NewsDTO
     * to News object. Then check Author of news (if news without author all is OK, if news have an author without id
     * starting creation of new author and assign id of new author, if author has id we check does this author has the
     * same name and surname in database (if author has no matches throws runtime exception and method stopped)).
     *
     * If author data is valid setup to CreationDate and ModificationDate current date.
     * After this insert data of news to database and make link between author and news tables.
     *
     * If news hasn't tags this step skipped, otherwise start check tags:
     * 1) tad hasn't id - check is exist tag with the same name in database:
     * a)if exist insert id from database.
     * b)if not exist create new tag in database.
     * 2) tag has id:
     * a) in database tag with this id has the same name - tag correct.
     * b) in database tag with this id has the another name - tag incorrect so removed from list.
     * After this make link between tags and news tables.
     *
     * @param bean NewsDTO with title, short_text and full_text, it can have or not author and list of tags.
     * @return NewsDTO with all generated data.
     */
    @Override
    public NewsDTO create(NewsDTO bean) {
        checkNews(bean);

        News news = mapper.toBean(bean);

        checkAndCreateAuthorIfNew(news);

        news.setCreationDate(LocalDate.now());
        news.setModificationDate(LocalDate.now());

        newsRepository.create(news);

        if (news.getAuthor() != null) {
            newsRepository.linkAuthorWithNews(news.getAuthor().getId(), news.getId());
        }

        boolean hasTags = news.getListOfTags() != null && news.getListOfTags().size() > 0;
        if (hasTags) {
            for (int i = 0; i < news.getListOfTags().size(); i++) {
                i = checkAndCreateTagIfNew(news, i);
            }
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
        } catch (EmptyResultDataAccessException ex) {
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

    /**
     * This method check news data (title, short_text and full_text can't by null), then transfer NewsDTO
     * to News object.
     * Then check Author of news:
     * 1) if news in database hasn't author and this news came without author  all is OK.
     * 2) if news in database hasn't author and this news came with author without id, start creating new author and
     * adding this author to news.
     * 3) if news in database hasn't author and this news came with author with id, start check author and if it correct
     *  adding this author to news otherwise throws ServiceException.
     * 3) if news came without author but this news in database has another author throws ServiceException.
     * 4) if news came without author and this news in database has the same author all is OK.
     *
     * If author data is valid setup to ModificationDate current date.
     * After this insert data of news to database and make link between author and news tables.
     *
     * If news hasn't tags this step skipped, otherwise drop all links between news and tags and start check tags:
     * 1) tad hasn't id - check is exist tag with the same name in database:
     * a)if exist insert id from database.
     * b)if not exist create new tag in database.
     * 2) tag has id:
     * a) in database tag with this id has the same name - tag correct.
     * b) in database tag with this id has the another name - tag incorrect so removed from list.
     * After this make link between tags and news tables.
     *
     * @param bean NewsDTO with title, short_text and full_text, it can have or not author and list of tags.
     * @return NewsDTO with all generated data.
     */
    @Override
    public NewsDTO update(NewsDTO bean) {
        checkNews(bean);
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
        } catch (EmptyResultDataAccessException ex) {
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


    /**
     * This method try to find news by id,  if it is successful then take author of this news (if one is exist)
     * and find all tags of this news (if they exist). then transfer from news to NewsDTO.
     * @param id of news which need to find.
     * @return NewsDTO with all params if successful otherwise runtime exception.
     */
    @Override
    public NewsDTO findById(long id) {
        News news = newsRepository.findBy(id);
        if (news.getAuthor() != null && news.getAuthor().getId() != 0) {
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
        String query = makeQueryForSearch(searchCriteria);

        List<News> newsList = newsRepository.findAllNewsAndSortByQuery(query);
        List<NewsDTO> newsDTOList = new ArrayList<>();
        for (News news : newsList) {
            news.setListOfTags(tagRepository.findBy(news));
            newsDTOList.add(mapper.toDTO(news));
        }
        return newsDTOList;
    }

    private String makeQueryForSearch(SearchCriteria searchCriteria) {
        StringBuilder queryBuilder = new StringBuilder("WHERE (1=1) ");
        if (searchCriteria.getAuthorName() != null && !searchCriteria.getAuthorName().isEmpty()) {
            queryBuilder.append(" AND (author_name = '").append(searchCriteria.getAuthorName()).append("') ");
        }
        if (searchCriteria.getAuthorSurname() != null && !searchCriteria.getAuthorSurname().isEmpty()) {
            queryBuilder.append(" AND (author_surname = '").append(searchCriteria.getAuthorSurname()).append("') ");
        }
        Set<String> tagsList = searchCriteria.getTagsList();
        tagsList.forEach(c -> queryBuilder.append(" AND ('").append(c).append("' = ANY(tag_names)) "));

        if (!searchCriteria.getOrderByParameter().isEmpty()) {
            queryBuilder.append(" ORDER BY ");
            List<String> orderSet = new ArrayList<>(searchCriteria.getOrderByParameter());
            for (int i = 0; i < orderSet.size(); i++) {
                if (i > 0) {
                    queryBuilder.append(", ");
                }
                queryBuilder.append(orderSet.get(i));
            }
            if (searchCriteria.isDesc()) {
                queryBuilder.append(" DESC");
            }
        }

        return queryBuilder.append(";").toString();
    }
}
