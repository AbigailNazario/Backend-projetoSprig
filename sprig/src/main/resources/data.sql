-- =====================================================
-- SCRIPT DE POPULAÇÃO INICIAL DO BANCO SPRIG
-- Sistema de Logística de Sementes
-- =====================================================

-- IMPORTANTE: Este script será executado automaticamente pelo Spring Boot
-- se spring.jpa.hibernate.ddl-auto=update e as tabelas já existirem

-- =====================================================
-- 1. USUÁRIOS (Gestor, Técnico, Agricultor)
-- =====================================================
-- Senha padrão para todos: "senha123" (será criptografada pelo BCrypt)
-- CPF como ID (Long)

INSERT INTO usuario (cpf, nome, email, senha, perfil, regiao_atuacao) VALUES
(12345678901, 'João Silva Gestor', 'gestor@sprig.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'GESTOR', 'Pernambuco'),
(12345678902, 'Maria Santos Gestora', 'maria.gestor@sprig.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'GESTOR', 'São Paulo'),
(23456789012, 'Carlos Oliveira Técnico', 'tecnico@sprig.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'TECNICO', 'Pernambuco'),
(23456789013, 'Ana Costa Técnica', 'ana.tecnico@sprig.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'TECNICO', 'Bahia'),
(23456789014, 'Pedro Souza Técnico', 'pedro.tecnico@sprig.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'TECNICO', 'Ceará'),
(34567890123, 'José Ferreira Agricultor', 'agricultor@sprig.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'AGRICULTOR', 'Interior de PE'),
(34567890124, 'Antônio Lima Agricultor', 'antonio.agri@sprig.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'AGRICULTOR', 'Sertão PE'),
(34567890125, 'Francisco Rocha Agricultor', 'francisco.agri@sprig.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'AGRICULTOR', 'Agreste PE'),
(34567890126, 'Manuel Dias Agricultor', 'manuel.agri@sprig.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'AGRICULTOR', 'Zona da Mata PE'),
(34567890127, 'Sebastião Alves Agricultor', 'sebastiao.agri@sprig.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'AGRICULTOR', 'Caatinga PE'),
(34567890128, 'Joaquim Pereira Agricultor', 'joaquim.agri@sprig.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'AGRICULTOR', 'Vale do São Francisco'),
(34567890129, 'Raimundo Silva Agricultor', 'raimundo.agri@sprig.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'AGRICULTOR', 'Sertão Central PE'),
(34567890130, 'Luiz Martins Agricultor', 'luiz.agri@sprig.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'AGRICULTOR', 'Agreste Meridional'),
(34567890131, 'Paulo Gomes Agricultor', 'paulo.agri@sprig.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'AGRICULTOR', 'Mata Norte PE'),
(34567890132, 'Marcos Ribeiro Agricultor', 'marcos.agri@sprig.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'AGRICULTOR', 'Mata Sul PE'),
(34567890133, 'João Batista Agricultor', 'joao.batista@sprig.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'AGRICULTOR', 'Sertão do Pajeú'),
(34567890134, 'Pedro Paulo Agricultor', 'pedro.paulo@sprig.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'AGRICULTOR', 'Sertão do Moxotó'),
(34567890135, 'Carlos Eduardo Agricultor', 'carlos.edu@sprig.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'AGRICULTOR', 'Agreste Setentrional'),
(34567890136, 'José Carlos Agricultor', 'jose.carlos@sprig.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'AGRICULTOR', 'Região Metropolitana'),
(34567890137, 'Antônio Carlos Agricultor', 'antonio.carlos@sprig.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'AGRICULTOR', 'Litoral Sul PE');

-- =====================================================
-- 2. SUBCLASSES DE USUÁRIO (Herança JOINED)
-- =====================================================
INSERT INTO gestor (cpf) VALUES (12345678901), (12345678902);
INSERT INTO tecnico (cpf) VALUES (23456789012), (23456789013), (23456789014);
INSERT INTO agricultor (cpf) VALUES 
(34567890123), (34567890124), (34567890125), (34567890126), (34567890127),
(34567890128), (34567890129), (34567890130), (34567890131), (34567890132),
(34567890133), (34567890134), (34567890135), (34567890136), (34567890137);

-- =====================================================
-- 3. FORNECEDORES
-- =====================================================
INSERT INTO fornecedor (nome, cnpj) VALUES
('Sementes Brasil Ltda', '12345678000190'),
('AgroSementes do Nordeste', '23456789000191'),
('Sementes Premium SA', '34567890000192'),
('Fornecedor Sementes PE', '45678901000193'),
('Sementes Orgânicas Ltda', '56789012000194'),
('AgroNordeste Sementes', '67890123000195'),
('Sementes Tropicais SA', '78901234000196'),
('Fornecedor Regional PE', '89012345000197'),
('Sementes do Sertão Ltda', '90123456000198'),
('AgroVale Sementes', '01234567000199'),
('Sementes Caatinga SA', '11234567000100'),
('Fornecedor Agreste Ltda', '21234567000101'),
('Sementes Mata Norte', '31234567000102'),
('AgroMata Sementes SA', '41234567000103'),
('Sementes São Francisco', '51234567000104'),
('Fornecedor Pajeú Ltda', '61234567000105'),
('Sementes Moxotó SA', '71234567000106'),
('AgroLitoral Sementes', '81234567000107'),
('Sementes Metropolitanas', '91234567000108'),
('Fornecedor Central PE', '10234567000109');

-- =====================================================
-- 4. ARMAZÉNS
-- =====================================================
INSERT INTO armazem (nome_armazem, capacidade, temperatura_ideal) VALUES
('Armazém Central Recife', 50000, 18.5),
('Armazém Caruaru', 30000, 19.0),
('Armazém Petrolina', 40000, 20.0),
('Armazém Garanhuns', 25000, 17.5),
('Armazém Serra Talhada', 20000, 19.5),
('Armazém Arcoverde', 22000, 18.0),
('Armazém Salgueiro', 18000, 20.5),
('Armazém Palmares', 15000, 18.5),
('Armazém Vitória de Santo Antão', 28000, 19.0),
('Armazém Belo Jardim', 24000, 18.0),
('Armazém Pesqueira', 21000, 18.5),
('Armazém Surubim', 19000, 19.0),
('Armazém Limoeiro', 17000, 18.5),
('Armazém Goiana', 16000, 19.5),
('Armazém Cabo de Santo Agostinho', 23000, 19.0),
('Armazém Ipojuca', 20000, 19.5),
('Armazém Ouricuri', 14000, 21.0),
('Armazém Afogados da Ingazeira', 13000, 19.5),
('Armazém Santa Cruz do Capibaribe', 26000, 18.5),
('Armazém Jaboatão dos Guararapes', 32000, 19.0);

-- =====================================================
-- 5. LOTES DE SEMENTES
-- =====================================================
INSERT INTO lote (numero_lote, especie, quantidade, validade, data_recebimento, status, qr_code, fk_armazem, fk_fornecedor) VALUES
(1001, 'Milho Híbrido', 5000, '2026-12-31 00:00:00', '2024-01-15', 'Disponivel', 'QR1001', 1, 1),
(1002, 'Feijão Carioca', 3000, '2026-06-30 00:00:00', '2024-02-10', 'Disponivel', 'QR1002', 2, 2),
(1003, 'Soja Transgênica', 8000, '2027-03-31 00:00:00', '2024-03-05', 'Disponivel', 'QR1003', 3, 3),
(1004, 'Arroz Irrigado', 4500, '2026-09-30 00:00:00', '2024-01-20', 'Disponivel', 'QR1004', 4, 4),
(1005, 'Sorgo Forrageiro', 2500, '2026-11-30 00:00:00', '2024-02-25', 'Disponivel', 'QR1005', 5, 5),
(1006, 'Girassol', 3500, '2026-08-31 00:00:00', '2024-03-10', 'Disponivel', 'QR1006', 6, 6),
(1007, 'Algodão Herbáceo', 6000, '2027-01-31 00:00:00', '2024-01-30', 'Disponivel', 'QR1007', 7, 7),
(1008, 'Trigo', 4000, '2026-10-31 00:00:00', '2024-02-15', 'Disponivel', 'QR1008', 8, 8),
(1009, 'Cevada', 2000, '2026-07-31 00:00:00', '2024-03-20', 'Disponivel', 'QR1009', 9, 9),
(1010, 'Aveia', 2800, '2026-12-31 00:00:00', '2024-01-25', 'Disponivel', 'QR1010', 10, 10),
(1011, 'Milho Crioulo', 3200, '2026-11-30 00:00:00', '2024-02-20', 'Disponivel', 'QR1011', 11, 11),
(1012, 'Feijão Preto', 2700, '2026-06-30 00:00:00', '2024-03-15', 'Disponivel', 'QR1012', 12, 12),
(1013, 'Amendoim', 1800, '2026-09-30 00:00:00', '2024-01-18', 'Disponivel', 'QR1013', 13, 13),
(1014, 'Gergelim', 1500, '2026-08-31 00:00:00', '2024-02-22', 'Disponivel', 'QR1014', 14, 14),
(1015, 'Mamona', 2200, '2027-02-28 00:00:00', '2024-03-12', 'Disponivel', 'QR1015', 15, 15),
(1016, 'Milheto', 1900, '2026-10-31 00:00:00', '2024-01-28', 'Disponivel', 'QR1016', 16, 16),
(1017, 'Painço', 1600, '2026-07-31 00:00:00', '2024-02-18', 'Disponivel', 'QR1017', 17, 17),
(1018, 'Centeio', 2100, '2026-12-31 00:00:00', '2024-03-08', 'Disponivel', 'QR1018', 18, 18),
(1019, 'Triticale', 2400, '2026-11-30 00:00:00', '2024-01-22', 'Disponivel', 'QR1019', 19, 19),
(1020, 'Canola', 3100, '2027-01-31 00:00:00', '2024-02-28', 'Disponivel', 'QR1020', 20, 20);

-- =====================================================
-- 6. MOTORISTAS
-- =====================================================
INSERT INTO motorista (nome, cnh) VALUES
('Roberto Silva', '12345678901'),
('Fernando Santos', '23456789012'),
('Ricardo Oliveira', '34567890123'),
('Marcelo Costa', '45678901234'),
('André Souza', '56789012345'),
('Rodrigo Lima', '67890123456'),
('Fábio Rocha', '78901234567'),
('Diego Dias', '89012345678'),
('Bruno Alves', '90123456789'),
('Thiago Pereira', '01234567890'),
('Leonardo Martins', '11234567891'),
('Rafael Gomes', '21234567892'),
('Gustavo Ribeiro', '31234567893'),
('Felipe Carvalho', '41234567894'),
('Lucas Araújo', '51234567895'),
('Matheus Fernandes', '61234567896'),
('Vinícius Barbosa', '71234567897'),
('Gabriel Mendes', '81234567898'),
('Daniel Castro', '91234567899'),
('Renato Pinto', '10234567800');

-- =====================================================
-- 7. VEÍCULOS
-- =====================================================
INSERT INTO veiculo (placa, modelo, capacidade) VALUES
('ABC1234', 'Mercedes-Benz Atego 1719', 8000),
('DEF5678', 'Volkswagen Constellation 17.280', 12000),
('GHI9012', 'Ford Cargo 1719', 9000),
('JKL3456', 'Iveco Tector 170E28', 10000),
('MNO7890', 'Scania P 320', 15000),
('PQR1234', 'Volvo VM 270', 11000),
('STU5678', 'DAF CF 85', 13000),
('VWX9012', 'MAN TGX 29.480', 16000),
('YZA3456', 'Mercedes-Benz Axor 2544', 14000),
('BCD7890', 'Volkswagen Delivery 11.180', 7000),
('EFG1234', 'Ford Cargo 2429', 12000),
('HIJ5678', 'Iveco Stralis 740S46T', 18000),
('KLM9012', 'Scania R 440', 17000),
('NOP3456', 'Volvo FH 460', 19000),
('QRS7890', 'DAF XF 105', 20000),
('TUV1234', 'MAN TGS 28.440', 15000),
('WXY5678', 'Mercedes-Benz Actros 2651', 16000),
('ZAB9012', 'Volkswagen Constellation 24.280', 14000),
('CDE3456', 'Ford Cargo 2842', 18000),
('FGH7890', 'Iveco Tector 240E28', 11000);

-- =====================================================
-- 8. VÍNCULO VEÍCULO-MOTORISTA
-- =====================================================
INSERT INTO veiculo_motorista (fk_motorista, fk_veiculo) VALUES
(1, 1), (2, 2), (3, 3), (4, 4), (5, 5),
(6, 6), (7, 7), (8, 8), (9, 9), (10, 10),
(11, 11), (12, 12), (13, 13), (14, 14), (15, 15),
(16, 16), (17, 17), (18, 18), (19, 19), (20, 20);

-- =====================================================
-- 9. DESTINOS
-- =====================================================
INSERT INTO destino (nome_destino, latitude, longitude, tipo, fk_agricultor) VALUES
('Fazenda São José', -8.0476, -34.8770, 'Fazenda', 34567890123),
('Sítio Boa Esperança', -8.2837, -35.9737, 'Sitio', 34567890124),
('Fazenda Santa Maria', -9.3868, -40.5089, 'Fazenda', 34567890125),
('Cooperativa Agrícola PE', -8.7625, -36.4897, 'Cooperativa', 34567890126),
('Fazenda Vale Verde', -7.8867, -38.2976, 'Fazenda', 34567890127),
('Sítio Recanto Feliz', -9.0658, -37.8543, 'Sitio', 34567890128),
('Fazenda Primavera', -8.4123, -37.0456, 'Fazenda', 34567890129),
('Associação Rural Nordeste', -8.9543, -36.4987, 'Cooperativa', 34567890130),
('Fazenda Bom Jardim', -8.1234, -35.2345, 'Fazenda', 34567890131),
('Sítio Flor do Campo', -8.5678, -35.6789, 'Sitio', 34567890132),
('Fazenda Serra Alta', -7.9876, -37.5432, 'Fazenda', 34567890133),
('Cooperativa Sertão Verde', -8.3456, -38.1234, 'Cooperativa', 34567890134),
('Fazenda Água Viva', -8.6789, -36.8765, 'Fazenda', 34567890135),
('Sítio Paraíso Rural', -8.2345, -35.4321, 'Sitio', 34567890136),
('Fazenda Horizonte', -8.7654, -37.2109, 'Fazenda', 34567890137),
('Associação Agricultores Unidos', -8.4567, -36.7890, 'Cooperativa', 34567890123),
('Fazenda Nova Era', -8.8901, -37.6543, 'Fazenda', 34567890124),
('Sítio Três Irmãos', -8.1098, -35.8765, 'Sitio', 34567890125),
('Fazenda Esperança Verde', -8.5432, -36.2109, 'Fazenda', 34567890126),
('Cooperativa Vale do Sol', -8.9876, -37.4321, 'Cooperativa', 34567890127);

-- =====================================================
-- 10. ROTAS
-- =====================================================
INSERT INTO rota (data_saida, data_retorno, distancia, tempo_estimado, custo_estimado, fk_motorista, fk_armazem_origem, fk_destino) VALUES
('2024-11-01', '2024-11-02', 120.5, 3.5, 450.00, 1, 1, 1),
('2024-11-02', '2024-11-03', 85.3, 2.8, 320.00, 2, 2, 2),
('2024-11-03', '2024-11-04', 210.7, 5.2, 680.00, 3, 3, 3),
('2024-11-04', '2024-11-05', 95.2, 3.0, 380.00, 4, 4, 4),
('2024-11-05', '2024-11-06', 150.8, 4.1, 520.00, 5, 5, 5),
('2024-11-06', '2024-11-07', 78.4, 2.5, 290.00, 6, 6, 6),
('2024-11-07', '2024-11-08', 195.6, 4.8, 640.00, 7, 7, 7),
('2024-11-08', '2024-11-09', 110.3, 3.2, 420.00, 8, 8, 8),
('2024-11-09', '2024-11-10', 165.9, 4.5, 580.00, 9, 9, 9),
('2024-11-10', '2024-11-11', 88.7, 2.9, 340.00, 10, 10, 10),
('2024-11-11', '2024-11-12', 135.2, 3.8, 490.00, 11, 11, 11),
('2024-11-12', '2024-11-13', 102.5, 3.1, 400.00, 12, 12, 12),
('2024-11-13', '2024-11-14', 178.4, 4.6, 610.00, 13, 13, 13),
('2024-11-14', '2024-11-15', 92.8, 2.7, 360.00, 14, 14, 14),
('2024-11-15', '2024-11-16', 142.6, 3.9, 510.00, 15, 15, 15),
('2024-11-16', '2024-11-17', 118.9, 3.4, 440.00, 16, 16, 16),
('2024-11-17', '2024-11-18', 187.3, 4.7, 630.00, 17, 17, 17),
('2024-11-18', '2024-11-19', 96.5, 3.0, 370.00, 18, 18, 18),
('2024-11-19', '2024-11-20', 155.7, 4.2, 550.00, 19, 19, 19),
('2024-11-20', '2024-11-21', 82.1, 2.6, 310.00, 20, 20, 20);

-- =====================================================
-- 11. ENTREGAS
-- =====================================================
INSERT INTO entregas (data_prevista, data_entrega, quantidade_entregue, status, fk_lote, fk_rota, fk_destino) VALUES
('2024-11-02', '2024-11-02', 500, 'Entregue', 1, 1, 1),
('2024-11-03', '2024-11-03', 300, 'Entregue', 2, 2, 2),
('2024-11-04', NULL, 800, 'Em_rota', 3, 3, 3),
('2024-11-05', '2024-11-05', 450, 'Entregue', 4, 4, 4),
('2024-11-06', NULL, 250, 'Em_rota', 5, 5, 5),
('2024-11-07', '2024-11-07', 350, 'Entregue', 6, 6, 6),
('2024-11-08', NULL, 600, 'Em_rota', 7, 7, 7),
('2024-11-09', '2024-11-09', 400, 'Entregue', 8, 8, 8),
('2024-11-10', NULL, 200, 'Em_rota', 9, 9, 9),
('2024-11-11', '2024-11-11', 280, 'Entregue', 10, 10, 10),
('2024-11-12', NULL, 320, 'Em_rota', 11, 11, 11),
('2024-11-13', '2024-11-13', 270, 'Entregue', 12, 12, 12),
('2024-11-14', NULL, 180, 'Em_rota', 13, 13, 13),
('2024-11-15', '2024-11-15', 150, 'Entregue', 14, 14, 14),
('2024-11-16', NULL, 220, 'Em_rota', 15, 15, 15),
('2024-11-17', '2024-11-17', 190, 'Entregue', 16, 16, 16),
('2024-11-18', NULL, 160, 'Em_rota', 17, 17, 17),
('2024-11-19', '2024-11-19', 210, 'Entregue', 18, 18, 18),
('2024-11-20', NULL, 240, 'Em_rota', 19, 19, 19),
('2024-11-21', '2024-11-21', 310, 'Entregue', 20, 20, 20);

-- =====================================================
-- 12. RELATÓRIOS
-- =====================================================
INSERT INTO relatorio (periodo_inicio, periodo_fim, total_entregas, tempo_medio, volume_total, custo_medio, data_geracao, fk_usuario) VALUES
('2024-11-01 00:00:00', '2024-11-10 23:59:59', 10, 3.5, 4280.00, 450.00, '2024-11-11', 12345678901),
('2024-11-11 00:00:00', '2024-11-20 23:59:59', 10, 3.4, 2420.00, 480.00, '2024-11-21', 12345678901),
('2024-11-01 00:00:00', '2024-11-15 23:59:59', 15, 3.6, 5500.00, 465.00, '2024-11-16', 12345678902),
('2024-10-01 00:00:00', '2024-10-31 23:59:59', 25, 3.8, 8900.00, 490.00, '2024-11-01', 12345678901),
('2024-09-01 00:00:00', '2024-09-30 23:59:59', 22, 3.7, 7800.00, 470.00, '2024-10-01', 12345678902),
('2024-08-01 00:00:00', '2024-08-31 23:59:59', 20, 3.5, 7200.00, 450.00, '2024-09-01', 12345678901),
('2024-07-01 00:00:00', '2024-07-31 23:59:59', 18, 3.4, 6500.00, 440.00, '2024-08-01', 12345678902),
('2024-06-01 00:00:00', '2024-06-30 23:59:59', 21, 3.6, 7600.00, 460.00, '2024-07-01', 12345678901),
('2024-05-01 00:00:00', '2024-05-31 23:59:59', 19, 3.5, 6900.00, 455.00, '2024-06-01', 12345678902),
('2024-04-01 00:00:00', '2024-04-30 23:59:59', 23, 3.7, 8200.00, 475.00, '2024-05-01', 12345678901),
('2024-03-01 00:00:00', '2024-03-31 23:59:59', 24, 3.8, 8600.00, 485.00, '2024-04-01', 12345678902),
('2024-02-01 00:00:00', '2024-02-29 23:59:59', 17, 3.3, 6100.00, 430.00, '2024-03-01', 12345678901),
('2024-01-01 00:00:00', '2024-01-31 23:59:59', 26, 3.9, 9300.00, 495.00, '2024-02-01', 12345678902),
('2023-12-01 00:00:00', '2023-12-31 23:59:59', 28, 4.0, 10100.00, 510.00, '2024-01-01', 12345678901),
('2023-11-01 00:00:00', '2023-11-30 23:59:59', 27, 3.9, 9700.00, 500.00, '2023-12-01', 12345678902),
('2023-10-01 00:00:00', '2023-10-31 23:59:59', 25, 3.8, 9000.00, 490.00, '2023-11-01', 12345678901),
('2023-09-01 00:00:00', '2023-09-30 23:59:59', 22, 3.7, 7900.00, 470.00, '2023-10-01', 12345678902),
('2023-08-01 00:00:00', '2023-08-31 23:59:59', 20, 3.5, 7300.00, 450.00, '2023-09-01', 12345678901),
('2023-07-01 00:00:00', '2023-07-31 23:59:59', 19, 3.4, 6800.00, 445.00, '2023-08-01', 12345678902),
('2023-06-01 00:00:00', '2023-06-30 23:59:59', 21, 3.6, 7700.00, 465.00, '2023-07-01', 12345678901);

-- =====================================================
-- FIM DO SCRIPT
-- =====================================================
-- Total de registros inseridos:
-- - 20 Usuários (2 Gestores, 3 Técnicos, 15 Agricultores)
-- - 20 Fornecedores
-- - 20 Armazéns
-- - 20 Lotes
-- - 20 Motoristas
-- - 20 Veículos
-- - 20 Vínculos Veículo-Motorista
-- - 20 Destinos
-- - 20 Rotas
-- - 20 Entregas
-- - 20 Relatórios
-- =====================================================