CREATE TABLE inventory
(
   id               SERIAL PRIMARY KEY,
   name             VARCHAR(50)  NOT NULL UNIQUE,
   price            INTEGER       NOT NULL,
   department       VARCHAR(50),
   creation_date    TIMESTAMP
);

CREATE TABLE users
(
   id               SERIAL PRIMARY KEY,
   email             VARCHAR(50)  NOT NULL UNIQUE,
   password          VARCHAR(50)       NOT NULL,
   salt             VARCHAR(50)         NOT NULL,
   user_type        VARCHAR(50)
);