SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `healthgem` DEFAULT CHARACTER SET utf8 ;
USE `healthgem` ;

-- -----------------------------------------------------
-- Table `healthgem`.`activitylist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `healthgem`.`activitylist` (
  `activityID` INT(11) NOT NULL,
  `name` VARCHAR(100) NULL DEFAULT NULL,
  `MET` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`activityID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `healthgem`.`activitytracker`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `healthgem`.`activitytracker` (
  `id` INT(11) NOT NULL,
  `datetime` DATETIME NULL DEFAULT NULL,
  `activityID` INT(11) NULL DEFAULT NULL,
  `calorieBurnedPerHour` INT(11) NULL DEFAULT NULL,
  `status` VARCHAR(200) NULL DEFAULT NULL,
  `photo` VARCHAR(100) NULL DEFAULT NULL,
  `fbPostID` INT(11) NULL DEFAULT NULL,
  `userID` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `userID` (`userID` ASC),
  INDEX `activityID` (`activityID` ASC),
  CONSTRAINT `activityID`
    FOREIGN KEY (`activityID`)
    REFERENCES `healthgem`.`activitylist` (`activityID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `healthgem`.`bloodpressuretracker`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `healthgem`.`bloodpressuretracker` (
  `id` INT(11) NOT NULL,
  `datetime` DATETIME NULL DEFAULT NULL,
  `systolic` VARCHAR(100) NULL DEFAULT NULL,
  `diastolic` VARCHAR(100) NULL DEFAULT NULL,
  `status` VARCHAR(200) NULL DEFAULT NULL,
  `photo` VARCHAR(100) NULL DEFAULT NULL,
  `fbPostID` INT(11) NULL DEFAULT NULL,
  `userID` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `bpUserID` (`userID` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `healthgem`.`useraccountandinfo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `healthgem`.`useraccountandinfo` (
  `userID` INT(11) NOT NULL,
  `username` VARCHAR(100) NULL DEFAULT NULL,
  `password` VARCHAR(100) NULL DEFAULT NULL,
  `firstName` VARCHAR(100) NULL DEFAULT NULL,
  `middleName` VARCHAR(100) NULL DEFAULT NULL,
  `lastName` VARCHAR(100) NULL DEFAULT NULL,
  `birthdate` DATE NULL DEFAULT NULL,
  `gender` VARCHAR(45) NULL DEFAULT NULL,
  `heightInInches` INT(50) NULL DEFAULT NULL,
  `contactNumber` INT(50) NULL DEFAULT NULL,
  `emailAddress` VARCHAR(100) NULL DEFAULT NULL,
  `fbEmailAddress` VARCHAR(45) NULL DEFAULT NULL,
  `fbAccessToken` VARCHAR(100) NULL DEFAULT NULL,
  `photo` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`userID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `healthgem`.`bloodsugartracker`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `healthgem`.`bloodsugartracker` (
  `id` INT(11) NOT NULL,
  `datetime` DATETIME NULL DEFAULT NULL,
  `bloodSugar` DECIMAL(11,0) NULL DEFAULT NULL,
  `status` VARCHAR(200) NULL DEFAULT NULL,
  `photo` VARCHAR(100) NULL DEFAULT NULL,
  `fbPostID` INT(11) NULL DEFAULT NULL,
  `userID` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `bloodsugarUserID` (`userID` ASC),
  CONSTRAINT `bloodsugarUserID`
    FOREIGN KEY (`userID`)
    REFERENCES `healthgem`.`useraccountandinfo` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `healthgem`.`checkuptracker`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `healthgem`.`checkuptracker` (
  `id` INT(11) NOT NULL,
  `datetime` DATETIME NULL DEFAULT NULL,
  `purpose` VARCHAR(100) NULL DEFAULT NULL,
  `doctorsName` VARCHAR(100) NULL DEFAULT NULL,
  `location` VARCHAR(100) NULL DEFAULT NULL,
  `notes` VARCHAR(100) NULL DEFAULT NULL,
  `status` VARCHAR(200) NULL DEFAULT NULL,
  `photo` VARCHAR(100) NULL DEFAULT NULL,
  `fbPostID` INT(11) NULL DEFAULT NULL,
  `userID` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `checkupUserID` (`userID` ASC),
  CONSTRAINT `checkupUserID`
    FOREIGN KEY (`userID`)
    REFERENCES `healthgem`.`useraccountandinfo` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `healthgem`.`restaurantlist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `healthgem`.`restaurantlist` (
  `restaurantID` INT(11) NOT NULL,
  `name` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`restaurantID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `healthgem`.`foodlist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `healthgem`.`foodlist` (
  `foodID` INT(11) NOT NULL,
  `name` VARCHAR(100) NULL DEFAULT NULL,
  `calorie` VARCHAR(100) NULL DEFAULT NULL,
  `servingUnit` VARCHAR(100) NULL DEFAULT NULL,
  `servingSize` INT(11) NULL DEFAULT NULL,
  `restaurantID` INT(11) NULL DEFAULT NULL,
  `fromFatsecret` BINARY(1) NULL DEFAULT NULL,
  PRIMARY KEY (`foodID`),
  INDEX `restaurantID` (`restaurantID` ASC),
  CONSTRAINT `restaurantID`
    FOREIGN KEY (`restaurantID`)
    REFERENCES `healthgem`.`restaurantlist` (`restaurantID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `healthgem`.`foodtracker`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `healthgem`.`foodtracker` (
  `id` INT(11) NOT NULL,
  `datetime` DATETIME NULL DEFAULT NULL,
  `foodID` INT(11) NULL DEFAULT NULL,
  `servingCount` INT(11) NULL DEFAULT NULL,
  `status` VARCHAR(200) NULL DEFAULT NULL,
  `photo` VARCHAR(100) NULL DEFAULT NULL,
  `fbPostID` INT(11) NULL DEFAULT NULL,
  `userID` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `foodID` (`foodID` ASC),
  INDEX `userID` (`userID` ASC),
  CONSTRAINT `userID`
    FOREIGN KEY (`userID`)
    REFERENCES `healthgem`.`useraccountandinfo` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `foodID`
    FOREIGN KEY (`foodID`)
    REFERENCES `healthgem`.`foodlist` (`foodID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `healthgem`.`notestracker`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `healthgem`.`notestracker` (
  `id` INT(11) NOT NULL,
  `datetime` DATETIME NULL DEFAULT NULL,
  `title` VARCHAR(100) NULL DEFAULT NULL,
  `notes` VARCHAR(200) NULL DEFAULT NULL,
  `status` VARCHAR(200) NULL DEFAULT NULL,
  `photo` VARCHAR(100) NULL DEFAULT NULL,
  `fbPostID` INT(11) NULL DEFAULT NULL,
  `userID` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `notesUserID` (`userID` ASC),
  CONSTRAINT `notesUserID`
    FOREIGN KEY (`userID`)
    REFERENCES `healthgem`.`useraccountandinfo` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `healthgem`.`verifytable`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `healthgem`.`verifytable` (
  `verifyID` INT(11) NOT NULL,
  `datetime` DATETIME NULL DEFAULT NULL,
  `status` VARCHAR(200) NULL DEFAULT NULL,
  `extractedWord` VARCHAR(100) NULL DEFAULT NULL,
  `category` VARCHAR(100) NULL DEFAULT NULL,
  `fbPostID` INT(11) NULL DEFAULT NULL,
  `photo` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`verifyID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `healthgem`.`weighttracker`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `healthgem`.`weighttracker` (
  `id` INT(11) NOT NULL,
  `datetime` DATETIME NULL DEFAULT NULL,
  `weightInPounds` INT(20) NULL DEFAULT NULL,
  `status` VARCHAR(200) NULL DEFAULT NULL,
  `photo` VARCHAR(100) NULL DEFAULT NULL,
  `fbPostID` INT(11) NULL DEFAULT NULL,
  `userID` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `weightUserID` (`userID` ASC),
  CONSTRAINT `weightUserID`
    FOREIGN KEY (`userID`)
    REFERENCES `healthgem`.`useraccountandinfo` (`userID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
