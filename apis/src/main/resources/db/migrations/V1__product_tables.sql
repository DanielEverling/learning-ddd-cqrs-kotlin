create schema product;

CREATE TABLE product.category (
	id uuid NOT NULL,
	name varchar(30) NOT NULL,
	CONSTRAINT category_pk PRIMARY KEY (id)
);

CREATE TABLE product.product (
	id uuid NOT NULL,
	name varchar(30) NOT NULL,
	description varchar(100) NOT NULL,
	category_id uuid NOT NULL,
	active bool NOT NULL,
	value numeric(30,6) NOT NULL,
	CONSTRAINT product_pk PRIMARY KEY (id)
);
ALTER TABLE product.product ADD CONSTRAINT product_category_fk FOREIGN KEY (category_id) REFERENCES product.category(id);