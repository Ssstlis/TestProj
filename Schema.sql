-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema TestBooks
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema TestBooks
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `TestBooks` DEFAULT CHARACTER SET utf8 ;
USE `TestBooks` ;

-- -----------------------------------------------------
-- Table `TestBooks`.`books`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TestBooks`.`books` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(80) NOT NULL,
  `year` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `TestBooks`.`Authors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TestBooks`.`Authors` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `FirstName` VARCHAR(45) NULL,
  `LastName` VARCHAR(45) NULL,
  `MiddleName` VARCHAR(45) NULL,
  `FullName` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `TestBooks`.`books_has_Authors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TestBooks`.`books_has_Authors` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `books_id` INT UNSIGNED NOT NULL,
  `Authors_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `books_id`, `Authors_id`),
  INDEX `fk_books_has_Authors_Authors1_idx` (`Authors_id` ASC),
  INDEX `fk_books_has_Authors_books1_idx` (`books_id` ASC),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  CONSTRAINT `fk_books_has_Authors_books1`
    FOREIGN KEY (`books_id`)
    REFERENCES `TestBooks`.`books` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_books_has_Authors_Authors1`
    FOREIGN KEY (`Authors_id`)
    REFERENCES `TestBooks`.`Authors` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
