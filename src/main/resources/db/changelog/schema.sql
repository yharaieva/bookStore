create table if not exists book (
    id bigserial not null,
    title varchar(1000) not null,
    author varchar(1000) not null,
    primary key (id)
    );
