CREATE TABLE dish_order_status (
    id SERIAL,
    dish_order_id INT REFERENCES dish_order(id) ON DELETE CASCADE ,
    PRIMARY KEY (id, dish_order_id),
    status status,
    creation_datetime TIMESTAMP
);