CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(50) NOT NULL UNIQUE,
    rating_sum int NOT NULL CHECK( rating_sum >= 0),
    rating_count int NOT NULL CHECK( rating_count >= 0),
    phone_number VARCHAR(8)
    );

CREATE TABLE IF NOT EXISTS auth (
    user_id INT PRIMARY KEY,
    uname VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    session_id int,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS cryptocurrency (
    code VARCHAR(5) PRIMARY KEY,
    market_price DECIMAL CHECK (market_price > 0),
    commercial_name VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_role (
    id SERIAL PRIMARY KEY,
    description VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS status (
    code VARCHAR(3) PRIMARY KEY,
    status_description VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS offer (
    id SERIAL PRIMARY KEY,
    seller_id INT NOT NULL,
    offer_date TIMESTAMP NOT NULL,
    crypto_code VARCHAR(5) NOT NULL,
    status_code VARCHAR(3) NOT NULL,
    asking_price DECIMAL NOT NULL,
    quantity DECIMAL NOT NULL,
    FOREIGN KEY (crypto_code) REFERENCES cryptocurrency(code) ON DELETE CASCADE,
    FOREIGN KEY (status_code) REFERENCES status(code) ON DELETE SET NULL,
    FOREIGN KEY (seller_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS payment_method (
    code VARCHAR(5) PRIMARY KEY,
    payment_description VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS payment_methods_at_offer (
    offer_id INT,
    payment_code VARCHAR(5),
    PRIMARY KEY(offer_id, payment_code),
    FOREIGN KEY(offer_id) REFERENCES offer(id) ON DELETE CASCADE,
    FOREIGN KEY(payment_code) REFERENCES payment_method(code) ON DELETE CASCADE
);

INSERT INTO users (email, rating_sum, rating_count) VALUES ('gbeade@itba.edu.ar', 0, 0) ON CONFLICT DO NOTHING;
INSERT INTO users (email, rating_sum, rating_count) VALUES ('shadad@itba.edu.ar', 0, 0) ON CONFLICT DO NOTHING;
INSERT INTO users (email, rating_sum, rating_count) VALUES ('mdedeu@itba.edu.ar', 0, 0) ON CONFLICT DO NOTHING;
INSERT INTO users (email, rating_sum, rating_count) VALUES ('scastagnino@itba.edu.ar', 0, 0) ON CONFLICT DO NOTHING;

INSERT INTO user_role (description) VALUES ('supervisor');
INSERT INTO user_role (description) VALUES ('seller');

INSERT INTO cryptocurrency (code, market_price, commercial_name) VALUES ('BTC', 6789.0, 'Bitcoin') ON CONFLICT DO NOTHING;
INSERT INTO cryptocurrency (code, market_price, commercial_name) VALUES ('ETH', 12.0, 'Ether') ON CONFLICT DO NOTHING;
INSERT INTO cryptocurrency (code, market_price, commercial_name) VALUES ('DAI', 564.0, 'DAI Stable') ON CONFLICT DO NOTHING;
INSERT INTO cryptocurrency (code, market_price, commercial_name) VALUES ('USDT', 67.0, 'USDT Stable') ON CONFLICT DO NOTHING;
INSERT INTO cryptocurrency (code, market_price, commercial_name) VALUES ('DOGE', 86.0, 'Dogecoin') ON CONFLICT DO NOTHING;
INSERT INTO cryptocurrency (code, market_price, commercial_name) VALUES ('ADA', 10.0, 'Cardano') ON CONFLICT DO NOTHING;


INSERT INTO status (code, status_description) VALUES ('PEN', 'Pending') ON CONFLICT DO NOTHING;
INSERT INTO status (code, status_description) VALUES ('APR', 'Approved') ON CONFLICT DO NOTHING;
INSERT INTO status (code, status_description) VALUES ('REJ', 'Rejected') ON CONFLICT DO NOTHING;
INSERT INTO status (code, status_description) VALUES ('PSE', 'Paused by Seller') ON CONFLICT DO NOTHING;
INSERT INTO status (code, status_description) VALUES ('PSU', 'Paused by Supervisor') ON CONFLICT DO NOTHING;

INSERT INTO payment_method (code, payment_description) VALUES ('mp', 'MercadoPago') ON CONFLICT DO NOTHING;
INSERT INTO payment_method (code, payment_description) VALUES ('bru', 'Brubank') ON CONFLICT DO NOTHING;
INSERT INTO payment_method (code, payment_description) VALUES ('cas', 'Cash') ON CONFLICT DO NOTHING;


INSERT INTO offer (seller_id, offer_date, crypto_code, status_code, asking_price, quantity)
VALUES (1, '2022-01-01', 'BTC', 'APR', 34.7, 7)
ON CONFLICT DO NOTHING;

INSERT INTO offer (seller_id, offer_date, crypto_code, status_code, asking_price, quantity)
VALUES (1, '2022-01-01', 'ETH', 'APR', 23.4, 2)
ON CONFLICT DO NOTHING;

INSERT INTO offer (seller_id, offer_date, crypto_code, status_code, asking_price, quantity)
VALUES (1, '2022-01-01', 'DAI', 'APR', 12.8, 1)
ON CONFLICT DO NOTHING;

INSERT INTO offer (seller_id, offer_date, crypto_code, status_code, asking_price, quantity)
VALUES (1, '2022-01-01', 'DAI', 'APR', 200.4, 1)
ON CONFLICT DO NOTHING;

INSERT INTO offer (seller_id, offer_date, crypto_code, status_code, asking_price, quantity)
VALUES (1, '2022-01-01', 'ADA', 'APR', 234.9, 4)
ON CONFLICT DO NOTHING;

INSERT INTO offer (seller_id, offer_date, crypto_code, status_code, asking_price, quantity)
VALUES (1, '2022-01-01', 'DOGE', 'APR', 789.4, 2)
ON CONFLICT DO NOTHING;

INSERT INTO payment_methods_at_offer (offer_id, payment_code) VALUES (1, 'bru') ON CONFLICT DO NOTHING ;
INSERT INTO payment_methods_at_offer (offer_id, payment_code) VALUES (1, 'mp') ON CONFLICT DO NOTHING;
INSERT INTO payment_methods_at_offer (offer_id, payment_code) VALUES (2, 'mp') ON CONFLICT DO NOTHING;
INSERT INTO payment_methods_at_offer (offer_id, payment_code) VALUES (3, 'bru') ON CONFLICT DO NOTHING;
INSERT INTO payment_methods_at_offer (offer_id, payment_code) VALUES (4, 'bru') ON CONFLICT DO NOTHING;
INSERT INTO payment_methods_at_offer (offer_id, payment_code) VALUES (4, 'mp') ON CONFLICT DO NOTHING;
INSERT INTO payment_methods_at_offer (offer_id, payment_code) VALUES (4, 'bru') ON CONFLICT DO NOTHING;
INSERT INTO payment_methods_at_offer (offer_id, payment_code) VALUES (5, 'cas') ON CONFLICT DO NOTHING;
INSERT INTO payment_methods_at_offer (offer_id, payment_code) VALUES (5, 'mp') ON CONFLICT DO NOTHING;
INSERT INTO payment_methods_at_offer (offer_id, payment_code) VALUES (6, 'mp') ON CONFLICT DO NOTHING;
INSERT INTO payment_methods_at_offer (offer_id, payment_code) VALUES (6, 'cas') ON CONFLICT DO NOTHING;
INSERT INTO payment_methods_at_offer (offer_id, payment_code) VALUES (6, 'bru') ON CONFLICT DO NOTHING;










