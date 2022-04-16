INSERT INTO users (email, rating_sum, rating_count) VALUES ('gbeade@itba.edu.ar', 0, 0) ON CONFLICT DO NOTHING;
INSERT INTO users (email, rating_sum, rating_count) VALUES ('shadad@itba.edu.ar', 0, 0) ON CONFLICT DO NOTHING;
INSERT INTO users (email, rating_sum, rating_count) VALUES ('mdedeu@itba.edu.ar', 0, 0) ON CONFLICT DO NOTHING;
INSERT INTO users (email, rating_sum, rating_count) VALUES ('scastagnino@itba.edu.ar', 0, 0) ON CONFLICT DO NOTHING;

INSERT INTO user_role (description) VALUES ('supervisor') ON CONFLICT DO NOTHING;
INSERT INTO user_role (description) VALUES ('seller') ON CONFLICT DO NOTHING;

INSERT INTO cryptocurrency (code, market_price, commercial_name) VALUES ('BTC', 22.1, 'Bitcoin') ON CONFLICT DO NOTHING;
INSERT INTO cryptocurrency (code, market_price, commercial_name) VALUES ('ETH', 54.3, 'Ether') ON CONFLICT DO NOTHING;
INSERT INTO cryptocurrency (code, market_price, commercial_name) VALUES ('ADA', 10.4, 'Cardano') ON CONFLICT DO NOTHING;
INSERT INTO cryptocurrency (code, market_price, commercial_name) VALUES ('DOT', 2.0, 'Polkadot') ON CONFLICT DO NOTHING;
