create table if not exists has (
    author_id bigint not null,
    book_id bigint not null,
    constraint author_foreign_key foreign key (author_id) references author(id),
    constraint book_foreign_key foreign key (book_id) references book(id),
    constraint has_composite_key unique (author_id,book_id)
);

insert into has (author_id, book_id)
select author_id, id from book;

ALTER TABLE book
DROP COLUMN author_id;