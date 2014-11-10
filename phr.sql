CREATE DATABASE  IF NOT EXISTS `healthgem` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `healthgem`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: healthgem
-- ------------------------------------------------------
-- Server version	5.6.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `activitycorpus`
--

DROP TABLE IF EXISTS `activitycorpus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activitycorpus` (
  `activityID` int(11) NOT NULL,
  `wordTenses` varchar(100) NOT NULL,
  KEY `id_idx` (`activityID`),
  CONSTRAINT `id` FOREIGN KEY (`activityID`) REFERENCES `activitylist` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `activitylist`
--

DROP TABLE IF EXISTS `activitylist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activitylist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `MET` double DEFAULT NULL,
  `countUsed` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `activitytracker`
--

DROP TABLE IF EXISTS `activitytracker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activitytracker` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateAdded` timestamp NULL DEFAULT NULL,
  `activityID` int(11) DEFAULT NULL,
  `durationInSeconds` int(11) DEFAULT NULL,
  `calorieBurnedPerHour` double DEFAULT NULL,
  `status` varchar(1000) DEFAULT NULL,
  `photo` varchar(100) DEFAULT NULL,
  `facebookID` varchar(45) DEFAULT NULL,
  `userID` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userID` (`userID`),
  KEY `activityID` (`activityID`),
  CONSTRAINT `activityID` FOREIGN KEY (`activityID`) REFERENCES `activitylist` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bloodpressuretracker`
--

DROP TABLE IF EXISTS `bloodpressuretracker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bloodpressuretracker` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateAdded` timestamp NULL DEFAULT NULL,
  `systolic` int(11) DEFAULT NULL,
  `diastolic` int(11) DEFAULT NULL,
  `status` varchar(1000) DEFAULT NULL,
  `photo` varchar(100) DEFAULT NULL,
  `facebookID` varchar(45) DEFAULT NULL,
  `userID` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `bpUserID` (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bloodsugartracker`
--

DROP TABLE IF EXISTS `bloodsugartracker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bloodsugartracker` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateAdded` timestamp NULL DEFAULT NULL,
  `bloodSugar` double DEFAULT NULL,
  `type` varchar(200) DEFAULT NULL,
  `status` varchar(1000) DEFAULT NULL,
  `photo` varchar(100) DEFAULT NULL,
  `facebookID` varchar(45) DEFAULT NULL,
  `userID` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `bloodsugarUserID` (`userID`),
  CONSTRAINT `bloodsugarUserID` FOREIGN KEY (`userID`) REFERENCES `useraccountandinfo` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `checkuptracker`
--

DROP TABLE IF EXISTS `checkuptracker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `checkuptracker` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateAdded` timestamp NULL DEFAULT NULL,
  `purpose` varchar(100) DEFAULT NULL,
  `doctorsName` varchar(100) DEFAULT NULL,
  `notes` varchar(100) DEFAULT NULL,
  `status` varchar(1000) DEFAULT NULL,
  `photo` varchar(100) DEFAULT NULL,
  `facebookID` varchar(45) DEFAULT NULL,
  `userID` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `checkupUserID` (`userID`),
  CONSTRAINT `checkupUserID` FOREIGN KEY (`userID`) REFERENCES `useraccountandinfo` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `deletedfbpost`
--

DROP TABLE IF EXISTS `deletedfbpost`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deletedfbpost` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `facebookID` varchar(45) DEFAULT NULL,
  `userID` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDuser_idx` (`userID`),
  CONSTRAINT `IDUser` FOREIGN KEY (`userID`) REFERENCES `useraccountandinfo` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `foodlist`
--

DROP TABLE IF EXISTS `foodlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `foodlist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(1000) DEFAULT NULL,
  `calorie` double DEFAULT NULL,
  `serving` varchar(100) DEFAULT NULL,
  `restaurantID` int(11) DEFAULT NULL,
  `fromFatsecret` binary(1) DEFAULT NULL,
  `protein` double DEFAULT NULL,
  `fat` double DEFAULT NULL,
  `carbohydrate` double DEFAULT NULL,
  `countUsed` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `restaurantID` (`restaurantID`),
  CONSTRAINT `restaurantID` FOREIGN KEY (`restaurantID`) REFERENCES `restaurantlist` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=170202 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `foodtracker`
--

DROP TABLE IF EXISTS `foodtracker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `foodtracker` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateAdded` timestamp NULL DEFAULT NULL,
  `foodID` int(11) DEFAULT NULL,
  `servingCount` decimal(11,0) DEFAULT NULL,
  `status` varchar(1000) DEFAULT NULL,
  `photo` varchar(100) DEFAULT NULL,
  `facebookID` varchar(45) DEFAULT NULL,
  `userID` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `foodID` (`foodID`),
  KEY `userID` (`userID`),
  CONSTRAINT `foodID` FOREIGN KEY (`foodID`) REFERENCES `foodlist` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `userID` FOREIGN KEY (`userID`) REFERENCES `useraccountandinfo` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gym_activity`
--

DROP TABLE IF EXISTS `gym_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gym_activity` (
  `gymID` int(11) NOT NULL,
  `activityID` int(11) NOT NULL,
  KEY `gymID_idx` (`gymID`),
  KEY `activityID_idx` (`activityID`),
  CONSTRAINT `IDActivity` FOREIGN KEY (`activityID`) REFERENCES `activitylist` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `IDGym` FOREIGN KEY (`gymID`) REFERENCES `gymlist` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gymlist`
--

DROP TABLE IF EXISTS `gymlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gymlist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='			';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notestracker`
--

DROP TABLE IF EXISTS `notestracker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notestracker` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateAdded` timestamp NULL DEFAULT NULL,
  `note` varchar(1000) DEFAULT NULL,
  `status` varchar(1000) DEFAULT NULL,
  `photo` varchar(100) DEFAULT NULL,
  `facebookID` varchar(45) DEFAULT NULL,
  `userID` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `notesUserID` (`userID`),
  CONSTRAINT `notesUserID` FOREIGN KEY (`userID`) REFERENCES `useraccountandinfo` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `restaurantlist`
--

DROP TABLE IF EXISTS `restaurantlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `restaurantlist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2683 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tempactivitytracker`
--

DROP TABLE IF EXISTS `tempactivitytracker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tempactivitytracker` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateAdded` timestamp NULL DEFAULT NULL,
  `extractedWord` varchar(1000) DEFAULT NULL,
  `activityID` int(11) DEFAULT NULL,
  `durationInSeconds` int(11) DEFAULT NULL,
  `calorieBurnedPerHour` double DEFAULT NULL,
  `status` varchar(1000) DEFAULT NULL,
  `photo` varchar(100) DEFAULT NULL,
  `userID` int(11) DEFAULT NULL,
  `facebookID` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tempfoodtracker`
--

DROP TABLE IF EXISTS `tempfoodtracker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tempfoodtracker` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateAdded` timestamp NULL DEFAULT NULL,
  `extractedWord` varchar(1000) DEFAULT NULL,
  `foodID` int(11) DEFAULT NULL,
  `servingSize` double DEFAULT NULL,
  `status` varchar(1000) DEFAULT NULL,
  `photo` varchar(100) DEFAULT NULL,
  `userID` int(11) DEFAULT NULL,
  `facebookID` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `temprestaurant`
--

DROP TABLE IF EXISTS `temprestaurant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `temprestaurant` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateAdded` timestamp NULL DEFAULT NULL,
  `extractedWord` varchar(1000) DEFAULT NULL,
  `restaurantID` int(11) DEFAULT NULL,
  `status` varchar(1000) DEFAULT NULL,
  `photo` varchar(100) DEFAULT NULL,
  `userID` int(11) DEFAULT NULL,
  `facebookID` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tempsportestablishment`
--

DROP TABLE IF EXISTS `tempsportestablishment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tempsportestablishment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateAdded` timestamp NULL DEFAULT NULL,
  `extractedWord` varchar(1000) DEFAULT NULL,
  `gymID` varchar(100) DEFAULT NULL,
  `status` varchar(1000) DEFAULT NULL,
  `photo` varchar(100) DEFAULT NULL,
  `facebookID` varchar(45) DEFAULT NULL,
  `userID` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `useraccountandinfo`
--

DROP TABLE IF EXISTS `useraccountandinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccountandinfo` (
  `userID` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `fullName` varchar(100) DEFAULT NULL,
  `birthdate` timestamp NULL DEFAULT NULL,
  `gender` varchar(45) DEFAULT NULL,
  `heightInInches` double DEFAULT NULL,
  `weight` double DEFAULT NULL,
  `contactNumber` varchar(45) DEFAULT NULL,
  `emailAddress` varchar(100) DEFAULT NULL,
  `emergencyPerson` varchar(100) DEFAULT NULL,
  `emergencyContactNumber` varchar(45) DEFAULT NULL,
  `allergies` varchar(1000) DEFAULT NULL,
  `knownHealthProblems` varchar(1000) DEFAULT NULL,
  `fbAccessToken` varchar(300) DEFAULT NULL,
  `photo` varchar(100) DEFAULT NULL,
  `userAccessToken` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `verifytable`
--

DROP TABLE IF EXISTS `verifytable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `verifytable` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateAdded` timestamp NULL DEFAULT NULL,
  `status` varchar(200) DEFAULT NULL,
  `extractedWord` varchar(1000) DEFAULT NULL,
  `category` varchar(100) DEFAULT NULL,
  `fbPostID` int(11) DEFAULT NULL,
  `photo` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `weighttracker`
--

DROP TABLE IF EXISTS `weighttracker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `weighttracker` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateAdded` timestamp NULL DEFAULT NULL,
  `weightInPounds` double DEFAULT NULL,
  `status` varchar(1000) DEFAULT NULL,
  `photo` varchar(100) DEFAULT NULL,
  `facebookID` varchar(45) DEFAULT NULL,
  `userID` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `weightUserID` (`userID`),
  CONSTRAINT `weightUserID` FOREIGN KEY (`userID`) REFERENCES `useraccountandinfo` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-11-10 16:48:58
