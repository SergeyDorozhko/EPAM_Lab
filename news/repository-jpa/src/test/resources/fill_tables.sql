insert into author (id, name, surname) values
	(1, 'Sergei', 'Crachev'),
	(2, 'Andrew', 'Bubkov'),
	(3, 'Igor', 'Bikov'),
	(4, 'Lena', 'Lavrenteva'),
	(5, 'Viktoria', 'Tolstaya'),
	(6, 'Petr', 'Ivanov'),
	(7, 'Egor', 'Pilec'),
	(8, 'Igor', 'Rudakovski'),
	(9, 'Yaugeni', 'Metelski'),
	(10, 'Sergei', 'Podorognikov'),
	(11, 'Vlad', 'Berezkin'),
	(12, 'Tolik', 'Petrov'),
	(13, 'Alexey', 'Sidorov'),
	(14, 'Vasya', 'Pupkin'),
	(15, 'Svetlana', 'Meleg'),
	(16, 'Tatiana', 'Belaya'),
	(17, 'Natali', 'Ivanova'),
	(18, 'Sahsa', 'Golikov'),
	(19, 'Vladimir', 'Kolesnikov'),
	(20, 'Igor', 'Mishkin');
	
insert into tag (id, name) values 
	(1, 'animals'),
	(2, 'game'),
	(3, 'pet'),
	(4, 'car'),
	(5, 'football'),
	(6, 'airplane'),
	(7, 'hocki'),
	(8, 'law'),
	(9, 'home'),
	(10, 'country'),
	(11, 'children'),
	(12, 'parents'),
	(13, 'equipment'),
	(14, 'music'),
	(15, 'movi'),
	(16, 'city'),
	(17, 'culture'),
	(18, 'clothes'),
	(19, 'tv'),
	(20, 'song');
	
insert into news (id, title, short_text, full_text, creation_date, modification_date) values
	(1, 'title 1', 'short text 1', 'full text 1', '2020-01-28', '2020-01-28'),
	(2, 'title 2', 'short text 2', 'full text 2', '2020-01-28', '2020-01-28'),
	(3, 'title 3', 'short text 3', 'full text 3', '2020-01-28', '2020-01-28'),
	(4, 'title 4', 'short text 4', 'full text 4', '2020-01-28', '2020-01-28'),
	(5, 'title 5', 'short text 5', 'full text 5', '2020-01-28', '2020-01-28'),
	(6, 'title 6', 'short text 6', 'full text 6', '2020-01-28', '2020-01-28'),
	(7, 'title 7', 'short text 7', 'full text 7', '2020-01-28', '2020-01-28'),
	(8, 'title 8', 'short text 8', 'full text 8', '2020-01-28', '2020-01-28'),
	(9, 'title 9', 'short text 9', 'full text 9', '2020-01-28', '2020-01-28'),
	(10, 'title 10', 'short text 10', 'full text 10', '2020-01-28', '2020-01-28'),
	(11, 'title 11', 'short text 11', 'full text 11', '2020-01-28', '2020-01-28'),
	(12, 'title 12', 'short text 12', 'full text 12', '2020-01-28', '2020-01-28'),
	(13, 'title 13', 'short text 13', 'full text 13', '2020-01-28', '2020-01-28'),
	(14, 'title 14', 'short text 14', 'full text 14', '2020-01-28', '2020-01-28'),
	(15, 'title 15', 'short text 15', 'full text 15', '2020-01-28', '2020-01-28'),
	(16, 'title 16', 'short text 16', 'full text 16', '2020-01-28', '2020-01-28'),
	(17, 'title 17', 'short text 17', 'full text 17', '2020-01-28', '2020-01-28'),
	(18, 'title 18', 'short text 18', 'full text 18', '2020-01-28', '2020-01-28'),
	(19, 'title 19', 'short text 19', 'full text 19', '2020-01-28', '2020-01-28'),
	(20, 'title 20', 'short text 20', 'full text 20', '2020-01-28', '2020-01-28');
	
insert into news_author (news_id, author_id) values
	(2, 3),
	(3, 3),
	(4, 3),
	(5, 4),
	(6, 4),
	(7, 4),
	(10, 2),
	(11, 3),
	(12, 5),
	(13, 5),
	(14, 8),
	(15, 9),
	(16, 10),
	(17, 11),
	(18, 11),
	(19, 12),
	(20, 13);
	
insert into news_tag (news_id, tag_id) values
	(1, 4),
	(1, 5),
	(1, 6),
	(2, 4),
	(1, 7),
	(1, 8),
	(3, 9),
	(3, 10),
	(3, 11),
	(4, 4),
	(4, 5),
	(4, 7),
	(16, 3),
	(16, 1),
	(1, 3),
	(19, 4),
	(19, 10),
	(19, 11),
	(16, 2),
	(1, 5);