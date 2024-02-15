create table if not exists author (
    id bigserial not null,
    first_name varchar(1000) not null,
    last_name varchar(1000) not null,
    primary key (id)
    );

ALTER TABLE book
RENAME COLUMN author TO author_id;

ALTER TABLE book
ALTER COLUMN author_id TYPE bigint
USING author_id::bigint;

alter table book
    ADD CONSTRAINT  fk_author
      FOREIGN KEY(author_id)
        REFERENCES author(id);
