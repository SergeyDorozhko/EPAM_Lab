/*
3. Create custom db function that will return the list of all tags referred to a current news,
concatenated by specified separator character. Function must accept the news id and
separator character as input parameters and return a single string as a result of tag
values concatenation.
*/
CREATE OR REPLACE FUNCTION list_of_tags_of_current_news 
(current_news_id IN NUMBER, 
separator IN VARCHAR)
RETURN VARCHAR IS list_tags VARCHAR(100);
BEGIN
    SELECT LISTAGG(tag.name, separator) INTO list_tags FROM news
        LEFT JOIN news_tag ON news.id = news_tag.news_id
        LEFT JOIN tag ON news_tag.tag_id = tag.id
        WHERE news.id = current_news_id;
    RETURN (list_tags);
END;

