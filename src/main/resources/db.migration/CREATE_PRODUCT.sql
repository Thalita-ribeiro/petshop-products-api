-- Criação da tabela Product
CREATE TABLE Product (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    categoryId BIGINT,
    value DOUBLE PRECISION NOT NULL,
    quantity INT NOT NULL,
    createdAt TIMESTAMP,
    updatedAt TIMESTAMP,
    FOREIGN KEY (categoryId) REFERENCES Category(id)
);

-- Inserção de dados na tabela Product
INSERT INTO Product (id, name, categoryId, value, quantity, createdAt, updatedAt) VALUES
(1, 'Ração para cães', 1, 50.99, 100, NOW(), NOW()),
(2, 'Ração para gatos', 1, 45.50, 80, NOW(), NOW()),
(3, 'Bola de borracha para cães', 2, 15.00, 50, NOW(), NOW()),
(4, 'Arranhador para gatos', 2, 35.99, 30, NOW(), NOW()),
(5, 'Coleira para cães', 3, 10.50, 120, NOW(), NOW()),
(6, 'Caixa de transporte para gatos', 3, 40.00, 20, NOW(), NOW());