package com.epam.lab.repository.impl;

import com.epam.lab.exception.RepositoryException;
import com.epam.lab.model.Author;
import com.epam.lab.model.News;
import com.epam.lab.repository.AbstractRepository;
import com.epam.lab.repository.NewsRepository;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class NewsRepositoryImpl extends AbstractRepository implements NewsRepository {

    private static final String INSERT_NEW_NEWS = " INSERT INTO news (title, short_text, full_text, creation_date,"
            + "modification_date) VALUES (?, ?, ?, ? , ?);";
    private static final String SELECT_NEWS_AND_AUTHOR_ID_BY_NEWS_ID = " SELECT news_author.author_id, news.id,"
            + " news.title, news.short_text, news.full_text, news.creation_date, news.modification_date FROM news"
            + " LEFT JOIN news_author ON news_author.news_id = news.id WHERE news.id = ?;";
    private static final String INSERT_INTO_NEWS_AUTHOR_AUTHOR_ID_NEWS_ID
            = "INSERT INTO news_author (author_id, news_id) VALUES (?, ?);";
    private static final String SELECT_AUTHOR_ID_FROM_NEWS_AUTHOR_WHERE_NEWS_ID
            = "SELECT author_id FROM news_author WHERE news_id = ?";
    private static final String SELECT_NEWS_ID_BY_AUTHOR_ID
            = "SELECT news_id FROM news_author WHERE author_id = ?";
    private static final String UPDATE_NEWS_BY_ID
            = "UPDATE news SET title = ? , short_text = ?, full_text = ?, modification_date = ? WHERE id = ?";
    private static final String DELETE_FROM_NEWS_WHERE_ID = "DELETE FROM news WHERE id = ?";
    private static final String COUNT_ALL_NEWS = "SELECT COUNT(id) FROM news";
    private static final String FIND_BY_QUERY
            = " SELECT id, title, short_text, full_text, creation_date, modification_date, author_id, "
            + " author_name, author_surname FROM news_tags_author ";

    private static final String NEWS_ID = "id";
    private static final String NEWS_TITLE = "title";
    private static final String NEWS_SHORT_TEXT = "short_text";
    private static final String NEWS_FULL_TEXT = "full_text";
    private static final String NEWS_CREATION_DATE = "creation_date";
    private static final String NEWS_MODIFICATION_DATE = "modification_date";
    private static final String AUTHOR_ID = "author_id";
    private static final String NEWS_AUTHOR_NAME = "author_name";
    private static final String NEWS_AUTHOR_SURNAME = "author_surname";

    @Override
    public News create(News bean) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_NEW_NEWS, Statement.RETURN_GENERATED_KEYS);
            int counter = 1;
            ps.setString(counter++, bean.getTitle());
            ps.setString(counter++, bean.getShortText());
            ps.setString(counter++, bean.getFullText());
            ps.setDate(counter++, Date.valueOf(bean.getCreationDate()));
            ps.setDate(counter, Date.valueOf(bean.getModificationDate()));
            return ps;
        }, keyHolder);
        bean.setId((long) keyHolder.getKeys().get(NEWS_ID));
        return bean;
    }

    @Override
    public void linkAuthorWithNews(long authorId, long newsId) {
        jdbcTemplate.update(INSERT_INTO_NEWS_AUTHOR_AUTHOR_ID_NEWS_ID, authorId, newsId);
    }

    @Override
    public boolean delete(long id) {
        int result = jdbcTemplate.update(DELETE_FROM_NEWS_WHERE_ID, id);
        return isDeleted(result);
    }

    private boolean isDeleted(Integer numberOfDeletedLines) {
        return numberOfDeletedLines != 0;
    }

    @Override
    public News update(News bean) {

        int result = jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(UPDATE_NEWS_BY_ID, Statement.RETURN_GENERATED_KEYS);
            int counter = 1;
            statement.setString(counter++, bean.getTitle());
            statement.setString(counter++, bean.getShortText());
            statement.setString(counter++, bean.getFullText());
            statement.setDate(counter++, Date.valueOf(bean.getModificationDate()));
            statement.setLong(counter, bean.getId());
            return statement;
        }, keyHolder);
        boolean noUpdates = result == 0;
        if (noUpdates) {
            throw new RepositoryException("Error news id");
        }
        return bean;
    }

    @Override
    public Long findAuthorIdByNewsId(long newsId) {
        return jdbcTemplate.queryForObject(SELECT_AUTHOR_ID_FROM_NEWS_AUTHOR_WHERE_NEWS_ID, new Object[]{newsId},
                Long.class);

    }

    @Override
    public List<Long> findNewsIdByAuthor(long authorId) {
        return jdbcTemplate.queryForList(SELECT_NEWS_ID_BY_AUTHOR_ID,
                new Object[]{authorId}, Long.class);
    }

    @Override
    public News findBy(long id) {
        return jdbcTemplate.queryForObject(SELECT_NEWS_AND_AUTHOR_ID_BY_NEWS_ID,
                new Object[]{id},
                (resultSet, num) -> assembleNews(resultSet));
    }

    private News assembleNews(ResultSet resultSet) throws SQLException {
        News news = new News();
        news.setId(resultSet.getLong(NEWS_ID));
        news.setTitle(resultSet.getString(NEWS_TITLE));
        news.setShortText(resultSet.getString(NEWS_SHORT_TEXT));
        news.setFullText(resultSet.getString(NEWS_FULL_TEXT));
        news.setCreationDate(resultSet.getDate(NEWS_CREATION_DATE).toLocalDate());
        news.setModificationDate(resultSet.getDate(NEWS_MODIFICATION_DATE).toLocalDate());
        Author author = new Author();
        author.setId(resultSet.getLong(AUTHOR_ID));
        if (isAuthorExist(author)) {
            news.setAuthor(author);
        }
        return news;
    }

    private boolean isAuthorExist(Author author) {
        return author != null && author.getId() != 0;
    }

    @Override
    public long countAllNews() {
        return jdbcTemplate.queryForObject(COUNT_ALL_NEWS, Long.class);
    }

    @Override
    public List<News> findAllNewsAndSortByQuery(String query) {
        return jdbcTemplate.query(FIND_BY_QUERY + query, (resultSet, num) -> {
            News news = assembleNews(resultSet);
            if (isAuthorExist(news.getAuthor())) {
                news.getAuthor().setName(resultSet.getString(NEWS_AUTHOR_NAME));
                news.getAuthor().setSurname(resultSet.getString(NEWS_AUTHOR_SURNAME));
            }
            return news;
        });
    }
}
