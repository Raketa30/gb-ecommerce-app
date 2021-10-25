create table user_data
(
    id       bigserial primary key,
    username varchar(30) not null unique,
    password varchar(80) not null,
    email    varchar(50) unique
);

create table role_data
(
    id   serial primary key,
    name varchar(50) not null
);

create table activity_data
(
    id        bigserial primary key,
    activated smallint default 0,
    uuid      text,
    user_id   bigint,
    foreign key (user_id) references user_data (id)
);

CREATE TABLE user_role
(
    user_id bigint not null,
    role_id int    not null,
    primary key (user_id, role_id),
    foreign key (user_id) references user_data (id),
    foreign key (role_id) references role_data (id)
);
insert into role_data (name)
values ('ROLE_USER'),
       ('ROLE_MANAGER'),
       ('ROLE_ADMIN');

insert into user_data (username, password, email)
values ('admin', 'admin', 'admin@gmail.com'),
       ('manager', 'manager', 'manager@gmail.com'),
       ('user', 'user1', 'user@gmail.com');

insert into activity_data(id, activated, uuid, user_id)
values (1, 1, 'schabswdsdbhjksdcbsdcjkd', 1),
       (2, 1, 'sdfsdfdfssdfdfsdfsdfssdf', 2),
       (3, 1, 'sdfsdfdfssdfdfasdasdsdfs', 3);

insert into user_role (user_id, role_id)
values (1, 3),
       (1, 2),
       (2, 2),
       (3, 1);

alter
sequence activity_data_id_seq restart with 100;

alter
sequence user_data_id_seq restart with 100;

create
extension if not exists pgcrypto;

update user_data
set password = crypt(password, gen_salt('bf', 8));