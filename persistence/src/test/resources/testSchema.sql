CREATE TABLE IF NOT EXISTS users (
    id INT IDENTITY PRIMARY KEY,
    email VARCHAR(50) NOT NULL UNIQUE,
    rating_sum int NOT NULL CHECK( rating_sum >= 0),
    rating_count int NOT NULL CHECK( rating_count >= 0),
    phone_number VARCHAR(8)
    );

CREATE TABLE IF NOT EXISTS user_role (
        id INT IDENTITY PRIMARY KEY,
        description VARCHAR(20) NOT NULL
    );

CREATE TABLE IF NOT EXISTS auth (
    status INT CHECK(status = 0 OR status = 1),
    code INT,
    role_id INT NOT NULL,
    user_id INT PRIMARY KEY,
    uname VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES user_role
    );

CREATE TABLE IF NOT EXISTS cryptocurrency (
    code VARCHAR(5) PRIMARY KEY,
    market_price DECIMAL CHECK (market_price > 0),
    commercial_name VARCHAR(20) NOT NULL
    );

CREATE TABLE IF NOT EXISTS status (
    code VARCHAR(3) PRIMARY KEY,
    status_description VARCHAR(20) NOT NULL
    );

CREATE TABLE IF NOT EXISTS offer (
    id INT IDENTITY PRIMARY KEY,
    seller_id INT NOT NULL,
    offer_date DATE NOT NULL,
    crypto_code VARCHAR(5) NOT NULL,
    status_code VARCHAR(3) NOT NULL,
    asking_price DECIMAL NOT NULL,
    quantity DECIMAL NOT NULL,
    FOREIGN KEY (crypto_code) REFERENCES cryptocurrency(code) ON DELETE CASCADE,
    FOREIGN KEY (status_code) REFERENCES status(code) ON DELETE CASCADE,
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


CREATE VIEW offer_complete as
SELECT offer_id,
       seller_id,
       offer_date,
       crypto_code,
       status_code,
       asking_price,
       quantity,
       email,
       rating_sum,
       rating_count,
       phone_number,
       market_price,
       commercial_name,
       payment_code,
       status_description,
       payment_description
FROM offer
         JOIN users ON offer.seller_id = users.id
         JOIN cryptocurrency c on offer.crypto_code = c.code
         LEFT OUTER JOIN payment_methods_at_offer pmao on offer.id = pmao.offer_id
         JOIN status s on s.code = offer.status_code
         LEFT OUTER JOIN payment_method pm on pmao.payment_code = pm.code;

alter table offer
    rename column quantity to max_quantity;

alter table offer
    add min_quantity numeric default 0;

drop view offer_complete;

INSERT INTO status (code, status_description) VALUES ('DEL', 'Deleted by user');

alter table users
    add last_login timestamp default '2022-05-01 02:08:03.777554' not null;

CREATE VIEW offer_complete as
SELECT offer_id,
       seller_id,
       offer_date,
       crypto_code,
       status_code,
       asking_price,
       min_quantity,
       max_quantity,
       email,
       rating_sum,
       rating_count,
       phone_number,
       market_price,
       commercial_name,
       payment_code,
       status_description,
       payment_description,
       last_login,
       uname
FROM offer
         JOIN users ON offer.seller_id = users.id
         JOIN auth ON users.id = auth.user_id
         JOIN cryptocurrency c on offer.crypto_code = c.code
         LEFT OUTER JOIN payment_methods_at_offer pmao on offer.id = pmao.offer_id
         JOIN status s on s.code = offer.status_code
         LEFT OUTER JOIN payment_method pm on pmao.payment_code = pm.code;

create unique index user_role_description_uindex on user_role (description);
CREATE TABLE IF NOT EXISTS profile_pic(
                                          user_id INT PRIMARY KEY,
                                          image_data BYTEA,
                                          image_type TEXT,

                                          FOREIGN KEY (user_id) REFERENCES users (id)
    );
CREATE TABLE trade (
                       trade_id INT IDENTITY PRIMARY KEY,
                       offer_id INT CHECK(offer_id >= 0),
                       buyer_id INT CHECK(buyer_id >= 0),
                       seller_id INT CHECK (seller_id >=0),
                       start_date timestamp,
                       status VARCHAR(10) DEFAULT 'OPEN' CHECK(status IN ('OPEN', 'CLOSED')),
                       quantity NUMERIC CHECK(quantity >= 0),

                       FOREIGN KEY (offer_id) REFERENCES offer (id) ON DELETE SET NULL,
                       FOREIGN KEY (seller_id) REFERENCES users (id) ON DELETE SET NULL,
                       FOREIGN KEY (buyer_id) REFERENCES users (id) ON DELETE SET NULL
);

CREATE TABLE complain (
                          complain_id INT IDENTITY PRIMARY KEY,
                          trade_id INT,
                          complainer_id INT NOT NULL,
                          complainer_comments VARCHAR(140) NOT NULL,
                          status VARCHAR(10) NOT NULL DEFAULT 'PENDING' CHECK ( status IN ('PENDING', 'CLOSED', 'ASSIGNED')),
                          moderator_comments VARCHAR(140),
                          moderator_id INT,

                          FOREIGN KEY (trade_id) REFERENCES trade (trade_id) ON DELETE SET NULL,
                          FOREIGN KEY (complainer_id) REFERENCES users (id) ON DELETE SET NULL,
                          FOREIGN KEY (moderator_id) REFERENCES users (id) ON DELETE NO ACTION
);

CREATE VIEW trade_complete AS
SELECT
    trade_id,
    offer_id,
    buyer_auth.uname as buyer_uname,
    seller_auth.uname as seller_uname,
    start_date,
    trade.status,
    quantity
FROM trade
         JOIN offer ON trade.offer_id = offer.id
         JOIN auth seller_auth ON offer.seller_id = seller_auth.user_id
         JOIN auth buyer_auth ON buyer_auth.user_id = trade.buyer_id;

CREATE VIEW complain_complete AS
SELECT
    complain_id,
    trade_id,
    complainer_auth.uname complainer_uname,
    moderator_auth.uname as moderator_uname,
    complainer_comments,
    complain.status status,
    moderator_comments
FROM complain
         JOIN auth complainer_auth ON complainer_auth.user_id = complain.complainer_id
         LEFT OUTER JOIN auth moderator_auth ON moderator_auth.user_id = complain.moderator_id

alter table offer
    rename column quantity to max_quantity;
alter table offer
    add min_quantity numeric default 0;
drop view offer_complete;
INSERT INTO status (code, status_description) VALUES ('DEL', 'Deleted by user');
alter table users
    add last_login timestamp default '2022-05-01 02:08:03.777554' not null;
CREATE VIEW offer_complete as
SELECT offer_id,
       seller_id,
       offer_date,
       crypto_code,
       status_code,
       asking_price,
       min_quantity,
       max_quantity,
       email,
       rating_sum,
       rating_count,
       phone_number,
       market_price,
       commercial_name,
       payment_code,
       status_description,
       payment_description,
       last_login,
       uname
FROM offer
         JOIN users ON offer.seller_id = users.id
         JOIN auth ON users.id = auth.user_id
         JOIN cryptocurrency c on offer.crypto_code = c.code
         LEFT OUTER JOIN payment_methods_at_offer pmao on offer.id = pmao.offer_id
         JOIN status s on s.code = offer.status_code
         LEFT OUTER JOIN payment_method pm on pmao.payment_code = pm.code;
create unique index user_role_description_uindex
    on user_role (description);
CREATE TABLE IF NOT EXISTS profile_pic(
                                          user_id INT PRIMARY KEY,
                                          image_data BYTEA,
                                          image_type TEXT,

                                          FOREIGN KEY (user_id) REFERENCES users (id)
    );
CREATE TABLE trade (
                       trade_id INT IDENTITY PRIMARY KEY,
                       offer_id INT CHECK(offer_id >= 0),
                       buyer_id INT CHECK(offer_id >= 0),
                       start_date timestamp,
                       status VARCHAR(10) DEFAULT 'OPEN' CHECK(status IN ('OPEN', 'CLOSED')),
                       quantity NUMERIC CHECK(quantity >= 0),

                       FOREIGN KEY (offer_id) REFERENCES offer (id) ON DELETE SET NULL,
                       FOREIGN KEY (buyer_id) REFERENCES users (id) ON DELETE SET NULL
);

CREATE TABLE complain (
                          complain_id INT IDENTITY PRIMARY KEY,
                          trade_id INT,
                          complainer_id INT NOT NULL,
                          complainer_comments VARCHAR(140) NOT NULL,
                          status VARCHAR(10) NOT NULL DEFAULT 'PENDING' CHECK ( status IN ('PENDING', 'CLOSED', 'ASSIGNED')),
                          moderator_comments VARCHAR(140),
                          moderator_id INT,

                          FOREIGN KEY (trade_id) REFERENCES trade (trade_id) ON DELETE SET NULL,
                          FOREIGN KEY (complainer_id) REFERENCES users (id) ON DELETE SET NULL,
                          FOREIGN KEY (moderator_id) REFERENCES users (id) ON DELETE NO ACTION
);

CREATE VIEW trade_complete AS
SELECT
    trade_id,
    offer_id,
    buyer_auth.uname as buyer_uname,
    seller_auth.uname as seller_uname,
    start_date,
    trade.status,
    quantity
FROM trade
         JOIN offer ON trade.offer_id = offer.id
         JOIN auth seller_auth ON offer.seller_id = seller_auth.user_id
         JOIN auth buyer_auth ON buyer_auth.user_id = trade.buyer_id;

CREATE VIEW complain_complete AS
SELECT
    complain_id,
    trade_id,
    complainer_auth.uname complainer_uname,
    moderator_auth.uname as moderator_uname,
    complainer_comments,
    complain.status status,
    moderator_comments
FROM complain
         JOIN auth complainer_auth ON complainer_auth.user_id = complain.complainer_id
         LEFT OUTER JOIN auth moderator_auth ON moderator_auth.user_id = complain.moderator_id;

DROP VIEW trade_complete;
CREATE VIEW trade_complete AS
SELECT
    trade_id,
    offer_id,
    buyer_auth.uname as buyer_uname,
    seller_auth.uname as seller_uname,
    start_date,
    trade.status,
    quantity,
    asking_price,
    crypto_code,
    commercial_name
FROM trade
         JOIN offer ON trade.offer_id = offer.id
         JOIN auth seller_auth ON offer.seller_id = seller_auth.user_id
         JOIN auth buyer_auth ON buyer_auth.user_id = trade.buyer_id
         JOIN cryptocurrency ON offer.crypto_code = cryptocurrency.code

alter table offer
    add comments varchar(280) default 'No comments.';

DROP VIEW offer_complete;
CREATE VIEW offer_complete as
SELECT offer_id,
       seller_id,
       offer.comments as comments,
       offer_date,
       crypto_code,
       status_code,
       asking_price,
       min_quantity,
       max_quantity,
       email,
       rating_sum,
       rating_count,
       phone_number,
       market_price,
       commercial_name,
       payment_code,
       status_description,
       payment_description,
       last_login,
       uname
FROM offer
         JOIN users ON offer.seller_id = users.id
         JOIN auth ON users.id = auth.user_id
         JOIN cryptocurrency c on offer.crypto_code = c.code
         LEFT OUTER JOIN payment_methods_at_offer pmao on offer.id = pmao.offer_id
         JOIN status s on s.code = offer.status_code
         LEFT OUTER JOIN payment_method pm on pmao.payment_code = pm.code;

alter table complain
    add complain_date date default now()

DROP VIEW complain_complete;
CREATE VIEW complain_complete AS
SELECT
    complain_id,
    t.trade_id trade_id,
    complainer_auth.uname complainer_uname,
    moderator_auth.uname as moderator_uname,
    complainer_comments,
    complain.status status,
    moderator_comments,
    complain_date,
    offer_id
FROM complain
         JOIN auth complainer_auth ON complainer_auth.user_id = complain.complainer_id
         LEFT OUTER JOIN auth moderator_auth ON moderator_auth.user_id = complain.moderator_id
         LEFT OUTER JOIN trade t on complain.trade_id = t.trade_id