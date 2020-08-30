CREATE TABLE dollar_purchase
(
    id         SERIAL PRIMARY KEY,
    quantity   INT NOT NULL,
    price      FLOAT NOT NULL,
    commission FLOAT NOT NULL
);