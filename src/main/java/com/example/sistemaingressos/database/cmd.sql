CREATE TABLE `cliente` (
                           `cpf` varchar(11) NOT NULL,
                           `idade` int DEFAULT NULL,
                           `nome` varchar(100) DEFAULT NULL,
                           `estudante` tinyint(1) DEFAULT NULL,
                           `senha` varchar(255) NOT NULL,
                           PRIMARY KEY (`cpf`),
                           UNIQUE KEY `cpf` (`cpf`)
);

CREATE TABLE `combos` (
                          `Id_combo` int NOT NULL AUTO_INCREMENT,
                          `Itens_Combo` varchar(255) NOT NULL,
                          `Preco_combo` float NOT NULL,
                          PRIMARY KEY (`Id_combo`)
);

CREATE TABLE `filmes` (
                          `nome` varchar(45) NOT NULL,
                          `genero` varchar(45) NOT NULL,
                          `faixa_etaria` int NOT NULL,
                          `duracao` int NOT NULL,
                          PRIMARY KEY (`nome`),
                          UNIQUE KEY `nome` (`nome`)
);

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
);

CREATE TABLE `salas` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `qnt_max_pessoas` int NOT NULL,
                         PRIMARY KEY (`id`)
);

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
);

CREATE TABLE `venda_combo` (
                               `id_venda` int NOT NULL,
                               `id_combos` int NOT NULL,
                               PRIMARY KEY (`id_venda`,`id_combos`),
                               KEY `id_combos` (`id_combos`),
                               CONSTRAINT `venda_combo_ibfk_1` FOREIGN KEY (`id_venda`) REFERENCES `vendas` (`id`),
                               CONSTRAINT `venda_combo_ibfk_2` FOREIGN KEY (`id_combos`) REFERENCES `combos` (`Id_combo`)
);

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
);