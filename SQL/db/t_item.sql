create table t_item
(
	id int auto_increment primary key,
	type varchar(32) null,
	name varchar(32) not null,
	price float not null,
	stock int not null
);

alter table t_item
	add primary key (id);

