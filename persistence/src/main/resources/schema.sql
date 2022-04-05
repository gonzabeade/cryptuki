CREATE TABLE IF NOT EXISTS offers(
    offer_id SERIAL PRIMARY KEY,
    seller_id INT NOT NULL,
    offer_date TIMESTAMP NOT NULL,
    coin_id TEXT NOT NULL,
    asking_price DOUBLE NOT NULL,
    coin_amount DOUBLE NOT NULL);


