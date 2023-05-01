CREATE TABLE product(
    product_number INTEGER(10) PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE sku(
    sku_number INTEGER(10) PRIMARY KEY NOT NULL,
    product_id INTEGER(10),
    image VARCHAR(255),
    price DECIMAL(10, 2) NOT NULL,
    currency VARCHAR(255) NOT NULL,
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product (product_number)
);