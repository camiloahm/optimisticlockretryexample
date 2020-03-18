drop table if exists DEMO;

create table if not exists DEMO
(
    id          int         not null,
    counter     int         not null,
    version     int         not null,
    sample_text varchar(50) not null,
    primary key (id)
);

insert into DEMO(id, counter, version, sample_text)
values (1, 1, 1, 'this is a sample text')