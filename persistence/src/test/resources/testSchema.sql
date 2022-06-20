SET DATABASE SQL SYNTAX PGS TRUE;


CREATE TABLE IF NOT EXISTS users (
    id INT IDENTITY PRIMARY KEY,
    email VARCHAR(50) NOT NULL UNIQUE,
    rating_sum int NOT NULL CHECK( rating_sum >= 0),
    rating_count int NOT NULL CHECK( rating_count >= 0),
    phone_number VARCHAR(8),
    last_login TIMESTAMP DEFAULT '2022-05-01 02:08:03.777554' NOT NULL,
    rating INT DEFAULT 0 NOT NULL,
    locale VARCHAR(5) DEFAULT 'en_us'
);

CREATE TABLE user_role (
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
    FOREIGN KEY (role_id) REFERENCES user_role(id)
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
    offer_id INT IDENTITY PRIMARY KEY,
    seller_id INT NOT NULL,
    offer_date timestamp default now() NOT NULL,
    crypto_code VARCHAR(5) NOT NULL,
    status_code VARCHAR(3) NOT NULL,
    asking_price numeric NOT NULL,
    max_quantity numeric NOT NULL,
    min_quantity numeric DEFAULT 0,
    comments varchar(280) DEFAULT 'No comments.',
    location VARCHAR(100),
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
    FOREIGN KEY(offer_id) REFERENCES offer(offer_id) ON DELETE CASCADE,
    FOREIGN KEY(payment_code) REFERENCES payment_method(code) ON DELETE CASCADE
);

INSERT INTO status (code, status_description) VALUES ('DEL', 'Deleted by user');
INSERT INTO status (code, status_description) VALUES ('APR', 'Approved');
INSERT INTO status (code, status_description) VALUES ('PSE', 'Paused by Seller');
INSERT INTO status (code, status_description) VALUES ('PSU', 'Paused by Supervisor');

CREATE TABLE profile_pic(
    user_id INT,
    image_data VARBINARY(1000),
    image_type VARCHAR(1000),
    pic_id SERIAL NOT NULL PRIMARY KEY,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE trade (
    trade_id INT IDENTITY PRIMARY KEY,
    offer_id INT CHECK(offer_id >= 0),
    buyer_id INT CHECK(buyer_id >= 0),
    seller_id INT CHECK(seller_id >= 0),
    last_modified timestamp DEFAULT now(),
    status VARCHAR(10) DEFAULT 'PENDING',
    quantity NUMERIC CHECK(quantity >= 0),
    rated_buyer boolean DEFAULT false NOT NULL,
    rated_seller boolean DEFAULT false NOT NULL,
    q_unseen_msg_buyer int default 0 NOT NULL,
    q_unseen_msg_seller int default 0 NOT NULL,

    FOREIGN KEY (offer_id) REFERENCES offer(offer_id) ON DELETE SET NULL,
    FOREIGN KEY (seller_id) REFERENCES users (id) ON DELETE SET NULL,
    FOREIGN KEY (buyer_id) REFERENCES users (id) ON DELETE SET NULL
);

CREATE TABLE complain (
    complain_id INT IDENTITY PRIMARY KEY,
    trade_id INT,
    complainer_id INT NOT NULL,
    complainer_comments VARCHAR(140) NOT NULL,
    status VARCHAR(10) DEFAULT 'PENDING' CHECK ( status IN ('PENDING', 'CLOSED', 'ASSIGNED')) NOT NULL,
    moderator_comments VARCHAR(140),
    moderator_id INT,
    complain_date date DEFAULT now(),

    FOREIGN KEY (trade_id) REFERENCES trade(trade_id) ON DELETE SET NULL,
    FOREIGN KEY (complainer_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (moderator_id) REFERENCES users (id) ON DELETE NO ACTION
);

CREATE TABLE messages(
    message_id SERIAL,
    trade_id integer references trade not null,
    user_id integer references users not null,
    essage_content text not null ,
    message_date timestamp DEFAULT now() not null,
    primary key (trade_id,user_id,message_date)
);


create unique index auth_uname_uindex on auth (uname);

create unique index messages_message_id_uindex on messages (message_id);

create unique index profile_pic_user_id_uindex on profile_pic (user_id);

create table country
(
    iso VARCHAR(3) constraint countries_pk primary key,
    name VARCHAR(30) not null
);

INSERT INTO country VALUES ('ARG', 'Argentina');
INSERT INTO country VALUES ('URY', 'Uruguay');
INSERT INTO country VALUES ('CHL', 'Chile');

CREATE TABLE kyc (
                     kyc_id SERIAL PRIMARY KEY,
                     user_id INT,
                     given_names VARCHAR(50) NOT NULL,
                     surnames VARCHAR(50) NOT NULL,
                     emission_country VARCHAR(50) NOT NULL,
                     id_code VARCHAR(20) NOT NULL,
                     id_type VARCHAR(10) CHECK(id_type IN ('PASSPORT', 'ID')) NOT NULL,
                     id_photo VARBINARY(1000) NOT NULL,
                     validation_photo VARBINARY(1000) NOT NULL,
                     status varchar(3) CHECK(status IN ('APR', 'PEN', 'REJ', 'DEL')) DEFAULT 'PEN',
                     kyc_date timestamp NOT NULL DEFAULT now(),

                     FOREIGN KEY (emission_country) REFERENCES country(iso),
                     FOREIGN KEY (user_id) REFERENCES users(id)
);

create table kyc_Rejected_Audit as (select * from kyc) with no data;


CREATE view trade_complete AS
SELECT
    trade_id,
    offer_id,
    buyer_auth.uname as buyer_uname,
    seller_auth.uname as seller_uname,
    last_modified,
    trade.status,
    quantity,
    asking_price,
    crypto_code,
    commercial_name,
    rated_buyer,
    rated_seller
FROM trade
    JOIN offer ON trade.offer_id = offer.offer_id
    JOIN auth seller_auth ON offer.seller_id = seller_auth.user_id
    JOIN auth buyer_auth ON buyer_auth.user_id = trade.buyer_id
    JOIN cryptocurrency ON offer.crypto_code = cryptocurrency.code;

CREATE VIEW offer_complete as
SELECT offer.offer_id as offer_id,
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
       rating,
       phone_number,
       market_price,
       commercial_name,
       payment_code,
       status_description,
       payment_description,
       last_login,
       uname,
       location
FROM offer
         JOIN users ON offer.seller_id = users.id
         JOIN auth ON users.id = auth.user_id
         JOIN cryptocurrency c on offer.crypto_code = c.code
         LEFT OUTER JOIN payment_methods_at_offer pmao on offer.offer_id = pmao.offer_id
         JOIN status s on s.code = offer.status_code
         LEFT OUTER JOIN payment_method pm on pmao.payment_code = pm.code;

CREATE VIEW complain_complete AS
SELECT
    complain_id,
    t.trade_id trade_id,
    complainer_auth.uname complainer_uname,
    complainer_auth.user_id complainer_id,
    moderator_auth.uname as moderator_uname,
    moderator_auth.user_id moderator_id,
    complainer_comments,
    complain.status status,
    moderator_comments,
    complain_date,
    offer_id
FROM complain
         JOIN auth complainer_auth ON complainer_auth.user_id = complain.complainer_id
         LEFT OUTER JOIN auth moderator_auth ON moderator_auth.user_id = complain.moderator_id
         LEFT OUTER JOIN trade t on complain.trade_id = t.trade_id

-- create or replace function auditkyconreject() returns trigger
--     language plpgsql
-- as
-- $$
-- BEGIN
--     raise notice 'Auditing KYC rejection';
--     IF (new.status = 'REJ') THEN
--         INSERT INTO kyc_rejected_audit VALUES (
--                                                   old.kyc_id,
--                                                   old.given_names,
--                                                   old.surnames,
--                                                   old.emission_country,
--                                                   old.id_code,
--                                                   old.id_type,
--                                                   old.id_photo,
--                                                   old.validation_photo,
--                                                   new.status,
--                                                   old.kyc_date,
--                                                   old.id_photo_type,
--                                                   old.validation_photo_type,
--                                                   old.user_id
--                                               );
-- DELETE FROM kyc WHERE kyc.kyc_id = new.kyc_id;
-- END IF;
--
-- RETURN new;
-- END
-- $$;
--
-- DROP TRIGGER auditKycOnRejectTrigger on kyc;
-- CREATE TRIGGER auditKycOnRejectTrigger
--     AFTER UPDATE ON kyc
--     FOR EACH ROW
--     EXECUTE PROCEDURE auditkyconreject();

