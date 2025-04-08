CREATE TABLE tenant (
    id uuid PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    cnpj VARCHAR(14) NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE address (
    id uuid PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    number VARCHAR(10) NOT NULL,
    neighborhood VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    state VARCHAR(2) NOT NULL,
    zip_code VARCHAR(10) NOT NULL,
    latitude DECIMAL(9, 6) NOT NULL,
    longitude DECIMAL(9, 6) NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tenant_address (
    id uuid PRIMARY KEY,
    tenant_id uuid NOT NULL,
    address_id uuid NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (tenant_id) REFERENCES tenant(id),
    FOREIGN KEY (address_id) REFERENCES address(id)
);

CREATE TABLE user (
    id uuid PRIMARY KEY,
    tenant_id INT NOT NULL,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (tenant_id) REFERENCES tenant(id)
);

CREATE TABLE permission_set (
    id uuid PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tenant_permission_set (
    tenant_id uuid NOT NULL,
    permission_set_id uuid NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (tenant_id, permission_set_id),
    FOREIGN KEY (tenant_id) REFERENCES tenant(id),
    FOREIGN KEY (permission_set_id) REFERENCES permission_set(id)
);

CREATE TABLE user_permission_set (
    user_id uuid NOT NULL,
    permission_set_id uuid NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, permission_set_id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (permission_set_id) REFERENCES permission_set(id)
);

CREATE INDEX idx_tenant_permission_set_tenant_id ON tenant_permission_set(tenant_id);
CREATE INDEX idx_tenant_permission_set_permission_set_id ON tenant_permission_set(permission_set_id);
CREATE INDEX idx_user_permission_set_user_id ON user_permission_set(user_id);
CREATE INDEX idx_user_permission_set_permission_set_id ON user_permission_set(permission_set_id);
CREATE INDEX idx_user_tenant_id ON user(tenant_id);
CREATE INDEX idx_user_username ON user(username);
CREATE INDEX idx_tenant_name ON tenant(name);
CREATE INDEX idx_tenant_address_tenant_id ON tenant_address(tenant_id);
CREATE INDEX idx_tenant_address_address_id ON tenant_address(address_id);
