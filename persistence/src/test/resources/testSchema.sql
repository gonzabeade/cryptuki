SET DATABASE SQL SYNTAX PGS TRUE;

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
       commercial_name,
       last_login,
       uname,
       location
FROM offer
         JOIN users ON offer.seller_id = users.id
         JOIN auth ON users.id = auth.user_id
         JOIN cryptocurrency c on offer.crypto_code = c.code

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
