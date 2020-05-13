/*
5. * Make a writers competition cross-map. Create a statement that will generate random
authors distribution by competition pairs.
Statement will generate a list of author names pairs selected for a single round of
tournament, displayed as two separate columns. Each author must be presented in
resulting set (in both columns) once only. (The total number of authors must be even. Do
not use custom functions during current implementation.)
*/
WITH competitors_list AS 
    (
    SELECT 
        author_randomId_totalcount.name,
        author_randomId_totalcount.surname,
        NTILE((author_randomId_totalcount.totalAuthors)) OVER (partition by author_randomId_totalcount.totalAuthors ORDER BY newid DESC) as competitor_number,
        author_randomId_totalcount.totalAuthors 
    FROM 
        (
        SELECT   
            name,
            surname,
            sys_guid() as newid,
            totalAuthors
        FROM
            author,
            (SELECT COUNT(*) AS totalAuthors from author) ) author_randomId_totalcount)
SELECT  
    one.name,
    one.surname,
    'VS' , 
    two.name, 
    two.surname 
FROM competitors_list one, competitors_list two
WHERE one.competitor_number = two.competitor_number+(two.totalAuthors/2)
ORDER BY one.competitor_number;
