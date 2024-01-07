Простой Crud проект с сущностями библиотека и человек, где сотрудники из библиотеки могут назначить или же взять книгу у человека.

Комманда для создания базы данных в MySQL

create table person( id int auto_increment, surname varchar(50) unique, year int check(year > 1900), primary key (id) );

create table book( id int auto_increment, person_id int, name varchar(50) not null , author varchar(50) not null , year_of_product int check ( year_of_product > 1900), primary key(id), foreign key (person_id) references person(id) on delete set null );


Был добавлен проект с более высокой абстракцией с обращением к БД, где не используется SQL комманды. А также имеюет более расширенный функционал этого проекта.
Проект расположен на ветке: second
