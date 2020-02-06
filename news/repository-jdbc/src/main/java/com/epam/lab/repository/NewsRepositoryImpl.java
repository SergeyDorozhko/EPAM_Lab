package com.epam.lab.repository;

import com.epam.lab.exception.RepositoryException;
import com.epam.lab.model.Author;
import com.epam.lab.model.News;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
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
    private static final String SELECT_AUTHOR_ID_FROM_NEWS_AUTHOR_WHERE_NEWS_ID = "SELECT author_id FROM news_author WHERE news_id = ?";
    private static final String SELECT_NEWS_ID_BY_AUTHOR_ID = "SELECT news_id FROM news_author WHERE author_id = ?";
    private static final String UPDATE_NEWS_BY_ID = "UPDATE news SET title = ? , short_text = ?, full_text = ?, modification_date = ? WHERE id = ?";
    private static final String DELETE_FROM_NEWS_WHERE_ID = "DELETE FROM news WHERE id = ?";
    private static final String COUNT_ALL_NEWS = "SELECT COUNT(id) FROM news";
    public static final String FIND_BY_QUERY = "SELECT id, title, short_text, full_text, creation_date, modification_date, author_id, author_name, author_surname FROM news_tags_author ";

    @Override
    public News create(News bean) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_NEW_NEWS, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, bean.getTitle());
            ps.setString(2, bean.getShortText());
            ps.setString(3, bean.getFullText());
            ps.setDate(4, Date.valueOf(bean.getCreationDate()));
            ps.setDate(5, Date.valueOf(bean.getModificationDate()));
            return ps;
        }, keyHolder);
        bean.setId((long) keyHolder.getKeys().get("id"));
        return bean;
    }

    @Override
    public void linkAuthorWithNews(long authorId, long newsId) {
        jdbcTemplate.update(INSERT_INTO_NEWS_AUTHOR_AUTHOR_ID_NEWS_ID, new Object[]{authorId, newsId});
    }

    @Override
    public boolean delete(long id) {
        int result = jdbcTemplate.update(DELETE_FROM_NEWS_WHERE_ID, id);
        return result == 1;
    }

    @Override
    public News update(News bean) {

        int result = jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(UPDATE_NEWS_BY_ID, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, bean.getTitle());
            statement.setString(2, bean.getShortText());
            statement.setString(3, bean.getFullText());
            statement.setDate(4, Date.valueOf(bean.getModificationDate()));
            statement.setLong(5, bean.getId());
            return statement;
        }, keyHolder);
        if (result == 0) {
            throw new RepositoryException("Error data");
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
                (resultSet, num) -> {
                    News news = new News();
                    news.setId(resultSet.getLong("id"));
                    news.setTitle(resultSet.getString("title"));
                    news.setShortText(resultSet.getString("short_text"));
                    news.setFullText(resultSet.getString("full_text"));
                    news.setCreationDate(resultSet.getDate("creation_date").toLocalDate());
                    news.setModificationDate(resultSet.getDate("modification_date").toLocalDate());
                    Author author = new Author();
                    author.setId(resultSet.getLong("author_id"));
                    news.setAuthor(author);
                    return news;
                });
    }


    @Override
    public long countAllNews() {
        return jdbcTemplate.queryForObject(COUNT_ALL_NEWS, Long.class);
    }

    @Override
    public List<News> findAllNewsAndSortByQuery(String query) {
        return jdbcTemplate.query(FIND_BY_QUERY + query, (resultSet, num) -> {
            News news = new News();
            news.setId(resultSet.getLong("id"));
            news.setTitle(resultSet.getString("title"));
            news.setShortText(resultSet.getString("short_text"));
            news.setFullText(resultSet.getString("full_text"));
            news.setCreationDate(resultSet.getDate("creation_date").toLocalDate());
            news.setModificationDate(resultSet.getDate("modification_date").toLocalDate());
            Author author = new Author();
            author.setId(resultSet.getLong("author_id"));
            author.setName(resultSet.getString("author_name"));
            author.setSurname(resultSet.getString("author_surname"));
            if(author.getId() != 0) {
                news.setAuthor(author);
            }
            return news;
        });
    }
}
