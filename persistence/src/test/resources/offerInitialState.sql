delete from auth;
delete  from user_role;
delete  from payment_method;
delete  from cryptocurrency;
delete  from users;

INSERT INTO user_role (description) VALUES ('supervisor')  ;
INSERT INTO user_role (description) VALUES ('seller') ;

INSERT INTO payment_method (code, payment_description) VALUES ('Cash', 'Paper money') ;

INSERT INTO users (id,email, rating_sum, rating_count) VALUES (0,'gbeade@itba.edu.ar', 0, 0) ;
INSERT INTO users (id,email, rating_sum, rating_count) VALUES (1,'shadad@itba.edu.ar', 0, 0) ;
INSERT INTO users (id,email, rating_sum, rating_count) VALUES (2,'mdedeu@itba.edu.ar', 0, 0) ;
INSERT INTO users (id,email, rating_sum, rating_count) VALUES (3,'scastagnino@itba.edu.ar', 0, 0) ;


INSERT INTO cryptocurrency (code, market_price, commercial_name) VALUES ('BTC', 22.1, 'Bitcoin') ;
INSERT INTO cryptocurrency (code, market_price, commercial_name) VALUES ('ETH', 54.3, 'Ether') ;
INSERT INTO cryptocurrency (code, market_price, commercial_name) VALUES ('ADA', 10.4, 'Cardano') ;
INSERT INTO cryptocurrency (code, market_price, commercial_name) VALUES ('DOT', 2.0, 'Polkadot') ;