/* 1. Develop a query to calculate the number of news, written by each author, the average
number of comments per news for a current author and the most popular tag, referred to
author news. All these information must be output in one single result set. Based on
these query create a custom db view. */

--------------------------------------------------------------------------------------------------------------------------------
CREATE VIEW author_news_info_query AS
SELECT
    author_news_info.id,
    author_news_info.name,
    author_news_info.surname,
    author_news_info.total_news,
    ROUND(author_news_info.avg_comments_per_news, 2 ) avg_comments_per_news,
    LISTAGG(author_tags_info.tag_name, ', ') most_used_tag
FROM
    (
    SELECT 
        author.id,
        author.name,
        author.surname,
        COUNT(news_with_num_of_comments.id) AS total_news,
        AVG(news_with_num_of_comments.total_comments_per_news) AS avg_comments_per_news
    FROM 
        (
        SELECT 
            news.id,
            news.title,
            COUNT(news.id) AS total_comments_per_news
        FROM news 
            LEFT JOIN comments ON news.id = comments.news_id
            GROUP BY news.id, news.title
        ) news_with_num_of_comments
            RIGHT JOIN news_author ON news_author.news_id = news_with_num_of_comments.id
            RIGHT JOIN author ON author.id = news_author.author_id
            GROUP BY author.id, author.name, author.surname
    ) author_news_info
    LEFT JOIN 
        (
        SELECT 
            author_tags2.id,
            author_tags2.tag_name
        FROM
            (
            SELECT
                author_tags.id,
                MAX(author_tags.count_each_tag_of_author) AS tag_max_value
            FROM 
                (
                SELECT 
                    author.id, 
                    count(tag.name) AS count_each_tag_of_author
                FROM news
                    LEFT JOIN news_tag ON news.id= news_tag.news_id
                    INNER JOIN tag ON news_tag.tag_id = tag.id
                    INNER JOIN news_author ON news.id = news_author.news_id
                    INNER JOIN author ON news_author.author_id = author.id
                    GROUP BY author.id, tag.name
                ) author_tags
                    GROUP BY author_tags.id
            ) max_tag,
            (
            SELECT
                author.id,
                count(tag.name) AS count_each_tag_of_author, 
                tag.name AS tag_name 
            FROM news
                LEFT JOIN news_tag ON news.id= news_tag.news_id
                INNER JOIN tag ON news_tag.tag_id = tag.id
                INNER JOIN news_author ON news.id = news_author.news_id
                INNER JOIN author ON news_author.author_id = author.id
                GROUP BY author.id, author.name, author.surname, tag.name
            ) author_tags2
            WHERE author_tags2.id = max_tag.id AND author_tags2.count_each_tag_of_author = max_tag.tag_max_value
            GROUP BY author_tags2.id, author_tags2.tag_name
        ) author_tags_info ON author_news_info.id =  author_tags_info.id
    GROUP BY author_news_info.id, author_news_info.name, author_news_info.surname, author_news_info.total_news, author_news_info.avg_comments_per_news
    ORDER BY author_news_info.id
;
------------------------------------------------------------------------------------------------------------------------------------------------------------------------