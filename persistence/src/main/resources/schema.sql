CREATE TABLE IF NOT EXISTS offers (
    offer_id SERIAL PRIMARY KEY,
    seller_id INT NOT NULL,
    offer_date TIMESTAMP NOT NULL,
    coin_id TEXT NOT NULL,
    asking_price DECIMAL NOT NULL,
    coin_amount DECIMAL NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY,
    mail VARCHAR(50),
    uname VARCHAR(50),
    password VARCHAR(50),
    phone VARCHAR(50)
)


