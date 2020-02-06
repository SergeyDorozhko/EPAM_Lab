
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
	