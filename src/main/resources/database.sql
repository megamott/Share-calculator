CREATE TABLE dollar_purchase
(
    id         SERIAL PRIMARY KEY,
    quantity   INT NOT NULL,
    price      FLOAT NOT NULL,
    commission FLOAT NOT NULL
);

CREATE TABLE share_purchase
(
    id         SERIAL PRIMARY KEY,
    ticker     VARCHAR(255) NOT NULL,
    quantity   INT NOT NULL,
    price      FLOAT NOT NULL,
    commission FLOAT NOT NULL
);
