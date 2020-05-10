/*
6. * Create custom LOGGING db table. The following corresponding columns must be
created:
a. Record insert date;
b. Referenced Table Name - Table Name where new record was inserted;
c. Description - The list of key-value pairs, separated by semicolon. Note: empty
values and their column names must be omitted.
Add required changes to your DB Schema to populate current table columns each
time when new record was inserted to any db table (in a scope of your schema).
*/

create table logging_db_table (
record_insert_date TIMESTAMP  DEFAULT CURRENT_TIMESTAMP,
referenced_table_name VARCHAR2(30) NOT NULL,
description VARCHAR2(3000) NOT NULL);


create or replace trigger logging_tags_triger
  after insert on tag
  for each row
DECLARE
    insert_values VARCHAR2(3000);
begin
    insert_values := 'id = ' || :new.id || '; name = ' || :new.name || ';';
    INSERT INTO logging_db_table (referenced_table_name, description) VALUES('tag',insert_values);  
end;
/ 

create or replace trigger logging_news_tag_triger
  after insert on news_tag
  for each row
DECLARE
    insert_values VARCHAR2(3000);
begin
    insert_values := 'news_id = ' || :new.news_id || '; tag_id = ' || :new.tag_id || ';';
    INSERT INTO logging_db_table (referenced_table_name, description) VALUES('news_tag',insert_values);  
end;
/ 


create or replace trigger logging_news_author_triger
  after insert on news_author
  for each row
DECLARE
    insert_values VARCHAR2(3000);
begin
    insert_values := 'news_id = ' || :new.news_id || '; author_id = ' || :new.author_id || ';';
    INSERT INTO logging_db_table (referenced_table_name, description) VALUES('news_author',insert_values);  
end;
/ 


create or replace trigger logging_news_triger
  after insert on news
  for each row
DECLARE
    insert_values VARCHAR2(3000);
begin
    insert_values := 'id = ' || :NEW.id 
                || '; title = ' || :NEW.title 
                || '; SHORT_TEXT = ' || :NEW.short_text 
                || '; full_text = ' || :NEW.full_text 
                || '; creation_date = ' || :NEW.creation_date 
                || '; modification_date = ' ||:NEW.modification_date || ';';
    INSERT INTO logging_db_table (referenced_table_name, description) VALUES('news',insert_values);  
end;
/ 


create or replace trigger logging_comments_triger
  after insert on comments
  for each row
DECLARE
    insert_values VARCHAR2(3000);
begin
    insert_values := 'id = ' || :NEW.id 
                || '; news_id = ' || :NEW.news_id 
                || '; full_text = ' || :NEW.full_text  || ';';
    INSERT INTO logging_db_table (referenced_table_name, description) VALUES('comments',insert_values);  
end;
/ 



create or replace trigger logging_author_triger
  after insert on author
  for each row
DECLARE
    insert_values VARCHAR2(3000);
begin
    insert_values := 'id = ' || :new.id || '; name = ' || :new.name || '; surname = ' || :new.surname || ';';
    INSERT INTO logging_db_table (referenced_table_name, description) VALUES('author',insert_values);  
end;
/ 

