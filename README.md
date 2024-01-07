Этот проект расширение прошлого проекта из главной ветки, содержит пагинацию поиск, сортировку и срок истечения при попытке вернуть книгу более 10 дней.
Также этот проект переписан польностью из низкоуревного в более высокий уровень абстракции используя Hibernate + Jpa.


sql command for create table in MYSQL:

create table person(
    id int auto_increment,
    surname varchar(50) unique,
    year int check(year > 1900),
    primary key (id)
);

create table book(
    id int auto_increment,
    person_id int,
    name varchar(50) not null ,
    author varchar(50) not null ,
    year_of_product int check ( year_of_product > 1900),
    taken_at timestamp,
    primary key(id),
    foreign key (person_id) references person(id) on delete set null
);
