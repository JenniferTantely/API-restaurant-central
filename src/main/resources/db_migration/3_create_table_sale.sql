CREATE TABLE sale (
                      id SERIAL PRIMARY KEY,
                      sales_point_id INT REFERENCES sales_point(id) NOT NULL,
                      dish_id INT REFERENCES dish(id) NOT NULL,
                      quantity_sold INTEGER NOT NULL,
                      total_amount NUMERIC NOT NULL
);
