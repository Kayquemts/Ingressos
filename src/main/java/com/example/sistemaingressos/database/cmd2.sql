CREATE TABLE `cliente` (
                           `cpf` varchar(11) NOT NULL,
                           `idade` int DEFAULT NULL,
                           `nome` varchar(100) DEFAULT NULL,
                           `estudante` tinyint(1) DEFAULT NULL,
                           `senha` varchar(255) NOT NULL,
                           PRIMARY KEY (`cpf`),
                           UNIQUE KEY `cpf` (`cpf`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `combos` (
                          `Id_combo` int NOT NULL AUTO_INCREMENT,
                          `Itens_Combo` varchar(255) NOT NULL,
                          `Preco_combo` float NOT NULL,
                          PRIMARY KEY (`Id_combo`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `filmes` (
                          `nome` varchar(45) NOT NULL,
                          `genero` varchar(45) NOT NULL,
                          `faixa_etaria` int NOT NULL,
                          `duracao` int NOT NULL,
                          PRIMARY KEY (`nome`),
                          UNIQUE KEY `nome` (`nome`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `ingressos` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `cadeira_especial` int NOT NULL DEFAULT '0',
                             `preco_final` float NOT NULL,
                             `cadeira_id` int NOT NULL,
                             `venda_id` int DEFAULT NULL,
                             `filme` varchar(45) DEFAULT NULL,
                             `sala_id` int DEFAULT NULL,
                             `sessao_id` int DEFAULT NULL,
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `id` (`id`),
                             KEY `fk_venda` (`venda_id`),
                             KEY `fk_filme` (`filme`),
                             KEY `fk_sala` (`sala_id`),
                             KEY `fk_sessao` (`sessao_id`),
                             CONSTRAINT `fk_filme` FOREIGN KEY (`filme`) REFERENCES `filmes` (`nome`),
                             CONSTRAINT `fk_sala` FOREIGN KEY (`sala_id`) REFERENCES `salas` (`id`),
                             CONSTRAINT `fk_sessao` FOREIGN KEY (`sessao_id`) REFERENCES `sessoes` (`id`),
                             CONSTRAINT `fk_venda` FOREIGN KEY (`venda_id`) REFERENCES `vendas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `salas` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `qnt_max_pessoas` int NOT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `sessoes` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `filme` varchar(45) NOT NULL,
                           `hora` int NOT NULL,
                           `minuto` int NOT NULL,
                           `preco` float NOT NULL,
                           `sala_id` int DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `id` (`id`),
                           KEY `fk_filme_sessao` (`filme`),
                           KEY `fk_sala_sessao` (`sala_id`),
                           CONSTRAINT `fk_filme_sessao` FOREIGN KEY (`filme`) REFERENCES `filmes` (`nome`),
                           CONSTRAINT `fk_sala_sessao` FOREIGN KEY (`sala_id`) REFERENCES `salas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `venda_combo` (
                               `id_venda` int NOT NULL,
                               `id_combos` int NOT NULL,
                               PRIMARY KEY (`id_venda`,`id_combos`),
                               KEY `id_combos` (`id_combos`),
                               CONSTRAINT `venda_combo_ibfk_1` FOREIGN KEY (`id_venda`) REFERENCES `vendas` (`id`),
                               CONSTRAINT `venda_combo_ibfk_2` FOREIGN KEY (`id_combos`) REFERENCES `combos` (`Id_combo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `vendas` (
                          `id` int NOT NULL AUTO_INCREMENT,
                          `qnt_ingressos` int NOT NULL,
                          `data` date NOT NULL,
                          `preco_total` float NOT NULL,
                          `cpf` varchar(11) DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `id` (`id`),
                          KEY `fk_cliente` (`cpf`),
                          CONSTRAINT `fk_cliente` FOREIGN KEY (`cpf`) REFERENCES `cliente` (`cpf`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci