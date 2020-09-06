
CREATE TABLE share_purchase_test
(
    id         SERIAL PRIMARY KEY,
    ticker     VARCHAR(255) NOT NULL,
    quantity   INT NOT NULL,
    price      FLOAT NOT NULL,
    commission FLOAT NOT NULL
);


CREATE TABLE dollar_purchase_test
(
    id         SERIAL PRIMARY KEY,
    quantity   INT NOT NULL,
    price      FLOAT NOT NULL,
    commission FLOAT NOT NULL
);
