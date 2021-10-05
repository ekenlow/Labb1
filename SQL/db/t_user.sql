create table t_user
(
	id int auto_increment
		primary key,
	email varchar(30) not null,
	password varchar(30) not null,
	type enum('USER', 'ADMIN', 'STOCKFILLER') not null,
	constraint t_user_email_uindex
		unique (email)
);

