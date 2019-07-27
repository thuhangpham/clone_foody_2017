-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: foody
-- ------------------------------------------------------
-- Server version	5.6.28-ndb-7.4.10-cluster-gpl

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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `username` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `password` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `email` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `imageURL` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gender` tinyint(1) DEFAULT '0',
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created` decimal(20,0) DEFAULT '20170101000000',
  `firstname` varchar(20) CHARACTER SET utf8 DEFAULT '',
  `lastname` varchar(20) CHARACTER SET utf8 DEFAULT '',
  `url` varchar(100) CHARACTER SET utf8 DEFAULT '',
  `dob` varchar(40) CHARACTER SET utf8 DEFAULT '',
  `relationship` varchar(40) CHARACTER SET utf8 DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (14,NULL,'hang','qwer','sjsjd@zx.com',NULL,NULL,NULL,20170517183209,NULL,NULL,NULL,NULL,NULL),(15,NULL,'hang','qwer','sjss@xd.com',NULL,NULL,NULL,20170517183743,NULL,NULL,NULL,NULL,NULL),(16,NULL,'hang','qwer','vjkgfcg.jj@bn.com',NULL,NULL,NULL,20170517183940,NULL,NULL,NULL,NULL,NULL),(17,NULL,'hang','qwer','bjk@gh.com',NULL,NULL,NULL,20170517184129,NULL,NULL,NULL,NULL,NULL),(18,NULL,'hang','ssss','fhj@hj.com',NULL,NULL,NULL,20170517184339,NULL,NULL,NULL,NULL,NULL),(19,NULL,'hang','qwer','esjksj@dd.com',NULL,NULL,NULL,20170517190357,NULL,NULL,NULL,NULL,NULL),(20,NULL,'jjju','qwer','ghuu@gy.com',NULL,NULL,NULL,20170517191702,NULL,NULL,NULL,NULL,NULL),(21,NULL,'hjhhj','llll','fcggg@n.com',NULL,NULL,NULL,20170517192442,NULL,NULL,NULL,NULL,NULL),(22,NULL,'gfg','qqqq','dfddds@xx.com',NULL,NULL,NULL,20170517194016,NULL,NULL,NULL,NULL,NULL),(23,NULL,'sddf','qwer','cvhj@ji.com',NULL,NULL,NULL,20170517195009,NULL,NULL,NULL,NULL,NULL),(24,NULL,'ghjs','qqqq','wjsjsjs@sd.com',NULL,NULL,NULL,20170517195533,NULL,NULL,NULL,NULL,NULL),(25,NULL,'hhj','qqqqq','chjj@hj.com',NULL,NULL,NULL,20170517195706,NULL,NULL,NULL,NULL,NULL),(26,NULL,'fghmmmm','lllll','cvbnm@hj.com',NULL,NULL,NULL,20170517200244,NULL,NULL,NULL,NULL,NULL),(27,NULL,'hang','ssss','asj@zz.com',NULL,NULL,NULL,20170517200856,NULL,NULL,NULL,NULL,NULL),(28,NULL,'ddkd','qqqq','sjsj@zs.com',NULL,NULL,NULL,20170517201250,NULL,NULL,NULL,NULL,NULL),(29,NULL,'aaaff','qqqq','ajja@ss.com',NULL,NULL,NULL,20170517201411,NULL,NULL,NULL,NULL,NULL),(30,NULL,'habsf','qqqqq','sjsjsj@zs.com',NULL,NULL,NULL,20170517201523,NULL,NULL,NULL,NULL,NULL),(31,NULL,'qqqqq','qqqqq','sjskjsjsj@sss.com',NULL,NULL,NULL,20170517201700,NULL,NULL,NULL,NULL,NULL),(32,NULL,'wle','ssss','sjdj@xss.com',NULL,NULL,NULL,20170517201923,NULL,NULL,NULL,NULL,NULL),(33,NULL,'hakd','qqqq','hss@d.com',NULL,NULL,NULL,20170517202611,NULL,NULL,NULL,NULL,NULL),(34,NULL,'dkdx','qqqq','hdj@dd.com',NULL,NULL,NULL,20170517204943,NULL,NULL,NULL,NULL,NULL),(35,NULL,'jwkdkd','qqqq','djejd@dd.com',NULL,NULL,NULL,20170517205029,NULL,NULL,NULL,NULL,NULL),(36,NULL,'qqqq','qqqq','jjzjs@s.com',NULL,NULL,NULL,20170517205205,NULL,NULL,NULL,NULL,NULL),(37,NULL,'skdkd','qqqq','sjsj@sd.com',NULL,NULL,NULL,20170517205239,NULL,NULL,NULL,NULL,NULL),(38,NULL,'jfjdjd','qqqq','siss@sd.com',NULL,NULL,NULL,20170517205325,NULL,NULL,NULL,NULL,NULL),(39,NULL,'sffg','qqqq','jsjjsx@ss.com',NULL,NULL,NULL,20170517205642,NULL,NULL,NULL,NULL,NULL),(40,NULL,'hhb','qqqq','vjjcj@bb.com',NULL,NULL,NULL,20170517205745,NULL,NULL,NULL,NULL,NULL),(41,NULL,'qkjff','qqqq','qqq@ss.com',NULL,NULL,NULL,20170517212930,NULL,NULL,NULL,NULL,NULL),(42,NULL,'gfwwx','qqqq','sss@czz.com',NULL,NULL,NULL,20170517213407,NULL,NULL,NULL,NULL,NULL),(43,NULL,'fkqs','qqqq','asj@s.com',NULL,NULL,NULL,20170517213618,NULL,NULL,NULL,NULL,NULL),(44,NULL,'djddf','qqqq','cvvg@zs.com',NULL,NULL,NULL,20170517213845,NULL,NULL,NULL,NULL,NULL),(45,NULL,'hjxhj','qqqq','dfggg@h.com',NULL,NULL,NULL,20170517214001,NULL,NULL,NULL,NULL,NULL),(46,NULL,'hjxhj','qqqq','dfggg@h.com',NULL,NULL,NULL,20170517214009,NULL,NULL,NULL,NULL,NULL),(47,'thuhang3',NULL,NULL,NULL,NULL,NULL,NULL,20170517214256,NULL,NULL,NULL,NULL,NULL),(48,NULL,'slfgh','qqqq','shsjsj@xd.com',NULL,NULL,NULL,20170517214902,NULL,NULL,NULL,NULL,NULL),(49,NULL,'dsjfd','qqqq','hahsh@xd.com',NULL,NULL,NULL,20170517233053,NULL,NULL,NULL,NULL,NULL),(50,NULL,'jfjdbj','qqqq','sjsj@dd.com',NULL,NULL,NULL,20170517234312,NULL,NULL,NULL,NULL,NULL),(51,NULL,'eddeee','qqqqq','xxxs@dd.com',NULL,NULL,NULL,20170518015858,NULL,NULL,NULL,NULL,NULL),(52,NULL,'djkdjdjf','qqqq','gansn@zs.com',NULL,NULL,NULL,20170518020202,NULL,NULL,NULL,NULL,NULL),(53,NULL,'hang','qqqq','a@a.com',NULL,NULL,NULL,20170518043056,NULL,NULL,NULL,NULL,NULL),(54,NULL,'edf','qqqq','sjs@xd.com',NULL,NULL,NULL,20170518050728,NULL,NULL,NULL,NULL,NULL),(55,NULL,'ksks','qqqq','aakk@ss.com',NULL,NULL,NULL,20170518072853,NULL,NULL,NULL,NULL,NULL),(56,NULL,'dkfx','qqqq','jjd@dd.com',NULL,NULL,NULL,20170518073103,NULL,NULL,NULL,NULL,NULL),(57,NULL,'qqqqq','qqqq','q@q.com',NULL,NULL,NULL,20170518074412,'hng','',NULL,NULL,NULL),(58,NULL,'qqqq','qqqq','qhdjd@a.com',NULL,NULL,NULL,20170518183349,'hng','pgam',NULL,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-19  0:43:15
