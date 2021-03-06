-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema receipts_app_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema receipts_app_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `receipts_app_db` DEFAULT CHARACTER SET utf8 ;
USE `receipts_app_db` ;

-- -----------------------------------------------------
-- Table `receipts_app_db`.`accounts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `receipts_app_db`.`accounts` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(100) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `account_type` ENUM("customer", "store_owner") NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `receipts_app_db`.`customers_details`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `receipts_app_db`.`customers_details` (
  `accounts_id` INT NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `telephone` VARCHAR(45) NOT NULL,
  INDEX `fk_customers_details_accounts_idx` (`accounts_id` ASC) VISIBLE,
  UNIQUE INDEX `first_name_UNIQUE` (`first_name` ASC) VISIBLE,
  PRIMARY KEY (`accounts_id`),
  CONSTRAINT `fk_customers_details_accounts`
    FOREIGN KEY (`accounts_id`)
    REFERENCES `receipts_app_db`.`accounts` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `receipts_app_db`.`store_owners_details`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `receipts_app_db`.`store_owners_details` (
  `accounts_id` INT NOT NULL,
  `cui` VARCHAR(45) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `store_type` ENUM("food", "clothes", "gas", "other") NOT NULL,
  INDEX `fk_store_owners_details_accounts1_idx` (`accounts_id` ASC) VISIBLE,
  UNIQUE INDEX `cui_UNIQUE` (`cui` ASC) VISIBLE,
  PRIMARY KEY (`accounts_id`),
  CONSTRAINT `fk_store_owners_details_accounts1`
    FOREIGN KEY (`accounts_id`)
    REFERENCES `receipts_app_db`.`accounts` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `receipts_app_db`.`receipts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `receipts_app_db`.`receipts` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `store_owner_id` INT NOT NULL,
  `customer_id` INT NOT NULL,
  `details` JSON NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_receipts_accounts1_idx` (`store_owner_id` ASC) VISIBLE,
  INDEX `fk_receipts_accounts2_idx` (`customer_id` ASC) VISIBLE,
  CONSTRAINT `fk_receipts_accounts1`
    FOREIGN KEY (`store_owner_id`)
    REFERENCES `receipts_app_db`.`accounts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_receipts_accounts2`
    FOREIGN KEY (`customer_id`)
    REFERENCES `receipts_app_db`.`accounts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `receipts_app_db`.`products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `receipts_app_db`.`products` (
  `product_id` INT NOT NULL AUTO_INCREMENT,
  `store_owners_id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `brand` VARCHAR(45) NOT NULL,
  `quota` VARCHAR(45) NOT NULL,
  `price` FLOAT NOT NULL,
  PRIMARY KEY (`product_id`),
  INDEX `fk_products_store_owners_details1_idx` (`store_owners_id` ASC) VISIBLE,
  CONSTRAINT `fk_products_store_owners_details1`
    FOREIGN KEY (`store_owners_id`)
    REFERENCES `receipts_app_db`.`store_owners_details` (`accounts_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `receipts_app_db` ;

-- -----------------------------------------------------
-- Placeholder table for view `receipts_app_db`.`customers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `receipts_app_db`.`customers` (`id` INT, `username` INT, `password` INT, `account_type` INT, `accounts_id` INT, `first_name` INT, `last_name` INT, `telephone` INT);

-- -----------------------------------------------------
-- Placeholder table for view `receipts_app_db`.`store_owners`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `receipts_app_db`.`store_owners` (`id` INT, `username` INT, `password` INT, `account_type` INT, `accounts_id` INT, `cui` INT, `name` INT, `store_type` INT);

-- -----------------------------------------------------
-- procedure insert_customer
-- -----------------------------------------------------

DELIMITER $$
USE `receipts_app_db`$$
CREATE PROCEDURE `insert_customer`(username VARCHAR(100), password VARCHAR(100), first_name VARCHAR(45), last_name VARCHAR(45), telephone VARCHAR(45))
BEGIN
	INSERT INTO accounts (username, password, account_type) VALUES (username, password, "customer");
	INSERT INTO customers_details (accounts_id, first_name, last_name, telephone) VALUES (LAST_INSERT_ID(), first_name, last_name, telephone);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure insert_store_owner
-- -----------------------------------------------------

DELIMITER $$
USE `receipts_app_db`$$
CREATE PROCEDURE `insert_store_owner`(username VARCHAR(100), password VARCHAR(100), cui VARCHAR(45), name VARCHAR(45), store_type ENUM('food', 'clothes', 'gas', 'other'))
BEGIN
	INSERT INTO accounts (username, password, account_type) VALUES (username, password, 'store_owner');
	INSERT INTO store_owners_details (accounts_id, cui, name, store_type) VALUES (LAST_INSERT_ID(), cui, name, store_type);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure insert_product
-- -----------------------------------------------------

DELIMITER $$
USE `receipts_app_db`$$
CREATE PROCEDURE `insert_product` (store_owners_id INT, name VARCHAR(45), brand VARCHAR(45), quota VARCHAR(45), price FLOAT)
BEGIN
	INSERT INTO products (store_owners_id, name, brand, quota, price) VALUES (store_owners_id, name, brand, quota, price);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- View `receipts_app_db`.`customers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `receipts_app_db`.`customers`;
USE `receipts_app_db`;
CREATE  OR REPLACE VIEW `customers` AS
SELECT * FROM accounts
INNER JOIN customers_details ON accounts.id = customers_details.accounts_id;

-- -----------------------------------------------------
-- View `receipts_app_db`.`store_owners`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `receipts_app_db`.`store_owners`;
USE `receipts_app_db`;
CREATE  OR REPLACE VIEW `store_owners` AS
SELECT * FROM accounts
INNER JOIN store_owners_details ON accounts.id = store_owners_details.accounts_id;
CREATE USER 'FIS' IDENTIFIED BY 'FIS';

GRANT ALL ON `receipts_app_db`.* TO 'FIS';
GRANT SELECT ON TABLE `receipts_app_db`.* TO 'FIS';
GRANT SELECT, INSERT, TRIGGER ON TABLE `receipts_app_db`.* TO 'FIS';
GRANT SELECT, INSERT, TRIGGER, UPDATE, DELETE ON TABLE `receipts_app_db`.* TO 'FIS';
GRANT EXECUTE ON ROUTINE `receipts_app_db`.* TO 'FIS';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
