create table if not exists author (
    id bigserial not null,
    first_name varchar(1000) not null,
    last_name varchar(1000) not null,
    primary key (id)
    );

ALTER TABLE book
ADD COLUMN author_id bigint not null;

ALTER TABLE book
DROP COLUMN author;

alter table book
    ADD CONSTRAINT  fk_author
      FOREIGN KEY(author_id)
        REFERENCES author(id);
