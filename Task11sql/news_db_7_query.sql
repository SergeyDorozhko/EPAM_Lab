/*
7. Prepare a SQL statement to output total/available space within each tablespace.
Calculate “Used %” column value.


!!! CONNECT AS SYS.
*/
SELECT 
    available.tablespace_name, 
    available.Mb_available,  
    free.Mb_free, 
    round((available.Mb_available - free.Mb_free)/available.Mb_available*100, 2) "USED %" 
FROM
    (
    SELECT 
        SUM(bytes)/1024/1024 Mb_available,
        tablespace_name
    FROM dba_data_files
    GROUP BY tablespace_name
    ) available

INNER JOIN 
    (
    SELECT 
        SUM(bytes)/1024/1024 Mb_free,
        tablespace_name
    FROM dba_free_space
        GROUP BY tablespace_name 
    ) free ON available.tablespace_name = free.tablespace_name
;
 
 
 
