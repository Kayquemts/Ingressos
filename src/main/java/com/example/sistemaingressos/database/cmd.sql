CREATE SCHEMA `sistemaingressos`;
CREATE TABLE `sistemaingressos`.`filmes` (
  `nome` VARCHAR(50) NOT NULL,
  `genero` VARCHAR(45) NOT NULL,
  `duracao` INT NOT NULL,
  `faixa_etaria` INT NOT NULL,
  PRIMARY KEY (`nome`),
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC) VISIBLE);

CREATE TABLE `sistemaingressos`.`sessoes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `filme` VARCHAR(45) NOT NULL,
  `hora` INT NOT NULL,
  `minuto` INT NOT NULL,
  `sala_id` INT NOT NULL,
  `preco` DOUBLE NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);

CREATE TABLE `sistemaingressos`.`ingressos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `filme` VARCHAR(45) NOT NULL,
  `sessao_id` INT NOT NULL,
  `sala_id` INT NOT NULL,
  `cadeira_id` INT NOT NULL,
  `cadeira_especial` TINYINT NOT NULL DEFAULT 0,
  `venda_id` INT NOT NULL,
  `preco_final` DOUBLE NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);

CREATE TABLE `sistemaingressos`.`salas` (
  `id` INT NOT NULL,
  `qnt_max_pessoas` INT NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `sistemaingressos`.`vendas` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `qnt_ingressos` INT NOT NULL,
  `data` DATETIME NOT NULL,
  `nome_comprador` VARCHAR(45) NOT NULL,
  `preco_total` DOUBLE NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);


