CREATE TYPE status AS ENUM ('IN_PROGRESS', 'FINISHED', 'DELIVERED');

CREATE TABLE dish_order (
                            id SERIAL PRIMARY KEY,
                            dish_id INT REFERENCES dish(id),
                            sales_point_id INT REFERENCES sales_point(id),
                            quantity INT,
                            order_status status
);
