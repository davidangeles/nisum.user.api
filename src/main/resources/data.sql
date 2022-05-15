DROP TABLE IF EXISTS USER;

CREATE TABLE USER
(
    ID           VARCHAR(36) NOT NULL,
    NAME         VARCHAR(100) NOT NULL,
    EMAIL        VARCHAR(120) NOT NULL,
    PASSWORD     VARCHAR(100) NOT NULL,
    IS_ACTIVE    TINYINT NOT NULL,
    TOKEN        VARCHAR(250) NOT NULL,
    LAST_LOGIN   DATETIME DEFAULT CURRENT_TIMESTAMP(),
    CREATED_DATE DATETIME DEFAULT CURRENT_TIMESTAMP(),
    MODIFIED_DATE DATETIME DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    PRIMARY KEY (ID),
    UNIQUE KEY EMAIL_UNIQUE (EMAIL)
);

DROP TABLE IF EXISTS PHONE;

CREATE TABLE PHONE
(
    ID           BIGINT AUTO_INCREMENT PRIMARY KEY,
    USER_ID      VARCHAR(36) NOT NULL,
    NUMBER       VARCHAR(15) NOT NULL,
    CITY_CODE    VARCHAR(6) NOT NULL,
    COUNTRY_CODE VARCHAR(6) NOT NULL,
    CREATED_DATE DATETIME DEFAULT CURRENT_TIMESTAMP(),
    MODIFIED_DATE DATETIME DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    FOREIGN KEY (USER_ID) REFERENCES USER (ID)
);

INSERT INTO USER(ID, NAME, EMAIL, PASSWORD, IS_ACTIVE, TOKEN, LAST_LOGIN)
VALUES ('5fa78ebf-01a3-4e68-b8ad-eea4630cd661', 'Juan Rodriguez', 'juan1@rodriguez.cl', '$2y$12$aHQHe79Ky9ILrb1jpFX/BOWeEY35gwnjVEdeVCkiLqhoF.ltE9av.',
        1, 'eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2NTI1NjAzOTYsImlzcyI6Imh0dHBzOi8vd3d3Lm5pc3VtLmNvbSIsInN1YiI6Imp1YW40QHJvZHJpZ3Vlei5jbCIsImV4cCI6MTY1MjU2NzU5Nn0.ViOYx0RGUFWYhJH-lg_X2oW61MkELdQwyj6LzQxEMgn6G7N3i-hI8Xrc0H5vWPpAqx1USlU6Fu_3zzNtRSKtKQ',
        CURRENT_TIMESTAMP());

INSERT INTO PHONE(ID, USER_ID, NUMBER, CITY_CODE, COUNTRY_CODE)
VALUES (1, '5fa78ebf-01a3-4e68-b8ad-eea4630cd661', '1234567', '1', '57');