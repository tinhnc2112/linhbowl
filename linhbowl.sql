-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: linhbowl
-- ------------------------------------------------------
-- Server version	5.7.36-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cart_items`
--

DROP TABLE IF EXISTS `cart_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdagcsk6v6x4n1kxw3rkp57921` (`customer_id`),
  KEY `FK1re40cjegsfvw58xrkdp6bac6` (`product_id`),
  CONSTRAINT `FK1re40cjegsfvw58xrkdp6bac6` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `FKdagcsk6v6x4n1kxw3rkp57921` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_items`
--

LOCK TABLES `cart_items` WRITE;
/*!40000 ALTER TABLE `cart_items` DISABLE KEYS */;
INSERT INTO `cart_items` VALUES (23,2,9,1),(27,3,9,1),(28,1,16,2),(29,1,9,1);
/*!40000 ALTER TABLE `cart_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `enabled` bit(1) NOT NULL,
  `image` varchar(128) NOT NULL,
  `name` varchar(128) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_t8o6pivur7nn124jehx7cygw5` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,_binary '','Poke Bowl and rice_ Illustrator graphic_.png','Rice'),(2,_binary '','Poke Bowl and rice_ Illustrator graphic_.png','Pho Noodle Soup'),(4,_binary '','4-removebg-preview.png','Vegetable');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(64) NOT NULL,
  `email` varchar(45) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `password` varchar(64) NOT NULL,
  `phone_number` varchar(15) NOT NULL,
  `verification_code` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_rfbvkrffamfql7cjmen8v976v` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'haiduong','tinhnc2112@gmail.com',_binary '','tinh','nguyen cong','$2a$10$3eSTehG5fLF72sHlTvj1rOewH4hGyE84tWGkPKKXbISpZW/Erpaiu','',NULL),(2,'HD','tinhnc2811@gmail.com',_binary '','tinh','nguyen','$2a$10$WJUarO7dWJzk7tLohnbbiO4gjsjlcptk3wl/ZNHow19y20PC/styu','012345678',NULL),(3,'','phuc28112000@gmail.com',_binary '','phuc','trinh','$2a$10$r.B7u2wUCuoTg0DBMs5oaOT5wNo6aRAJyWEdOas3Qc80brWFxshey','',NULL),(4,'hn','tranhieu200914@gmail.com',_binary '\0','hieu','tran','$2a$10$fKVfz7wethgmFP9RRfueJuopx9lMnVmwaIqeA3c3U62jXTTRwvDR.','012345678','R2vC2CYBpeADoq4yoRK60y66xcRjfI5kbiAGjdJoW5aHbhflIqeteBERttRLp80V');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `full_description` varchar(4096) NOT NULL,
  `name` varchar(256) NOT NULL,
  `photo` varchar(255) NOT NULL,
  `price` float NOT NULL,
  `short_description` varchar(512) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `category_id` int(11) DEFAULT NULL,
  `description` varchar(4096) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_o61fmio5yukmmiqgnxf8pnavn` (`name`),
  KEY `FKog2rp4qthbtt2lfyhfo32lsw9` (`category_id`),
  CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Six different fruits and vegetables are included in this recipe. As fruits and vegetables contain different combinations of fibre, vitamins, minerals and other nutrients it is always good to eat a variety to get the most benefit. This vegetable rice certainly gives you variety.','Rice healthy','best 3.png',24,'abc',_binary '',1,'Six different fruits and vegetables are included in this recipe. As fruits and vegetables contain different combinations of fibre, vitamins, minerals and other nutrients it is always good to eat a variety to get the most benefit. This vegetable rice certainly gives you variety.'),(2,'Six different fruits and vegetables are included in this recipe. As fruits and vegetables contain different combinations of fibre, vitamins, minerals and other nutrients it is always good to eat a variety to get the most benefit. This vegetable rice certainly gives you variety.','Pho noodle soup','best 2.png',20,'abc',_binary '',2,'Six different fruits and vegetables are included in this recipe. As fruits and vegetables contain different combinations of fibre, vitamins, minerals and other nutrients it is always good to eat a variety to get the most benefit. This vegetable rice certainly gives you variety.'),(9,'Six different fruits and vegetables are included in this recipe. As fruits and vegetables contain different combinations of fibre, vitamins, minerals and other nutrients it is always good to eat a variety to get the most benefit. This vegetable rice certainly gives you variety.','Cari','best 1.png',24,'<div>adc</div>',_binary '',1,'Six different fruits and vegetables are included in this recipe. As fruits and vegetables contain different combinations of fibre, vitamins, minerals and other nutrients it is always good to eat a variety to get the most benefit. This vegetable rice certainly gives you variety.'),(10,'Six different fruits and vegetables are included in this recipe. As fruits and vegetables contain different combinations of fibre, vitamins, minerals and other nutrients it is always good to eat a variety to get the most benefit. This vegetable rice certainly gives you variety.','Pho ','pho_1.png',20,'<div>abc</div>',_binary '',2,'Six different fruits and vegetables are included in this recipe. As fruits and vegetables contain different combinations of fibre, vitamins, minerals and other nutrients it is always good to eat a variety to get the most benefit. This vegetable rice certainly gives you variety.'),(16,'Six different fruits and vegetables are included in this recipe. As fruits and vegetables contain different combinations of fibre, vitamins, minerals and other nutrients it is always good to eat a variety to get the most benefit. This vegetable rice certainly gives you variety.','Beef Rice','beef_rice.png',30,'<div><br></div>',_binary '',1,'Six different fruits and vegetables are included in this recipe. As fruits and vegetables contain different combinations of fibre, vitamins, minerals and other nutrients it is always good to eat a variety to get the most benefit. This vegetable rice certainly gives you variety.');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(150) NOT NULL,
  `name` varchar(40) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ofx66keruapi6vyqpv6f2or37` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'manage everything','Admin'),(2,'view products, view orders and update order status','Shipper');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `settings`
--

DROP TABLE IF EXISTS `settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `settings` (
  `key` varchar(128) NOT NULL,
  `value` varchar(1024) NOT NULL,
  `category` varchar(45) NOT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `settings`
--

LOCK TABLES `settings` WRITE;
/*!40000 ALTER TABLE `settings` DISABLE KEYS */;
INSERT INTO `settings` VALUES ('CUSTOMER_VERIFY_CONTENT','<span style=\"font-size:16px;\">Dear [[name]],<br>\r\nClick to link below to verify your account.</span><div><span style=\"font-size:16px;\"><br><b><font color=\"#0000ff\">\r\n</font></b></span><h2>\r\n<a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h2><div><span style=\"font-size:16px;\"><b><font color=\"#0000ff\"><a href=\"[[URL]]\" target=\"_self\"></a><span style=\"font-size:18px;\"></span></font></b><br>\r\nThanks you,<br>\r\nThe LinhBowl Team</span><span style=\"font-size:16px;\"></span><span style=\"font-size:16px;\"></span></div></div>','MAIL_TEMPLATES'),('CUSTOMER_VERIFY_SUBJECT','Please verify your registration to continue shopping','MAIL_TEMPLATES'),('MAIL_FROM','linkbowlteam@gmail.com','MAIL_SERVER'),('MAIL_HOST','smtp.gmail.com','MAIL_SERVER'),('MAIL_PASSWORD','zahgvtxeqarijodw','MAIL_SERVER'),('MAIL_PORT','587','MAIL_SERVER'),('MAIL_SENDER_NAME','LinhBowl kitchen','MAIL_SERVER'),('MAIL_USERNAME','linkbowlteam@gmail.com','MAIL_SERVER'),('SMTP_AUTH','true','MAIL_SERVER'),('SMTP_SECURED','true','MAIL_SERVER');
/*!40000 ALTER TABLE `settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(128) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `password` varchar(64) NOT NULL,
  `photo` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,'linh@gmail.com',_binary '','linh','do thi','$2a$10$zg49doOZC86VJ3yRKOSl..CjF.vD3bkRLb6gwxaDipziEatXOC0ei','Svetlana Stasovska.png'),(4,'nam@gmail.com',_binary '','nam','nguyen cong','$2a$10$k29OkT2r4aEblS3mCs9BieaJ7.vpyjZppPEeZoONZrKoDRLrrnvJ2','Ali Raza.png'),(6,'tinh@gmail.com',_binary '','tinh','nguyen cong','$2a$10$7wyzlu35Ghsz5DgvalxVdOtBYO0xdvUNTaraw1ahZqt.bUnXuduIC','Isaac Henry.jpg'),(7,'tinh1@gmail.com',_binary '\0','tinh','nguyen cong','$2a$10$.hRGjqfxz16qcIMLpFq/x.6s0AkPkvKjAOk4k1waXenZAi1YXON22','Mike Gates.png'),(8,'dave_kuma@gmail.com',_binary '','dave','kuma','$2a$10$qKhjK7VccV5GcIwWkTSwnesTqRvI8KH1vMM4aYs0m64HWk1PupWLK','Dave Kumar.png'),(11,'tinhnc@ptit.edu.vn',_binary '\0','nam1','nguyen hoai','$2a$10$VEmo63pY8cqiywwIEspyHObFgz4jsLDrOopNQ7/GDZk9r/5hGkAra','Mohamed Zirri.jpg'),(12,'duc@gmail.com',_binary '','duc','nguyen','$2a$10$kfyM094Mte3qvi.a.Kalc.QM6OshUAx/4cTGK3/u/vKPjE3w1yWeG',NULL),(14,'duc1@gmail.com',_binary '','duc','vu','$2a$10$GNIWgj3hOFBmM/GPle/ogufc2qB9eiDJNCZl5AkO8bB4nKrrbhuKe',NULL),(15,'phuc28112000@gmail.com',_binary '','trinh','phuc','$2a$10$O95/7hsfwcTpfVusMqjoQ.HCvqPOjW359QmMOabL4XfwcTiMyR1UO',NULL),(16,'phuc281120001@gmail.com',_binary '','phuc','trinh','$2a$10$U8F9xEtiq7OGn.cKh.xaMeCP9.9dsx9aczSrXs/2gbWjnOjCQYuoa',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKj6m8fwv7oqv74fcehir1a9ffy` (`role_id`),
  CONSTRAINT `FK2o0jvgh89lemvvo17cbqvdxaa` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKj6m8fwv7oqv74fcehir1a9ffy` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` VALUES (2,1),(14,1),(15,1),(16,1),(4,2),(6,2),(8,2),(11,2),(12,2);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-14 12:31:05
