CREATE TABLE shipment_status
(
    id BIGSERIAL PRIMARY KEY,
    code        VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE shipments
(
    id BIGSERIAL PRIMARY KEY,
    origin         VARCHAR(255) NOT NULL,
    destination    VARCHAR(255) NOT NULL,
    creation_date  TIMESTAMP   NOT NULL,
    estimated_time DOUBLE PRECISION,
    distance       DOUBLE PRECISION,
    status_id      BIGINT       NOT NULL,
    CONSTRAINT fk_status FOREIGN KEY (status_id) REFERENCES shipment_status (id)
);
