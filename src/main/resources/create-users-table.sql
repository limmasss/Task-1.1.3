create table if not exists users(
    id bigint not null auto_increment,
    name varchar(250),
    lastname varchar(250),
    age tinyint,
    primary key(id)
);