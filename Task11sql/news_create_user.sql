CREATE USER admin 
    IDENTIFIED BY admin 
    DEFAULT TABLESPACE users 
    QUOTA 10M ON users 
    TEMPORARY TABLESPACE temp
    QUOTA 5M ON system 
    PASSWORD EXPIRE;

    GRANT create session TO admin;
    
    GRANT create table TO admin;

    GRANT create view, create procedure, create sequence TO admin;
    
    
    GRANT create TRIGGER TO admin;
        GRANT DROP ANY TRIGGER TO admin;

        GRANT DROP ANY TABLE TO admin;
        
        GRANT DEBUG ANY PROCEDURE, DEBUG CONNECT SESSION TO admin;

     
    
    
    
