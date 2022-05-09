INSERT INTO user_role (description) VALUES ('supervisor');
INSERT INTO user_role (description) VALUES ('seller');

INSERT INTO payment_method (code, payment_description) VALUES ('Cash', 'Paper money');

INSERT INTO users (email, rating_sum, rating_count) VALUES ('gbeade@itba.edu.ar', 0, 0);
INSERT INTO users (email, rating_sum, rating_count) VALUES ('shadad@itba.edu.ar', 0, 0);
INSERT INTO users (email, rating_sum, rating_count) VALUES ('mdedeu@itba.edu.ar', 0, 0);
INSERT INTO users (email, rating_sum, rating_count) VALUES ('scastagnino@itba.edu.ar', 0, 0);


INSERT INTO cryptocurrency (code, market_price, commercial_name) VALUES ('BTC', 22.1, 'Bitcoin');
INSERT INTO cryptocurrency (code, market_price, commercial_name) VALUES ('ETH', 54.3, 'Ether');
INSERT INTO cryptocurrency (code, market_price, commercial_name) VALUES ('ADA', 10.4, 'Cardano');
INSERT INTO cryptocurrency (code, market_price, commercial_name) VALUES ('DOT', 2.0, 'Polkadot');
