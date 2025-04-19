CREATE TABLE best_sales (
                            id SERIAL PRIMARY KEY,
                            updated_at TIMESTAMP,
                            sale_id INT REFERENCES sale(id)
);
