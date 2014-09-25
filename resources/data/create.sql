-- enq4 schema

drop table if exists enq4;
create table enq4 (
       id    integer primary key,
       name  varchar(255) not null,
       subject varchar(255) not null,
       q1      varchar(255) not null,
       q2      varchar(255) not null,
       q3      varchar(255) not null,
       q4      varchar(255) not null,
       original varchar(255) not null, -- url for downloading original
       upload   varchar(255) default null, -- url for uploaded file
       timestamp datetime default null,
       in_use    bool default true
);

insert into enq4 (name, subject, q1, q2, q3, q4, original, upload) values
       ('kimura', 'clojure', 'q1', 'q2', 'q3', 'q4', 'original', 'update');
insert into enq4 (name, subject, q1, q2, q3, q4, original, upload) values
       ('hiroshi', 'ruby', 'q1', 'q2', 'q3', 'q4','down', 'up');
