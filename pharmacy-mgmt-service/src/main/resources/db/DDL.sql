CREATE TABLE roles
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE users
(
    id                      BIGSERIAL PRIMARY KEY,
    username                VARCHAR(100) NOT NULL UNIQUE,
    password                VARCHAR(255) NOT NULL,
    email                   VARCHAR(150),
    phone_number            VARCHAR(20),

    enabled                 BOOLEAN      NOT NULL DEFAULT TRUE,
    account_non_expired     BOOLEAN      NOT NULL DEFAULT TRUE,
    account_non_locked      BOOLEAN      NOT NULL DEFAULT TRUE,
    credentials_non_expired BOOLEAN      NOT NULL DEFAULT TRUE,
    credentials_expiry_date TIMESTAMP,

    failed_login_attempts   INT          NOT NULL DEFAULT 0,
    last_failed_login       TIMESTAMP,

    created_at              TIMESTAMP             DEFAULT CURRENT_TIMESTAMP,
    updated_at              TIMESTAMP             DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE users
    ADD COLUMN email VARCHAR(150) UNIQUE,
ADD COLUMN phone_number VARCHAR(20);

CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,

    PRIMARY KEY (user_id, role_id),

    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_role
        FOREIGN KEY (role_id)
            REFERENCES roles (id)
);

INSERT INTO roles (name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER'),
       ('ROLE_PHARMACIST');

CREATE TABLE customers
(
    id     BIGSERIAL PRIMARY KEY,

    name   VARCHAR(150) NOT NULL,
    email  VARCHAR(150),
    phone  VARCHAR(15),
    gender VARCHAR(10),
    dob    DATE
);

ALTER TABLE customers
    ADD CONSTRAINT uq_customers_phone UNIQUE (phone);

ALTER TABLE customers
    ADD CONSTRAINT chk_gender
        CHECK (gender IN ('MALE', 'FEMALE', 'OTHER'));

-------------------------------------------------
CREATE TABLE sales
(
    id             BIGSERIAL PRIMARY KEY,

    customer_id    BIGINT         NOT NULL REFERENCES customers (id),
    sold_by        BIGINT         NOT NULL REFERENCES users (id),

    total_amount   NUMERIC(10, 2) NOT NULL,
    payment_method VARCHAR(20),
    sale_date      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sale_items
(
    id          BIGSERIAL PRIMARY KEY,

    sale_id     BIGINT         NOT NULL REFERENCES sales (id) ON DELETE CASCADE,
    medicine_id BIGINT         NOT NULL REFERENCES medicines (id),

    quantity    INT            NOT NULL,
    unit_price  NUMERIC(10, 2) NOT NULL,
    total_price NUMERIC(10, 2) NOT NULL
);

CREATE TABLE medicines
(
    id                    BIGSERIAL PRIMARY KEY,
    name                  VARCHAR(255)   NOT NULL,
    brand                 VARCHAR(100),
    batch_number          VARCHAR(50),
    expiry_date           DATE           NOT NULL,
    price                 NUMERIC(10, 2) NOT NULL,
    stock                 INT            NOT NULL DEFAULT 0,
    prescription_required BOOLEAN        NOT NULL DEFAULT FALSE,
    created_at            TIMESTAMP               DEFAULT CURRENT_TIMESTAMP,
    updated_at            TIMESTAMP               DEFAULT CURRENT_TIMESTAMP
);

