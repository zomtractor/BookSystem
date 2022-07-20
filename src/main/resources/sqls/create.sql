use book;
create table user
(
    id int auto_increment,
    username nchar(32) not null unique,
    password nchar(32) not null,
    borrowing_num int default 0 not null,
    constraint user_pk
        primary key (id)
);

