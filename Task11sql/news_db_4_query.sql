/*
4. Develop single SQL statement that will return the list of all available news (news id,
news title columns) and one more column that will display all concatenated tags values,
available for current news as a single string.
Make two versions of statement:
a. By using previously developed custom function (#3).
b. * By using Oracle 10g DB features
*/

--a)
SELECT news.id, news.title, list_of_tags_of_current_news(news.id, ', ') FROM news;


--b)

SET SERVEROUTPUT ON;


DECLARE
  news_id           news.id%TYPE;
  news_title  news.title%TYPE;
  tags          VARCHAR(2000);

  iterator        INTEGER := 1;
 
  CURSOR c1 IS
    SELECT news.id, news.title, list_of_tags_of_current_news(news.id, ', ') FROM news;

BEGIN
  OPEN c1;  -- PL/SQL evaluates factor 
  LOOP
    FETCH c1 INTO news_id, news_title, tags;
    EXIT WHEN c1%NOTFOUND;
    DBMS_OUTPUT.PUT_LINE('id = ' || news_id || ' title = ' || news_title || ' tags = ' || tags);
    iterator := iterator + 1;
  END LOOP; 
  CLOSE c1;
END;


