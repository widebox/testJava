drop table if exists t_User cascade;
drop sequence user_sequence;
create table t_User (id int8 not null, digest varchar(255), status varchar(255), username varchar(255), primary key (id));
create sequence user_sequence;
