/*
8. Prepare a SQL Statement to calculate each table size in your schema. 
*/
begin
    dbms_stats.gather_table_stats('admin','NEWS');
    dbms_stats.gather_table_stats('admin','NEWS_AUTHOR');
    dbms_stats.gather_table_stats('admin','NEWS_TAG');
    dbms_stats.gather_table_stats('admin','AUTHOR');
    dbms_stats.gather_table_stats('admin','COMMENTS');
    dbms_stats.gather_table_stats('admin','TAG');
    dbms_stats.gather_table_stats('admin','LOGGING_DB_TABLE');
end;
/



    SELECT
        table_name, num_rows
    FROM all_tables 
    WHERE table_name LIKE 'NEWS%' OR table_name LIKE 'TAG%' OR table_name LIKE 'AUTHOR%' OR table_name LIKE 'COMMENTS%' OR table_name LIKE 'LOGGING%'
    ORDER BY num_rows DESC,table_name;
    
 /*   
    
        SELECT *    FROM sys.all_tables 
    WHERE table_name LIKE 'NEWS%' OR table_name LIKE 'TAG%' OR table_name LIKE 'AUTHOR%' OR table_name LIKE 'COMMENTS%'
    ORDER BY num_rows DESC,table_name;
    
    /*