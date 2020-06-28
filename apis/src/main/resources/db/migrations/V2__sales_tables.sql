create schema sales;

CREATE TABLE sales."order" (
	id uuid NOT NULL,
	customer_id uuid NOT NULL,
	street varchar(100) NOT NULL,
	"number" int NOT NULL,
	city varchar(30) NOT NULL,
	state varchar(2) NOT NULL,
	complement varchar(100) NOT NULL,
	CONSTRAINT order_pk PRIMARY KEY (id)
);

CREATE TABLE sales.order_item (
	id uuid NOT NULL,
	product_id uuid NOT NULL,
	quantity double precision NOT NULL,
	unit_value double precision NOT NULL,
	order_id uuid NOT NULL,
	CONSTRAINT order_item_pk PRIMARY KEY (id),
	CONSTRAINT order_item_fk FOREIGN KEY (order_id) REFERENCES sales."order"(id)
);
