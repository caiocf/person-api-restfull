
    create table books (
       key bigint generated by default as identity,
        author varchar(180) not null,
        launch_date date not null,
        price double not null,
        title varchar(250) not null,
        primary key (key)
    )

    create table permission (
       id bigint generated by default as identity,
        description varchar(255),
        primary key (id)
    )

    create table person (
       id bigint generated by default as identity,
        address varchar(100) not null,
        first_name varchar(80) not null,
        gender varchar(6) not null,
        last_name varchar(80) not null,
        primary key (id)
    )

    create table user_permission (
       id_user bigint not null,
        id_permission bigint not null
    )

    create table users (
       id bigint generated by default as identity,
        account_non_expired boolean,
        account_non_locked boolean,
        credentials_non_expired boolean,
        enabled boolean,
        full_name varchar(255),
        password varchar(255),
        user_name varchar(255),
        primary key (id)
    )

    alter table users 
       add constraint UK_k8d0f2n7n88w1a16yhua64onx unique (user_name)

    alter table user_permission 
       add constraint FKo47t1we6do84ku6rb9jcey2s9 
       foreign key (id_permission) 
       references permission

    alter table user_permission 
       add constraint FKprpp02ivhe66b5nrc0a3a4lk8 
       foreign key (id_user) 
       references users
