CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(50) NOT NULL,
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
    id VARCHAR(3) PRIMARY KEY,
    market_price DECIMAL CHECK (market_price > 0),
    description VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_role (
    id SERIAL PRIMARY KEY,
    description VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS status (
    id SERIAL PRIMARY KEY,
    description VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS offer (
    offer_id SERIAL PRIMARY KEY,
    seller_id INT NOT NULL,
    offer_date TIMESTAMP NOT NULL,
    coin_id VARCHAR(3) NOT NULL,
    status_id INT NOT NULL,
    asking_price DECIMAL NOT NULL,
    coin_amount DECIMAL NOT NULL,
    FOREIGN KEY (coin_id) REFERENCES cryptocurrency(id) ON DELETE CASCADE,
    FOREIGN KEY (status_id) REFERENCES status(id) ON DELETE SET NULL,
    FOREIGN KEY (seller_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS payment_method (
    id SERIAL PRIMARY KEY,
    payment_name VARCHAR(20) NOT NULL,
    description VARCHAR(20)
);

CREATE TABLE payment_methods_at_offer (
    offer_id INT,
    payment_method_id INT,
    PRIMARY KEY(offer_id, payment_method_id),
    FOREIGN KEY(offer_id) REFERENCES offer(offer_id) ON DELETE CASCADE,
    FOREIGN KEY(payment_method_id) REFERENCES payment_method(id) ON DELETE CASCADE
);

INSERT INTO users (email, rating_sum, rating_count) VALUES ('gonza@paw.com', 0, 0) ;
INSERT INTO users (email, rating_sum, rating_count) VALUES ('salta@paw.com', 0, 0);
INSERT INTO users (email, rating_sum, rating_count) VALUES ('marquinhos@paw.com', 0, 0);
INSERT INTO users (email, rating_sum, rating_count) VALUES ('salcas@paw.com', 0, 0);

INSERT INTO user_role (description) VALUES ('supervisor');
INSERT INTO user_role (description) VALUES ('seller');

INSERT INTO cryptocurrency (id, market_price, description) VALUES ('BTC', 6789.0, 'Bitcoin');
INSERT INTO cryptocurrency (id, market_price, description) VALUES ('ETH', 12.0, 'Ether');
INSERT INTO cryptocurrency (id, market_price, description) VALUES ('DAI', 564.0, 'DAI Stablecoin');

INSERT INTO status (description) VALUES ('Pending');
INSERT INTO status (description) VALUES ('Approved');
INSERT INTO status (description) VALUES ('Rejected');
INSERT INTO status (description) VALUES ('PausedBySeller');
INSERT INTO status (description) VALUES ('PausedBySupervisor');

INSERT INTO offer (seller_id, offer_date, coin_id, status_id, asking_price, coin_amount)
VALUES (1, '2022-01-01', 'BTC', '2', 789.4, 2);

INSERT INTO offer (seller_id, offer_date, coin_id, status_id, asking_price, coin_amount)
VALUES (3, '2022-01-04', 'ETH', '1', 67.2, 1);





