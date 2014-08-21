CREATE DATABASE  IF NOT EXISTS `phr` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `phr`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: phr
-- ------------------------------------------------------
-- Server version	5.6.17

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
-- Table structure for table `bloodpressure`
--

DROP TABLE IF EXISTS `bloodpressure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bloodpressure` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `systolic` varchar(100) NOT NULL,
  `diastolic` varchar(100) NOT NULL,
  `date` varchar(100) NOT NULL,
  `time` varchar(100) NOT NULL,
  `status` varchar(100) DEFAULT NULL,
  `userID` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `userID_idx` (`userID`),
  CONSTRAINT `userID` FOREIGN KEY (`userID`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bloodpressure`
--

LOCK TABLES `bloodpressure` WRITE;
/*!40000 ALTER TABLE `bloodpressure` DISABLE KEYS */;
/*!40000 ALTER TABLE `bloodpressure` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `clientid` varchar(45) NOT NULL,
  `clientpassword` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `clientid_UNIQUE` (`clientid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (1,'9543ED1349084DA816F103234217FED7A8627621','Y9xSazM4fHrkNd8tMKPkbjeqKAl4YE8QXGiJ');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logs`
--

DROP TABLE IF EXISTS `logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `logs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message` varchar(100) NOT NULL,
  `ip` varchar(45) NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logs`
--

LOCK TABLES `logs` WRITE;
/*!40000 ALTER TABLE `logs` DISABLE KEYS */;
INSERT INTO `logs` VALUES (1,'User admin has logged in.','127.0.0.1','2014-08-20 13:54:56'),(2,'An error has occured while processing a request','0:0:0:0:0:0:0:1','2014-08-20 14:01:22'),(3,'Alert! Somebody tried to access the web server without passing a JSONObject. Potential Attacker','0:0:0:0:0:0:0:1','2014-08-20 14:03:47'),(4,'Alert! Somebody tried to access the web server without passing a JSONObject. Potential Attacker','0:0:0:0:0:0:0:1','2014-08-20 14:03:54'),(5,'Alert! Somebody tried to access the web server without passing a JSONObject. Potential Attacker','0:0:0:0:0:0:0:1','2014-08-20 14:11:21'),(6,'Alert! Somebody tried to access the web server without passing a JSONObject. Potential Attacker','0:0:0:0:0:0:0:1','2014-08-21 02:36:12'),(7,'User givmefive5 has logged in.','127.0.0.1','2014-08-21 02:39:13'),(8,'User givmefive5 has logged in.','127.0.0.1','2014-08-21 02:39:33'),(9,'User givmefive5 has logged in.','127.0.0.1','2014-08-21 02:41:13'),(10,'User givmefive5 has logged in.','127.0.0.1','2014-08-21 02:42:46'),(11,'User givmefive5 has logged in.','127.0.0.1','2014-08-21 02:45:00');
/*!40000 ALTER TABLE `logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(128) NOT NULL,
  `name` varchar(45) NOT NULL,
  `dateOfBirth` date NOT NULL,
  `accessToken` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `accessToken_UNIQUE` (`accessToken`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (13,'givmefive5','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','Matthew Go','1994-05-13','[B@442d2ee9'),(14,'admin','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','Administrator','1997-06-30','[B@421e2f91');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `validateip`
--

DROP TABLE IF EXISTS `validateip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `validateip` (
  `idvalidateIp` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(100) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`idvalidateIp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `validateip`
--

LOCK TABLES `validateip` WRITE;
/*!40000 ALTER TABLE `validateip` DISABLE KEYS */;
/*!40000 ALTER TABLE `validateip` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-08-21 11:55:28
