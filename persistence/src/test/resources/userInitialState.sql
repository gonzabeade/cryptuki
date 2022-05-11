TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
delete from user_role;

INSERT INTO user_role (id,description) VALUES (0,'supervisor');
INSERT INTO user_role (id,description) VALUES (1,'seller');

