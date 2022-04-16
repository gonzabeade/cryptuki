CREATE TABLE IF NOT EXISTS users (
    id INT IDENTITY PRIMARY KEY,
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
    id INT IDENTITY PRIMARY KEY,
    description VARCHAR(20) NOT NULL
    );

CREATE TABLE IF NOT EXISTS status (
    code VARCHAR(3) PRIMARY KEY,
    status_description VARCHAR(20) NOT NULL
    );

CREATE TABLE IF NOT EXISTS offer (
    id INT IDENTITY PRIMARY KEY,
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
