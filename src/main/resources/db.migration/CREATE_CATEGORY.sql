-- Criação da tabela Category
CREATE TABLE Category (
    id BIGINT PRIMARY KEY,
    active BOOLEAN,
    name VARCHAR(255),
    createdAt TIMESTAMP,
    updatedAt TIMESTAMP
);

-- Inserção de dados na tabela Category
INSERT INTO Category (id, active, name, createdAt, updatedAt) VALUES
(1, true, 'Ração', NOW(), NOW()),
(2, false, 'Brinquedos', NOW(), NOW()),
(3, true, 'Acessórios', NOW(), NOW());

