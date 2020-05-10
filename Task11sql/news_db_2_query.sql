/*
2. Write SQL statement to select author names who wrote more than 3 000 characters,
but the average number of characters per news is more than 500. Think about the
shortest statement notation.
*/
SELECT 
    author_with_news_length.id, 
    author_with_news_length.name, 
    author_with_news_length.surname, 
    SUM(author_with_news_length.symbols_per_news)AS total_symbols, 
    ROUND(AVG(author_with_news_length.symbols_per_news), 2) AS avg_symbols_per_news 
FROM
    (
    SELECT 
        author.id, 
        author.name, 
        author.surname, 
        LENGTH(news.full_text) AS symbols_per_news 
    FROM news
        RIGHT JOIN news_author ON news.id = news_author.news_id
        RIGHT JOIN author ON author.id = news_author.author_id
    ) author_with_news_length
    GROUP BY author_with_news_length.id, author_with_news_length.name, author_with_news_length.surname
    HAVING 
        SUM(author_with_news_length.symbols_per_news) > 3000 
        AND 
        AVG(author_with_news_length.symbols_per_news) > 500
    ORDER BY author_with_news_length.id
;