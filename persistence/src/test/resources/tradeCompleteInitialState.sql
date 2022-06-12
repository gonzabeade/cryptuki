drop table trade_complete;

delete from auth;
delete from user_role;
delete from users;
delete from cryptocurrency;
delete from offer;
delete from trade;


INSERT INTO user_role (id,description) VALUES (0,'supervisor');
INSERT INTO user_role (id,description) VALUES (1,'seller');

INSERT INTO users (id,email, rating_sum, rating_count, phone_number, last_login) VALUES (0,'gbeade@itba.edu.ar', 20, 4, '12345678', '2022-05-01 02:08:03');
INSERT INTO users (id,email, rating_sum, rating_count, phone_number, last_login) VALUES (1,'scastagnino@itba.edu.ar', 14, 7, '87654321', '2022-05-01 02:08:03');
INSERT INTO users (id,email, rating_sum, rating_count, phone_number, last_login) VALUES (2,'mdedeu@itba.edu.ar', 14, 7, '87654321', '2022-05-01 02:08:03');

INSERT INTO auth (status, code, role_id, user_id, uname, password) VALUES (0, 1234, 1, 0, 'gbeade', 'pass_gbeade');
INSERT INTO auth (status, code, role_id, user_id, uname, password) VALUES (0, 4321, 1, 1, 'scastagnino', 'pass_scastagnino');
INSERT INTO auth (status, code, role_id, user_id, uname, password) VALUES (0, 4322, 0, 2, 'mdedeu', 'pass_mdedeu');

INSERT INTO cryptocurrency (code, market_price, commercial_name) VALUES ('BTC', 22.1, 'Bitcoin');
INSERT INTO cryptocurrency (code, market_price, commercial_name) VALUES ('ETH', 54.3, 'Ether');

INSERT INTO offer (offer_id,seller_id, offer_date, crypto_code, status_code, asking_price, max_quantity, min_quantity, comments) VALUES (0,0, '2020-11-01 01:18:33', 'BTC', 'APR', 10.33, 4.2, 2.6, 'Im selling BTC');
INSERT INTO offer (offer_id,seller_id, offer_date, crypto_code, status_code, asking_price, max_quantity, min_quantity, comments) VALUES (1,0, '2003-04-27 21:03:02', 'ETH', 'APR', 6, 10.7, 1, 'Im selling ETH');
INSERT INTO offer (offer_id,seller_id, offer_date, crypto_code, status_code, asking_price, max_quantity, min_quantity, comments) VALUES (2,1, '2001-06-27 22:05:02', 'ETH', 'APR', 6.5, 16.7, 0.4, 'Im selling ETH');

INSERT INTO trade (trade_id,offer_id, buyer_id, seller_id, start_date, quantity) VALUES (0,0, 1, 0, '2020-11-01 01:18:33', 3.4);
INSERT INTO trade (trade_id ,offer_id, buyer_id, seller_id, start_date, quantity) VALUES (1,0, 1, 0, '2020-11-01 01:18:33', 5.6);
INSERT INTO trade (trade_id,offer_id, buyer_id, seller_id, start_date, quantity) VALUES (2,0, 0, 1, '2020-11-01 01:18:33', 3.4);

CREATE view  trade_complete AS
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
    commercial_name,
    rated_buyer,
    rated_seller
FROM trade
         JOIN offer ON trade.offer_id = offer.offer_id
         JOIN auth seller_auth ON offer.seller_id = seller_auth.user_id
         JOIN auth buyer_auth ON buyer_auth.user_id = trade.buyer_id
         JOIN cryptocurrency ON offer.crypto_code = cryptocurrency.code;