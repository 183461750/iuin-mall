-- auto-generated definition

create schema test;

create table test.t_teacher
(
    id   bigint auto_increment
        primary key,
    name varchar(255) default '' not null comment '姓名',
    age  int          default 0  not null comment '年龄'
);

