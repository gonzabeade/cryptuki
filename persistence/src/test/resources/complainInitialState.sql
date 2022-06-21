delete from auth;
--delete from user_role;
delete from users;
delete from cryptocurrency;
delete from offer;
delete from trade;

--TODO: mirar que onda los roles
--INSERT INTO user_role (id,description) VALUES (0,'ROLE_USER');
--INSERT INTO user_role (id,description) VALUES (1,'ROLE_ADMIN');

INSERT INTO users (id,email, rating_sum, rating_count, phone_number, rating) VALUES (0,'gbeade@itba.edu.ar', 20, 4, '12345678', 0);
INSERT INTO users (id,email, rating_sum, rating_count, phone_number, rating) VALUES (1,'scastagnino@itba.edu.ar', 14, 7, '87654321', 0);
INSERT INTO users (id,email, rating_sum, rating_count, phone_number, rating) VALUES (2,'mdedeu@itba.edu.ar', 14, 7, '87654321', 0);

INSERT INTO auth (status, code, role_id, user_id, uname, password) VALUES (1, 1234, 1, 0, 'gbeade', 'pass_gbeade');
INSERT INTO auth (status, code, role_id, user_id, uname, password) VALUES (1, 4321, 1, 1, 'scastagnino', 'pass_scastagnino');
INSERT INTO auth (status, code, role_id, user_id, uname, password) VALUES (1, 4322, 0, 2, 'mdedeu', 'pass_mdedeu');

INSERT INTO cryptocurrency (code, commercial_name) VALUES ('BTC', 'Bitcoin');
INSERT INTO cryptocurrency (code, commercial_name) VALUES ('ETH', 'Ether');

INSERT INTO offer (offer_id,seller_id, crypto_code, status_code, asking_price, max_quantity, min_quantity, comments, location, offer_date) VALUES (0,0, 'BTC', 'APR', 10.33, 4.2, 2.6, 'Im selling BTC', 'CABA', '2022-05-01 02:08:03');
INSERT INTO offer (offer_id,seller_id, crypto_code, status_code, asking_price, max_quantity, min_quantity, comments, location, offer_date) VALUES (1,0, 'ETH', 'APR', 6, 10.7, 1, 'Im selling ETH', 'La Plata', '2022-05-01 02:08:03');
INSERT INTO offer (offer_id,seller_id, crypto_code, status_code, asking_price, max_quantity, min_quantity, comments, location, offer_date) VALUES (2,1, 'ETH', 'APR', 6.5, 16.7, 0.4, 'Im selling ETH', 'Pilar', '2022-05-01 02:08:03');

INSERT INTO trade (trade_id,offer_id, buyer_id, quantity) VALUES (0,0, 1, 3.4);
INSERT INTO trade (trade_id ,offer_id, buyer_id, quantity) VALUES (1,0, 1, 5.6);
INSERT INTO trade (trade_id,offer_id, buyer_id, quantity) VALUES (2,0, 0, 3.4);
