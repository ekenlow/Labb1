create table t_order_item
(
	order_id int not null,
	item_id int not null,
	constraint t_order_item_t_item_id_fk
		foreign key (item_id) references t_item (id),
	constraint t_order_item_t_order_id_fk
		foreign key (order_id) references t_order (id)
);

