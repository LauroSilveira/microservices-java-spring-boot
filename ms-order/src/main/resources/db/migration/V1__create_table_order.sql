CREATE TABLE orders (
   id BIGINT AUTO_INCREMENT NOT NULL,
   purchase_date datetime NOT NULL,
   status_order VARCHAR(255) NOT NULL,
   CONSTRAINT pk_order PRIMARY KEY (id)
);