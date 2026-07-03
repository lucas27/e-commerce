CREATE TABLE IF NOT EXISTS orders(
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id),
    total_price NUMERIC(10, 2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS order_itens(
    id SERIAL PRIMARY KEY,
    order_id INT REFERENCES orders(id),
    product_id BIGINT REFERENCES Products(id),
    quantity INT NOT NULL,
    price NUMERIC(10, 2) NOT NULL
);