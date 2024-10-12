CREATE TABLE users
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name   VARCHAR(255) NOT NULL,
    last_name    VARCHAR(255) NOT NULL,
    email        VARCHAR(255) NOT NULL UNIQUE,
    password     VARCHAR(255) NOT NULL,
    role         VARCHAR(50)  NOT NULL,
    is_active    BOOLEAN DEFAULT TRUE,
    is_logged_in BOOLEAN DEFAULT FALSE
);
