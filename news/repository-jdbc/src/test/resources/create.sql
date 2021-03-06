
	CREATE TABLE users
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    name character varying(20) NOT NULL,
    surname character varying(20) NOT NULL,
    login character varying(30) NOT NULL,
    password character varying(30) NOT NULL,
    PRIMARY KEY (id)
);
	
	
	
	CREATE TABLE roles
(
    user_id bigint NOT NULL,
    role_name character varying(30) NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);
	
	
	
	
	CREATE TABLE author
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    name character varying(30) NOT NULL,
    surname character varying(30) NOT NULL,
    PRIMARY KEY (id)
);
	
	
	
	
	
	
	CREATE TABLE news
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    title character varying(30) NOT NULL,
    short_text character varying(100) NOT NULL,
    full_text character varying(2000) NOT NULL,
    creation_date date NOT NULL,
    modification_date date NOT NULL,
    PRIMARY KEY (id)
);
	
	
	
	
	CREATE TABLE news_author
(
    news_id bigint NOT NULL,
    author_id bigint NOT NULL,
    FOREIGN KEY (news_id)
        REFERENCES news (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (author_id)
        REFERENCES author (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);
	
	
	
	
	
	
	CREATE TABLE tag
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    name character varying(30) NOT NULL,
    PRIMARY KEY (id)
);
	
	
	
	
	CREATE TABLE news_tag
(
    news_id bigint NOT NULL,
    tag_id bigint NOT NULL,
    FOREIGN KEY (news_id)
        REFERENCES news (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (tag_id)
        REFERENCES tag (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);



CREATE VIEW news_tags_author AS
     SELECT news_with_tags.id,
    news_with_tags.title,
    news_with_tags.short_text,
    news_with_tags.full_text,
    news_with_tags.creation_date,
    news_with_tags.modification_date,
    news_with_tags.tag_ids,
    news_with_tags.tag_names,
    author.id AS author_id,
    author.name AS author_name,
    author.surname AS author_surname
   FROM ( SELECT news.id,
            news.title,
            news.short_text,
            news.full_text,
            news.creation_date,
            news.modification_date,
            array_agg(tag.id) AS tag_ids,
            array_agg(tag.name) AS tag_names
           FROM news
             LEFT JOIN news_tag ON news.id = news_tag.news_id
             LEFT JOIN tag ON tag.id = news_tag.tag_id
          GROUP BY news.id) news_with_tags
     LEFT JOIN news_author ON news_with_tags.id = news_author.news_id
     LEFT JOIN author ON news_author.author_id = author.id;
