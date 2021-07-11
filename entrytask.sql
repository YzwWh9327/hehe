create table account
(
    id           int auto_increment
        primary key,
    account_name varchar(10)                 not null,
    name         varchar(20)                 not null,
    gender       enum ('F', 'M') default 'M' not null,
    create_time  datetime                    null,
    update_time  datetime                    null,
    constraint account_account_name_uindex
        unique (account_name)
);

create table tab_account_0
(
    id           int auto_increment
        primary key,
    account_name varchar(10)                 not null,
    name         varchar(20)                 not null,
    gender       enum ('F', 'M') default 'M' not null,
    create_time  datetime                    null,
    update_time  datetime                    null,
    constraint account_account_name_uindex
        unique (account_name)
);

create table tab_account_1
(
    id           int auto_increment
        primary key,
    account_name varchar(10)                 not null,
    name         varchar(20)                 not null,
    gender       enum ('F', 'M') default 'M' not null,
    create_time  datetime                    null,
    update_time  datetime                    null,
    constraint account_account_name_uindex
        unique (account_name)
);

create table tab_account_2
(
    id           int auto_increment
        primary key,
    account_name varchar(10)                 not null,
    name         varchar(20)                 not null,
    gender       enum ('F', 'M') default 'M' not null,
    create_time  datetime                    null,
    update_time  datetime                    null,
    constraint account_account_name_uindex
        unique (account_name)
);

create table tab_account_3
(
    id           int auto_increment
        primary key,
    account_name varchar(10)                 not null,
    name         varchar(20)                 not null,
    gender       enum ('F', 'M') default 'M' not null,
    create_time  datetime                    null,
    update_time  datetime                    null,
    constraint account_account_name_uindex
        unique (account_name)
);

create table tab_account_4
(
    id           int auto_increment
        primary key,
    account_name varchar(10)                 not null,
    name         varchar(20)                 not null,
    gender       enum ('F', 'M') default 'M' not null,
    create_time  datetime                    null,
    update_time  datetime                    null,
    constraint account_account_name_uindex
        unique (account_name)
);

create table tab_account_5
(
    id           int auto_increment
        primary key,
    account_name varchar(10)                 not null,
    name         varchar(20)                 not null,
    gender       enum ('F', 'M') default 'M' not null,
    create_time  datetime                    null,
    update_time  datetime                    null,
    constraint account_account_name_uindex
        unique (account_name)
);

create table tab_account_6
(
    id           int auto_increment
        primary key,
    account_name varchar(10)                 not null,
    name         varchar(20)                 not null,
    gender       enum ('F', 'M') default 'M' not null,
    create_time  datetime                    null,
    update_time  datetime                    null,
    constraint account_account_name_uindex
        unique (account_name)
);

create table tab_account_7
(
    id           int auto_increment
        primary key,
    account_name varchar(10)                 not null,
    name         varchar(20)                 not null,
    gender       enum ('F', 'M') default 'M' not null,
    create_time  datetime                    null,
    update_time  datetime                    null,
    constraint account_account_name_uindex
        unique (account_name)
);

create table tab_account_8
(
    id           int auto_increment
        primary key,
    account_name varchar(10)                 not null,
    name         varchar(20)                 not null,
    gender       enum ('F', 'M') default 'M' not null,
    create_time  datetime                    null,
    update_time  datetime                    null,
    constraint account_account_name_uindex
        unique (account_name)
);

create table tab_account_9
(
    id           int auto_increment
        primary key,
    account_name varchar(10)                 not null,
    name         varchar(20)                 not null,
    gender       enum ('F', 'M') default 'M' not null,
    create_time  datetime                    null,
    update_time  datetime                    null,
    constraint account_account_name_uindex
        unique (account_name)
);

create table ticket
(
    id          int auto_increment
        primary key,
    user_id     int               not null,
    expired     datetime          null,
    ticket_info varchar(128)      not null,
    is_deleted  tinyint default 0 null comment '登录状态，0：默认，1：失效'
);

create table user
(
    id          bigint auto_increment
        primary key,
    username    varchar(128)      not null,
    password    varchar(128)      not null,
    nickname    varchar(128)      null,
    picture     varchar(128)      null,
    create_time datetime          null,
    update_time datetime          null,
    is_deleted  tinyint default 0 null comment '用户状态，默认：0，删除：1',
    constraint user_username_uindex
        unique (username)
);

