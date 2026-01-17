CREATE TABLE roles
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,

    username VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,

    email VARCHAR(150),
    phone_number VARCHAR(20),

    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    account_non_expired BOOLEAN NOT NULL DEFAULT TRUE,
    account_non_locked BOOLEAN NOT NULL DEFAULT TRUE,
    credentials_non_expired BOOLEAN NOT NULL DEFAULT TRUE,
    credentials_expiry_date TIMESTAMP,

    failed_login_attempts INTEGER NOT NULL DEFAULT 0,
    last_failed_login TIMESTAMP,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- 🔒 Constraints
    CONSTRAINT uq_users_username UNIQUE (username),
    CONSTRAINT uq_users_email UNIQUE (email),

    CONSTRAINT chk_failed_login_attempts
        CHECK (failed_login_attempts >= 0)
);

--Optional
CREATE INDEX idx_users_username ON users (username);
CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_users_enabled ON users (enabled);

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

CREATE TABLE customers (
    id BIGSERIAL PRIMARY KEY,

    name VARCHAR(150) NOT NULL,
    email VARCHAR(150),
    phone VARCHAR(15),
    gender VARCHAR(10),
    dob DATE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- 🔒 Constraints
    CONSTRAINT uq_customers_email UNIQUE (email),

    CONSTRAINT chk_customers_gender
        CHECK (gender IN ('MALE', 'FEMALE', 'OTHER')),

    CONSTRAINT chk_customers_dob
        CHECK (dob <= CURRENT_DATE)
);

--Mandatory for Performance
CREATE INDEX idx_customers_name ON customers (name);
CREATE INDEX idx_customers_phone ON customers (phone);
CREATE INDEX idx_customers_email ON customers (email);


ALTER TABLE customers
    ADD CONSTRAINT uq_customers_phone UNIQUE (phone);

ALTER TABLE customers
    ADD CONSTRAINT chk_gender
        CHECK (gender IN ('MALE', 'FEMALE', 'OTHER'));

---------------------------------------------------

CREATE TABLE medicines (
    id BIGSERIAL PRIMARY KEY,

    name VARCHAR(255) NOT NULL,
    brand VARCHAR(100),

    dosage_mg INTEGER CHECK (dosage_mg > 0), -- nullable

    batch_number VARCHAR(50) NOT NULL,
    expiry_date DATE NOT NULL,

    price NUMERIC(10,2) NOT NULL CHECK (price >= 0),
    stock INTEGER NOT NULL DEFAULT 0 CHECK (stock >= 0),

    prescription_required BOOLEAN NOT NULL DEFAULT FALSE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uq_medicines_batch
        UNIQUE (name, brand, dosage_mg, batch_number),

    CONSTRAINT chk_medicines_expiry
        CHECK (expiry_date > CURRENT_DATE)
);

CREATE UNIQUE INDEX uq_medicines_with_dosage
ON medicines (name, brand, dosage_mg, batch_number)
WHERE dosage_mg IS NOT NULL;

CREATE UNIQUE INDEX uq_medicines_without_dosage
ON medicines (name, brand, batch_number)
WHERE dosage_mg IS NULL;

--For performance
CREATE INDEX idx_medicines_name ON medicines (name);
CREATE INDEX idx_medicines_brand ON medicines (brand);
CREATE INDEX idx_medicines_expiry ON medicines (expiry_date);
CREATE INDEX idx_medicines_stock ON medicines (stock);

ALTER TABLE medicines
ADD COLUMN dosage_mg INTEGER;

UPDATE medicines SET dosage_mg = 650 WHERE name = 'Dolo';

ALTER TABLE medicines
ALTER COLUMN dosage_mg SET NOT NULL;


-------------------------------------------------
CREATE TABLE sales (
    id BIGSERIAL PRIMARY KEY,

    customer_id BIGINT NOT NULL,
    sold_by BIGINT,  -- User who handled the sale (optional)

    subtotal NUMERIC(10,2) NOT NULL DEFAULT 0.00,
    discount NUMERIC(10,2) NOT NULL DEFAULT 0.00,
    gst NUMERIC(10,2) NOT NULL DEFAULT 0.00,
    grand_total NUMERIC(10,2) NOT NULL DEFAULT 0.00,

    payment_method VARCHAR(20) NOT NULL,
    sale_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Foreign keys
    CONSTRAINT fk_sales_customer
        FOREIGN KEY (customer_id) REFERENCES customers(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_sales_user
        FOREIGN KEY (sold_by) REFERENCES users(id)
        ON DELETE SET NULL,

    -- Payment method constraint
    CONSTRAINT chk_payment_method
        CHECK (payment_method IN ('CASH', 'CARD', 'UPI', 'ONLINE')),

    -- Amounts cannot be negative
    CONSTRAINT chk_sales_amounts
        CHECK (
            subtotal >= 0 AND
            discount >= 0 AND
            gst >= 0 AND
            grand_total >= 0
        )
);

CREATE TABLE sale_medicines (
    id BIGSERIAL PRIMARY KEY,
    sale_id BIGINT NOT NULL,
    medicine_id BIGINT NOT NULL,
    batch_number VARCHAR(50) NOT NULL,
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    unit_price NUMERIC(10,2) NOT NULL CHECK (unit_price >= 0),
    total NUMERIC(10,2) NOT NULL CHECK (total >= 0),

    CONSTRAINT fk_sale_medicines_sale
        FOREIGN KEY (sale_id)
        REFERENCES sales(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_sale_medicines_medicine
        FOREIGN KEY (medicine_id)
        REFERENCES medicines(id),

    -- Prevent duplicate medicine entries per sale & batch
    CONSTRAINT uq_sale_medicine_batch
        UNIQUE (sale_id, medicine_id, batch_number)
);

CREATE INDEX idx_sales_customer_id ON sales(customer_id);
CREATE INDEX idx_sale_medicines_sale_id ON sale_medicines(sale_id);
CREATE INDEX idx_sale_medicines_medicine_id ON sale_medicines(medicine_id);

---------------------------Supplier Details--------------------------
CREATE TYPE supplier_status AS ENUM ('ACTIVE', 'INACTIVE');
CREATE TABLE suppliers (
    id BIGSERIAL PRIMARY KEY,
    supplier_code VARCHAR(50) UNIQUE NOT NULL,
    supplier_name VARCHAR(255) NOT NULL,
    contact_person VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(255),
    gst_number VARCHAR(50),
    drug_license_number VARCHAR(50),
    bank_name VARCHAR(100),
    bank_account_number VARCHAR(50),
    ifsc_code VARCHAR(20),
    status VARCHAR(10),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

