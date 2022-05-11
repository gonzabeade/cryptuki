delete from auth;
delete from user_role;
delete from users;

INSERT INTO user_role (id,description) VALUES (0,'supervisor');
INSERT INTO user_role (id,description) VALUES (1,'buyer');
INSERT INTO user_role (id,description) VALUES (2,'seller');

INSERT INTO users (id,email, rating_sum, rating_count, phone_number, last_login) VALUES (0,'gbeade@itba.edu.ar', 20, 4, '12345678', '2022-05-01 02:08:03');
INSERT INTO users (id,email, rating_sum, rating_count, phone_number, last_login) VALUES (1,'scastagnino@itba.edu.ar', 14, 7, '87654321', '2022-05-01 02:08:03');
