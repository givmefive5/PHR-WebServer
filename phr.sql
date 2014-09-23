CREATE DATABASE  IF NOT EXISTS `healthgem` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `healthgem`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: healthgem
-- ------------------------------------------------------
-- Server version	5.6.19

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
-- Table structure for table `activitylist`
--

DROP TABLE IF EXISTS `activitylist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activitylist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `MET` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activitylist`
--

LOCK TABLES `activitylist` WRITE;
/*!40000 ALTER TABLE `activitylist` DISABLE KEYS */;
/*!40000 ALTER TABLE `activitylist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activitytracker`
--

DROP TABLE IF EXISTS `activitytracker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activitytracker` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateAdded` TIMESTAMP NULL DEFAULT NULL,
  `activityID` int(11) DEFAULT NULL,
  `calorieBurnedPerHour` int(11) DEFAULT NULL,
  `status` varchar(200) DEFAULT NULL,
  `photo` varchar(100) DEFAULT NULL,
  `fbPostID` int(11) DEFAULT NULL,
  `userID` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userID` (`userID`),
  KEY `activityID` (`activityID`),
  CONSTRAINT `activityID` FOREIGN KEY (`activityID`) REFERENCES `activitylist` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activitytracker`
--

LOCK TABLES `activitytracker` WRITE;
/*!40000 ALTER TABLE `activitytracker` DISABLE KEYS */;
/*!40000 ALTER TABLE `activitytracker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bloodpressuretracker`
--

DROP TABLE IF EXISTS `bloodpressuretracker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bloodpressuretracker` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateAdded` TIMESTAMP NULL DEFAULT NULL,
  `systolic` varchar(100) DEFAULT NULL,
  `diastolic` varchar(100) DEFAULT NULL,
  `status` varchar(200) DEFAULT NULL,
  `photo` varchar(100) DEFAULT NULL,
  `fbPostID` int(11) DEFAULT NULL,
  `userID` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `bpUserID` (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bloodpressuretracker`
--

LOCK TABLES `bloodpressuretracker` WRITE;
/*!40000 ALTER TABLE `bloodpressuretracker` DISABLE KEYS */;
INSERT INTO `bloodpressuretracker` VALUES (7,'2014-09-11 00:00:00','0','0','ww',NULL,NULL,1);
/*!40000 ALTER TABLE `bloodpressuretracker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bloodsugartracker`
--

DROP TABLE IF EXISTS `bloodsugartracker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bloodsugartracker` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateAdded` TIMESTAMP NULL DEFAULT NULL,
  `bloodSugar` decimal(11,0) DEFAULT NULL,
  `type` varchar(200) DEFAULT NULL,
  `status` varchar(200) DEFAULT NULL,
  `photo` varchar(100) DEFAULT NULL,
  `fbPostID` int(11) DEFAULT NULL,
  `userID` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `bloodsugarUserID` (`userID`),
  CONSTRAINT `bloodsugarUserID` FOREIGN KEY (`userID`) REFERENCES `useraccountandinfo` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bloodsugartracker`
--

LOCK TABLES `bloodsugartracker` WRITE;
/*!40000 ALTER TABLE `bloodsugartracker` DISABLE KEYS */;
/*!40000 ALTER TABLE `bloodsugartracker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `checkuptracker`
--

DROP TABLE IF EXISTS `checkuptracker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `checkuptracker` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateAdded` TIMESTAMP NULL DEFAULT NULL,
  `purpose` varchar(100) DEFAULT NULL,
  `doctorsName` varchar(100) DEFAULT NULL,
  `notes` varchar(100) DEFAULT NULL,
  `status` varchar(200) DEFAULT NULL,
  `photo` varchar(100) DEFAULT NULL,
  `fbPostID` int(11) DEFAULT NULL,
  `userID` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `checkupUserID` (`userID`),
  CONSTRAINT `checkupUserID` FOREIGN KEY (`userID`) REFERENCES `useraccountandinfo` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checkuptracker`
--

LOCK TABLES `checkuptracker` WRITE;
/*!40000 ALTER TABLE `checkuptracker` DISABLE KEYS */;
/*!40000 ALTER TABLE `checkuptracker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `foodlist`
--

DROP TABLE IF EXISTS `foodlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `foodlist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `calorie` varchar(100) DEFAULT NULL,
  `servingUnit` varchar(100) DEFAULT NULL,
  `servingSize` int(11) DEFAULT NULL,
  `restaurantID` int(11) DEFAULT NULL,
  `fromFatsecret` binary(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `restaurantID` (`restaurantID`),
  CONSTRAINT `restaurantID` FOREIGN KEY (`id`) REFERENCES `restaurantlist` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `foodlist`
--

LOCK TABLES `foodlist` WRITE;
/*!40000 ALTER TABLE `foodlist` DISABLE KEYS */;
/*!40000 ALTER TABLE `foodlist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `foodtracker`
--

DROP TABLE IF EXISTS `foodtracker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `foodtracker` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateAdded` TIMESTAMP NULL DEFAULT NULL,
  `foodID` int(11) DEFAULT NULL,
  `servingCount` int(11) DEFAULT NULL,
  `status` varchar(200) DEFAULT NULL,
  `photo` varchar(100) DEFAULT NULL,
  `fbPostID` int(11) DEFAULT NULL,
  `userID` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `foodID` (`foodID`),
  KEY `userID` (`userID`),
  CONSTRAINT `foodID` FOREIGN KEY (`foodID`) REFERENCES `foodlist` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `userID` FOREIGN KEY (`userID`) REFERENCES `useraccountandinfo` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `foodtracker`
--

LOCK TABLES `foodtracker` WRITE;
/*!40000 ALTER TABLE `foodtracker` DISABLE KEYS */;
/*!40000 ALTER TABLE `foodtracker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notestracker`
--

DROP TABLE IF EXISTS `notestracker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notestracker` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateAdded` TIMESTAMP NULL DEFAULT NULL,
  `note` varchar(200) DEFAULT NULL,
  `status` varchar(200) DEFAULT NULL,
  `photo` varchar(100) DEFAULT NULL,
  `fbPostID` int(11) DEFAULT NULL,
  `userID` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `notesUserID` (`userID`),
  CONSTRAINT `notesUserID` FOREIGN KEY (`userID`) REFERENCES `useraccountandinfo` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notestracker`
--

LOCK TABLES `notestracker` WRITE;
/*!40000 ALTER TABLE `notestracker` DISABLE KEYS */;
/*!40000 ALTER TABLE `notestracker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurantlist`
--

DROP TABLE IF EXISTS `restaurantlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `restaurantlist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurantlist`
--

LOCK TABLES `restaurantlist` WRITE;
/*!40000 ALTER TABLE `restaurantlist` DISABLE KEYS */;
/*!40000 ALTER TABLE `restaurantlist` ENABLE KEYS */;
UNLOCK TABLES;

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
  `firstName` varchar(100) DEFAULT NULL,
  `middleName` varchar(100) DEFAULT NULL,
  `lastName` varchar(100) DEFAULT NULL,
  `birthdate` date DEFAULT NULL,
  `gender` varchar(45) DEFAULT NULL,
  `heightInInches` int(50) DEFAULT NULL,
  `contactNumber` int(50) DEFAULT NULL,
  `emailAddress` varchar(100) DEFAULT NULL,
  `fbEmailAddress` varchar(45) DEFAULT NULL,
  `fbAccessToken` varchar(100) DEFAULT NULL,
  `photo` varchar(100) DEFAULT NULL,
  `userAccessToken` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccountandinfo`
--

LOCK TABLES `useraccountandinfo` WRITE;
/*!40000 ALTER TABLE `useraccountandinfo` DISABLE KEYS */;
INSERT INTO `useraccountandinfo` VALUES (1,'admin','1234',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'4e443873-82b1-428a-b8e6-3cf4c3e1378e');
/*!40000 ALTER TABLE `useraccountandinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `verifytable`
--

DROP TABLE IF EXISTS `verifytable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `verifytable` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateAdded` TIMESTAMP NULL DEFAULT NULL,
  `status` varchar(200) DEFAULT NULL,
  `extractedWord` varchar(100) DEFAULT NULL,
  `category` varchar(100) DEFAULT NULL,
  `fbPostID` int(11) DEFAULT NULL,
  `photo` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verifytable`
--

LOCK TABLES `verifytable` WRITE;
/*!40000 ALTER TABLE `verifytable` DISABLE KEYS */;
/*!40000 ALTER TABLE `verifytable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `weighttracker`
--

DROP TABLE IF EXISTS `weighttracker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `weighttracker` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateAdded` TIMESTAMP NULL DEFAULT NULL,
  `weightInPounds` double DEFAULT NULL,
  `status` varchar(200) DEFAULT NULL,
  `photo` varchar(100) DEFAULT NULL,
  `fbPostID` int(11) DEFAULT NULL,
  `userID` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `weightUserID` (`userID`),
  CONSTRAINT `weightUserID` FOREIGN KEY (`userID`) REFERENCES `useraccountandinfo` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `weighttracker`
--

LOCK TABLES `weighttracker` WRITE;
/*!40000 ALTER TABLE `weighttracker` DISABLE KEYS */;
/*!40000 ALTER TABLE `weighttracker` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-09-15 14:58:10
