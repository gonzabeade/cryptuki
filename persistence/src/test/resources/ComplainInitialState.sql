INSERT INTO user_role (description) VALUES ('supervisor');
INSERT INTO user_role (description) VALUES ('seller');

INSERT INTO users (email, rating_sum, rating_count, phone_number, last_login) VALUES ('gbeade@itba.edu.ar', 20, 4, '12345678', '2022-05-01 02:08:03');
INSERT INTO users (email, rating_sum, rating_count, phone_number, last_login) VALUES ('scastagnino@itba.edu.ar', 14, 7, '87654321', '2022-05-01 02:08:03');

INSERT INTO auth (status, code, role_id, user_id, uname, password) VALUES (0, 1234, 1, 0, 'gbeade', 'pass_gbeade');
INSERT INTO auth (status, code, role_id, user_id, uname, password) VALUES (0, 4321, 1, 1, 'scastagnino', 'pass_scastagnino');

INSERT INTO cryptocurrency (code, market_price, commercial_name) VALUES ('BTC', 22.1, 'Bitcoin');
INSERT INTO cryptocurrency (code, market_price, commercial_name) VALUES ('ETH', 54.3, 'Ether');

INSERT INTO offer (seller_id, offer_date, crypto_code, status_code, asking_price, max_quantity, min_quantity, comments) VALUES (0, '2020-11-01 01:18:33', 'BTC', 'APR', 10.33, 4.2, 2.6, 'Im selling BTC');
INSERT INTO offer (seller_id, offer_date, crypto_code, status_code, asking_price, max_quantity, min_quantity, comments) VALUES (0, '2003-04-27 21:03:02', 'ETH', 'APR', 6, 10.7, 1, 'Im selling ETH');
INSERT INTO offer (seller_id, offer_date, crypto_code, status_code, asking_price, max_quantity, min_quantity, comments) VALUES (1, '2001-06-27 22:05:02', 'ETH', 'APR', 6.5, 16.7, 0.4, 'Im selling ETH');

INSERT INTO trade (offer_id, buyer_id, seller_id, start_date, quantity) VALUES (0, 1, 0, '2010-13-01 01:18:33', 3.4);
INSERT INTO trade (offer_id, buyer_id, seller_id, start_date, quantity) VALUES (0, 1, 0, '2011-11-01 04:19:35', 5.6);
INSERT INTO trade (offer_id, buyer_id, seller_id, start_date, quantity) VALUES (0, 0, 1, '2020-14-01 11:18:33', 3.4);
