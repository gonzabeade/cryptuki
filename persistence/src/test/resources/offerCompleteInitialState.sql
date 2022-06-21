delete from auth;
delete from users;
delete from cryptocurrency;
delete from offer;

alter table offer alter column offer_date set default now();
alter table users alter column last_login set default now();

INSERT INTO users (id,email, rating_sum, rating_count, phone_number, rating) VALUES (0,'gbeade@itba.edu.ar', 20, 4, '12345678', 0);
INSERT INTO users (id,email, rating_sum, rating_count, phone_number, rating) VALUES (1,'scastagnino@itba.edu.ar', 14, 7, '87654321', 0);

INSERT INTO auth (status, code, role_id, user_id, uname, password) VALUES (0, 1234, 1, 0, 'gbeade', 'pass_gbeade');
INSERT INTO auth (status, code, role_id, user_id, uname, password) VALUES (0, 4321, 1, 1, 'scastagnino', 'pass_scastagnino');

INSERT INTO cryptocurrency (code, commercial_name) VALUES ('BTC', 'Bitcoin');
INSERT INTO cryptocurrency (code, commercial_name) VALUES ('ETH', 'Ether');
