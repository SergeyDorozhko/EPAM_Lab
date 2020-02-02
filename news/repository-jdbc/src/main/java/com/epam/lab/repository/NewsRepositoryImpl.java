package com.epam.lab.repository;

import com.epam.lab.model.Author;
import com.epam.lab.model.News;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;

@Repository
public class NewsRepositoryImpl extends AbstractRepository implements NewsRepository {

    private static final String INSERT_NEW_NEWS = " INSERT INTO news (title, short_text, full_text, creation_date,"
            + "modification_date) VALUES (?, ?, ?, ? , ?);";
    private static final String SELECT_NEWS_AND_AUTHOR_ID_BY_NEWS_ID = " SELECT news_author.author_id, news.id,"
            + " news.title, news.short_text, news.full_text, news.creation_date, news.modification_date FROM news"
            + " LEFT JOIN news_author ON news_author.news_id = news.id WHERE news.id = ?;";
    private static final String INSERT_INTO_NEWS_AUTHOR_AUTHOR_ID_NEWS_ID
            = "INSERT INTO news_author (author_id, news_id) VALUES (?, ?);";

    @Override
    public News create(News bean) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_NEW_NEWS, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, bean.getTitle());
            ps.setString(2, bean.getShortText());
            ps.setString(3, bean.getFullText());
            ps.setDate(4, Date.valueOf(LocalDate.now()));
            ps.setDate(5, Date.valueOf(LocalDate.now()));
            return ps;
        }, keyHolder);
        bean.setId((long) keyHolder.getKeys().get("id"));
        bean.setCreationDate(((Date) keyHolder.getKeys().get("creation_date")).toLocalDate());
        bean.setModificationDate(((Date) keyHolder.getKeys().get("modification_date")).toLocalDate());
        return bean;
    }

    @Override
    public void linkAuthorWithNews(long authorId, long newsId) {
        jdbcTemplate.update(INSERT_INTO_NEWS_AUTHOR_AUTHOR_ID_NEWS_ID, new Object[]{authorId, newsId});
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public News update(News bean) {
        return null;
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


}
