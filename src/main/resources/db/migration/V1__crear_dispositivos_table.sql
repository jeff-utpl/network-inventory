CREATE TABLE dispositivos (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    ip VARCHAR(255) NOT NULL,
    tipo VARCHAR(255),
    activo BOOLEAN
);

ALTER TABLE dispositivos ADD CONSTRAINT unique_ip UNIQUE (ip);