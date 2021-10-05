create table t_order
(
	id int auto_increment
		primary key,
	status enum('ORDERED', 'PICKING', 'SENT') not null
);

