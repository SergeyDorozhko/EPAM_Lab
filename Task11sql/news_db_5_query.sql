
SELECT t1.name, t1.surname, NTILE(2) OVER (ORDER BY newid DESC) as half FROM
(
SELECT id, name, surname, sys_guid() as newid
from author
) t1, 
(SELECT count(*) as total from author) t2


--ORDER by newid
;



SELECT t1.name, NTILE(2) OVER (ORDER BY newid DESC) FROM
(
SELECT id, name, surname, sys_guid() as newid
from author
) t1, 
(SELECT count(*) as total from author) t2
--ORDER by newid

;


select t1.name nae, t2.name name2 From author t1
cross join author t2
WHERE t2.id <> t1.id
;
