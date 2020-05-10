	CREATE TABLE author
(
    id NUMBER GENERATED AS IDENTITY PRIMARY KEY,
    name VARCHAR2(30) NOT NULL,
    surname VARCHAR2(30) NOT NULL
);
	
    
    
	CREATE TABLE news
(
    id NUMBER GENERATED AS IDENTITY PRIMARY KEY,
    title VARCHAR2(30) NOT NULL,
    short_text VARCHAR2(100) NOT NULL,
    full_text VARCHAR2(2000) NOT NULL,
    creation_date DATE NOT NULL,
    modification_date DATE NOT NULL
);
	
	
	
	
	CREATE TABLE news_author
(
    news_id NUMBER NOT NULL,
    author_id NUMBER NOT NULL,
    CONSTRAINT news_id_FK FOREIGN KEY (news_id)
        REFERENCES news (id)
        ON DELETE CASCADE,
    CONSTRAINT author_id_FK FOREIGN KEY (author_id)
        REFERENCES author (id)
        ON DELETE CASCADE
        
);	
	
	
	CREATE TABLE tag
(
    id NUMBER GENERATED AS IDENTITY PRIMARY KEY,
    name VARCHAR2(30) NOT NULL UNIQUE
);
	
	
	
	
	CREATE TABLE news_tag
(
    news_id NUMBER NOT NULL,
    tag_id NUMBER NOT NULL,
    CONSTRAINT "news_id_FK" FOREIGN KEY (news_id)
        REFERENCES news (id)
        ON DELETE CASCADE,
    CONSTRAINT "tag_id_FK" FOREIGN KEY (tag_id)
        REFERENCES tag (id) 
        ON DELETE CASCADE
);



	CREATE TABLE comments
(
    id NUMBER GENERATED AS IDENTITY PRIMARY KEY,
    news_id NUMBER NOT NULL,
    full_text VARCHAR2(1000) NOT NULL,
    CONSTRAINT "news_id_comment_FK" FOREIGN KEY (news_id)
        REFERENCES news (id)
        ON DELETE CASCADE
);
