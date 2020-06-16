create sequence rboard_idx_seq;
delete from rboard;
drop sequence rboard_idx_seq;
create table rboard (
    idx int not null primary key,
    ref int not null,
    lev int not null,
    seq int not null,
    name varchar(50) not null,
    password varchar(30) not null,
    title varchar(80) not null,
    content varchar(500) not null,
    wdate varchar(30) not null,
    hit int not null,
    ip varchar(20) not null
);
create sequence rboard_comment_idx_seq;
delete from rboard_comment;
drop sequence rboard_comment_idx_seq;
create table rboard_comment (
    idx int not null primary key,
    ref int not null,
    name varchar(50) not null,
    password varchar(30) not null,
    content varchar(500) not null,
    wdate varchar(30) not null,
    ip varchar(20) not null
);