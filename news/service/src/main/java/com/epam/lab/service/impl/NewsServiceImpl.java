package com.epam.lab.service.impl;

import com.epam.lab.dto.mapper.NewsMapper;
import com.epam.lab.dto.NewsDTO;
import com.epam.lab.dto.SearchCriteria;
import com.epam.lab.exception.InvalidAuthorException;
import com.epam.lab.exception.InvalidNewsDataException;
import com.epam.lab.model.Author;
import com.epam.lab.model.News;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.AuthorRepository;
import com.epam.lab.repository.NewsRepository;
import com.epam.lab.repository.TagRepository;
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
    public NewsServiceImpl(NewsMapper mapper,
                           NewsRepository newsRepository,
                           AuthorRepository authorRepository,
                           TagRepository tagRepository) {
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
     * <p>
     * If author data is valid setup to CreationDate and ModificationDate current date.
     * After this insert data of news to database and make link between author and news tables.
     * <p>
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

        setCreationDataToNews(news);

        newsRepository.create(news);

        connectAuthorWithNewsInStorage(news);

        processingWithTags(news);

        return mapper.toDTO(news);
    }


    private void checkNews(NewsDTO newsDTO) {
        boolean notValidNews = newsDTO.getTitle() == null
                || newsDTO.getShortText() == null
                || newsDTO.getFullText() == null;
        if (notValidNews) {
            throw new InvalidNewsDataException();
        }
    }


    private void checkAndCreateAuthorIfNew(News news) {
        boolean hasAuthor = news.getAuthor() != null;
        if (hasAuthor) {
            boolean hasAuthorWithId = news.getAuthor().getId() != 0;
            if (hasAuthorWithId) {
                checkAuthor(news.getAuthor());
            } else {
                authorRepository.create(news.getAuthor());
            }
        }
    }

    private void checkAuthor(Author author) {
        try {
            authorRepository.findBy(author);
        } catch (EmptyResultDataAccessException ex) {
            throw new InvalidAuthorException();
        }
    }

    private void setCreationDataToNews(News news) {
        news.setCreationDate(LocalDate.now());
        news.setModificationDate(LocalDate.now());
    }

    private void connectAuthorWithNewsInStorage(News news) {
        if (news.getAuthor() != null) {
            newsRepository.linkAuthorWithNews(news.getAuthor().getId(), news.getId());
        }
    }

    private void processingWithTags(News news) {
        boolean hasTags = news.getTags() != null && !news.getTags().isEmpty();
        if (hasTags) {
            for (int i = 0; i < news.getTags().size(); i++) {
                i = checkAndCreateTagIfNew(news, i);
            }
            makeUniqueListOfTags(news);
            connectTagsWithNewsInStorage(news);
        }
    }

    private int checkAndCreateTagIfNew(News news, int tagIndex) {
        Tag tag = news.getTags().get(tagIndex);
        boolean hasTagId = tag.getId() != 0;
        try {
            if (hasTagId) {
                tagRepository.findBy(tag);
            } else {
                tag = tagRepository.findBy(tag.getName());
            }
        } catch (EmptyResultDataAccessException ex) {
            if (hasTagId) {
                news.getTags().remove(tagIndex);
                return --tagIndex;
            } else {
                tagRepository.create(tag);
            }
        }
        news.getTags().set(tagIndex, tag);
        return tagIndex;
    }


    private void makeUniqueListOfTags(News news) {
        Set<Tag> tagList = new HashSet<>(news.getTags());
        List<Tag> list = new ArrayList<>(tagList);
        news.setTags(list);
    }

    private void connectTagsWithNewsInStorage(News news) {
        for (Tag tag : news.getTags()) {
            tagRepository.linkTagWithNews(tag.getId(), news.getId());
        }
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
     * adding this author to news otherwise throws ServiceException.
     * 3) if news came without author but this news in database has another author throws ServiceException.
     * 4) if news came without author and this news in database has the same author all is OK.
     * <p>
     * If author data is valid setup to ModificationDate current date.
     * After this insert data of news to database and make link between author and news tables.
     * <p>
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
        checkAuthorOrAddIfNewsWithoutAuthor(news);

        news.setModificationDate(LocalDate.now());
        newsRepository.update(news);

        deleteTagsFromThisNewsInStorage(news);

        processingWithTags(news);

        return mapper.toDTO(news);
    }

    private void checkAuthorOrAddIfNewsWithoutAuthor(News news) {
        Long correctAuthorId = findAuthorOfNews(news);
        if (newsHasAuthorInStorage(correctAuthorId)) {
            updatingNewsHasCorrectAuthor(correctAuthorId, news);
        } else {
            checkAndCreateAuthorIfNew(news);
            connectAuthorWithNewsInStorage(news);
        }
    }

    private Long findAuthorOfNews(News news) {
        try {
            return newsRepository.findAuthorIdByNewsId(news.getId());
        } catch (EmptyResultDataAccessException ex) {
            //TODO logger.
            System.err.println("News has no author (update action)");
        }
        return null;
    }

    private boolean newsHasAuthorInStorage(Long correctAuthorId) {
        return correctAuthorId != null;
    }

    private void updatingNewsHasCorrectAuthor(Long correctAuthorId, News news) {
        boolean incorrectAuthor = news.getAuthor() == null || correctAuthorId != news.getAuthor().getId();
        if (incorrectAuthor) {
            throw new InvalidAuthorException();
        }
    }

    private void deleteTagsFromThisNewsInStorage(News news) {
        tagRepository.deleteTagNewsLinks(news.getId());
    }


    /**
     * This method try to find news by id,  if it is successful then take author of this news (if one is exist)
     * and find all tags of this news (if they exist). then transfer from news to NewsDTO.
     *
     * @param id of news which need to find.
     * @return NewsDTO with all params if successful otherwise runtime exception.
     */
    @Override
    public NewsDTO findById(long id) {
        News news = newsRepository.findBy(id);
        getAuthorOfNews(news);
        getTagsOfNews(news);
        return mapper.toDTO(news);
    }

    private void getAuthorOfNews(News news) {
        boolean hasAuthor = news.getAuthor() != null && news.getAuthor().getId() != 0;
        if (hasAuthor) {
            news.setAuthor(authorRepository.findBy(news.getAuthor().getId()));
        }
    }

    private void getTagsOfNews(News news) {
        news.setTags(tagRepository.findBy(news));

    }

    @Override
    public long countAllNews() {
        return newsRepository.countAllNews();
    }

    @Override
    public List<NewsDTO> findAllNewsByQuery(SearchCriteria searchCriteria) {
        String query = makeQueryForSearch(searchCriteria);
        List<News> news = newsRepository.findAllNewsAndSortByQuery(query);
        return addTagsAndTransferToDTO(news);
    }

    private String makeQueryForSearch(SearchCriteria searchCriteria) {
        return new QueryBuilder()
                .setAuthorName(searchCriteria.getAuthorName())
                .setAuthorSurname(searchCriteria.getAuthorSurname())
                .setTags(searchCriteria.getTags())
                .setSort(searchCriteria)
                .buildQuery();
    }

    private List<NewsDTO> addTagsAndTransferToDTO(List<News> news) {
        List<NewsDTO> newsDTO = new ArrayList<>();
        for (News operatingNews : news) {
            operatingNews.setTags(tagRepository.findBy(operatingNews));
            newsDTO.add(mapper.toDTO(operatingNews));
        }
        return newsDTO;
    }
}
