insert into client(id, full_name, birthday, phone, email) values
(1, 'Мишина Екатерина Федоровна', '1992-05-19', '89209430243', 'Katya92@mail.ru'),
(2, 'Новиков Владимир Святославович', '1987-10-21', '89346566645', 'Novikov1987@mail.ru'),
(3, 'Александров Филипп Сергеевич', '1999-09-05', '89665556734', 'Filipph@mail.ru'),
(4, 'Морозова Александра Максимовна', '1998-03-15', '89340043434', 'Morozova_Aleks@mail.ru');

insert into animal(id, name, gender, type, breed, birthday, path, client_id) values
(1, 'Кошь', 'Другой', 'Кошка', 'Дворовая', '2012-08-15', null, 1),
(2, 'Мышь', 'Мужской', 'Мышь', 'Дворовая', '2013-08-15', 'image.jpg', 2),
(3, 'Собашь', 'Женский', 'Собака', 'Дворовая', '2014-08-15', 'photo.jpg', 1),
(4, 'Александр', 'Мужской', 'Игуана', 'Дворовая', '2015-08-15', 'img.jpg', 4),
(5, 'Попушь', 'Женский', 'Попугай', 'Дворовый', '2016-08-15', 'img.jpg', 3);

insert into employee(id, full_name, phone, email, post, speciality, can_help) values
(1, 'Admin', '89200505551', 'ZekaZak220@mail.ru', 'Администратор', 'Пользователи', false),
(2, 'Виноградов Константин Владимирович', '89204567812', 'Kostya@mail.ru', 'Ветеринар', 'Котики', true),
(3, 'Помидоров Олег Дмитриевич', '89207465491', 'Pomidor228@mail.ru', 'Медбрат', '', true);

insert into visit(id, animal_id, employee_id, helper_employee_id, client_id, total_cost, diagnosis, assignment, date) values
(1, 1, 1, 2, 1, 2000, 'Депрессия', 'Успокоительные', '2022-12-24 15:05:05'),
(2, 2, 2, 3, 1, 2000, 'Депрессия', 'Успокоительные', '2022-12-24 17:05:00');

insert into treatment(id, name, cost) values
(1, 'Обследование', 300),
(2, 'Кремация', 5000),
(3, 'Усыпление', 2500);

insert into visit_treatments(visits_id, treatments_id) values
(1, 1),
(2, 1),
(2, 3);