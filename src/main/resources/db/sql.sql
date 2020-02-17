-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: trainingdb
-- ------------------------------------------------------
-- Server version	8.0.18


CREATE DATABASE IF NOT EXISTS `trainingdb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `trainingdb`;


/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `cruises`
--

DROP TABLE IF EXISTS `cruises`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cruises`
(
    `id`              bigint(20)   NOT NULL,
    `arrival_date`    date         NOT NULL,
    `cruise_name`     varchar(255) NOT NULL,
    `departure_date`  date         NOT NULL,
    `ship_id`         bigint(20)   DEFAULT NULL,
    `description_eng` varchar(255) DEFAULT NULL,
    `description_ru`  varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UKd7b8sswu7pgbcyxm5ntn6eot7` (`cruise_name`),
    KEY `FK8pwrwe2q2etr3sqp31cxuf519` (`ship_id`),
    CONSTRAINT `FK8pwrwe2q2etr3sqp31cxuf519` FOREIGN KEY (`ship_id`) REFERENCES `ships` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cruises`
--

LOCK TABLES `cruises` WRITE;
/*!40000 ALTER TABLE `cruises`
    DISABLE KEYS */;
INSERT INTO `cruises`
VALUES (1, '2020-01-03', 'Costa Cruise', '2019-12-28', 1, 'FIrst!', 'Первый!'),
       (2, '2020-01-17', 'Carnival', '2020-01-09', 2, 'Normal cruise', 'Нормальный круиз'),
       (3, '2020-01-24', 'Titanic', '2020-01-18', 3, 'Not good enough cruise', 'Не очень хороший круиз');
/*!40000 ALTER TABLE `cruises`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `excursions`
--

DROP TABLE IF EXISTS `excursions`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `excursions`
(
    `id`             bigint(20) NOT NULL,
    `duration`       int(11)      DEFAULT NULL,
    `excursion_name` varchar(255) DEFAULT NULL,
    `port_id`        bigint(20)   DEFAULT NULL,
    `price`          bigint(20) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK1ns0i1qr50qt0ojhktxc2wo7m` (`excursion_name`),
    KEY `FKrsuu6nrlfh4rxaw8t6vdv1lje` (`port_id`),
    CONSTRAINT `FKrsuu6nrlfh4rxaw8t6vdv1lje` FOREIGN KEY (`port_id`) REFERENCES `ports` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `excursions`
--

LOCK TABLES `excursions` WRITE;
/*!40000 ALTER TABLE `excursions`
    DISABLE KEYS */;
INSERT INTO `excursions`
VALUES (1, 1, 'name1', 1, 100),
       (2, 2, 'name2', 1, 100),
       (3, 3, 'name3', 1, 100),
       (4, 4, 'name4', 1, 100),
       (5, 3, 'name5', 2, 100),
       (6, 2, 'name16', 3, 100),
       (7, 1, 'name7', 2, 100),
       (8, 1, 'name8', 2, 100),
       (9, 3, 'name9', 2, 100),
       (10, 2, 'name20', 1, 100),
       (11, 6, 'name12', 3, 100);
/*!40000 ALTER TABLE `excursions`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence`
(
    `next_val` bigint(20) DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence`
    DISABLE KEYS */;
INSERT INTO `hibernate_sequence`
VALUES (237);
/*!40000 ALTER TABLE `hibernate_sequence`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders`
(
    `id`          bigint(20) NOT NULL,
    `cruise_id`   bigint(20) NOT NULL,
    `user_id`     bigint(20) NOT NULL,
    `ticket_id`   bigint(20) NOT NULL,
    `first_name`  varchar(255) DEFAULT NULL,
    `second_name` varchar(255) DEFAULT NULL,
    `price`       bigint(20) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FKq0ico9u9gr90i8yvbql4pdcpj` (`cruise_id`),
    KEY `FK32ql8ubntj5uh44ph9659tiih` (`user_id`),
    KEY `FKsesssrpdbpddyr7a4t4nyiy7p` (`ticket_id`),
    CONSTRAINT `FK32ql8ubntj5uh44ph9659tiih` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    CONSTRAINT `FKq0ico9u9gr90i8yvbql4pdcpj` FOREIGN KEY (`cruise_id`) REFERENCES `cruises` (`id`),
    CONSTRAINT `FKsesssrpdbpddyr7a4t4nyiy7p` FOREIGN KEY (`ticket_id`) REFERENCES `ticket` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders`
    DISABLE KEYS */;
INSERT INTO `orders`
VALUES (227, 1, 11, 1, 'Tymofii', 'Hodik', 1100),
       (228, 1, 11, 1, 'Alexey', 'Hodik', 1100),
       (229, 1, 11, 1, 'Maxim', 'Maxim', 1000),
       (231, 1, 11, 1, 'Egor', 'Egor', 900),
       (234, 1, 11, 1, 'Artur', 'Artur', 1200);
/*!40000 ALTER TABLE `orders`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders_excursions`
--

DROP TABLE IF EXISTS `orders_excursions`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders_excursions`
(
    `order_id`     bigint(20) NOT NULL,
    `excursion_id` bigint(20) NOT NULL,
    KEY `FKm9tsgwk7s2q1twooph5hx9h3c` (`excursion_id`),
    KEY `FKagr7jfxtsbke4891f0jjy7td3` (`order_id`),
    CONSTRAINT `FKagr7jfxtsbke4891f0jjy7td3` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
    CONSTRAINT `FKm9tsgwk7s2q1twooph5hx9h3c` FOREIGN KEY (`excursion_id`) REFERENCES `excursions` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders_excursions`
--

LOCK TABLES `orders_excursions` WRITE;
/*!40000 ALTER TABLE `orders_excursions`
    DISABLE KEYS */;
INSERT INTO `orders_excursions`
VALUES (227, 6),
       (227, 8),
       (228, 5),
       (228, 8),
       (229, 6),
       (234, 7),
       (234, 9),
       (234, 11);
/*!40000 ALTER TABLE `orders_excursions`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ports`
--

DROP TABLE IF EXISTS `ports`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ports`
(
    `id`        bigint(20) NOT NULL,
    `port_name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UKlvrhlh5tcwxby1ao6cnxl76sd` (`port_name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ports`
--

LOCK TABLES `ports` WRITE;
/*!40000 ALTER TABLE `ports`
    DISABLE KEYS */;
INSERT INTO `ports`
VALUES (1, 'America'),
       (3, 'India'),
       (2, 'Odessa');
/*!40000 ALTER TABLE `ports`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ports_cruises`
--

DROP TABLE IF EXISTS `ports_cruises`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ports_cruises`
(
    `cruise_id` bigint(20) NOT NULL,
    `port_id`   bigint(20) NOT NULL,
    KEY `FKe7r8tsv60e10qunc985fkx4o2` (`port_id`),
    KEY `FKtfr5fp75d9nn6ib70jivka29t` (`cruise_id`),
    CONSTRAINT `FKe7r8tsv60e10qunc985fkx4o2` FOREIGN KEY (`port_id`) REFERENCES `ports` (`id`),
    CONSTRAINT `FKtfr5fp75d9nn6ib70jivka29t` FOREIGN KEY (`cruise_id`) REFERENCES `cruises` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ports_cruises`
--

LOCK TABLES `ports_cruises` WRITE;
/*!40000 ALTER TABLE `ports_cruises`
    DISABLE KEYS */;
INSERT INTO `ports_cruises`
VALUES (1, 2),
       (1, 3),
       (1, 1),
       (2, 1),
       (2, 2),
       (3, 2),
       (3, 1);
/*!40000 ALTER TABLE `ports_cruises`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ships`
--

DROP TABLE IF EXISTS `ships`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ships`
(
    `id`                          bigint(20)   NOT NULL,
    `max_amount_of_passenger`     int(11)      NOT NULL,
    `ship_name`                   varchar(255) NOT NULL,
    `current_amount_of_passenger` int(11)      NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK83ysgfacfsnhkotxa6f2fr1kx` (`ship_name`),
    CONSTRAINT `check_passengers` CHECK ((`current_amount_of_passenger` <= `max_amount_of_passenger`))
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ships`
--

LOCK TABLES `ships` WRITE;
/*!40000 ALTER TABLE `ships`
    DISABLE KEYS */;
INSERT INTO `ships`
VALUES (1, 100, 'Costa', 100),
       (2, 5000, 'Titanic', 0),
       (3, 100000, 'test', 0);
/*!40000 ALTER TABLE `ships`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticket`
(
    `id`          bigint(20)   NOT NULL,
    `price`       bigint(20)   NOT NULL,
    `ticket_name` varchar(255) NOT NULL,
    `discount`    int(11)      NOT NULL DEFAULT '0',
    `cruise_id`   bigint(20)            DEFAULT NULL,
    `final_price` bigint(20)   NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK8yquxa7j4gh9so35bhbb8q5ae` (`ticket_name`, `cruise_id`),
    KEY `FKp2n9s4607x30xy6bkxopr8fjc` (`cruise_id`),
    CONSTRAINT `FKp2n9s4607x30xy6bkxopr8fjc` FOREIGN KEY (`cruise_id`) REFERENCES `cruises` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket`
    DISABLE KEYS */;
INSERT INTO `ticket`
VALUES (1, 1000, 'VIP', 10, 1, 900),
       (2, 500, 'CASUAL', 10, 1, 450),
       (3, 100, 'LOW', 20, 1, 80),
       (4, 2000, 'SUPER_VIP', 30, 1, 1400),
       (5, 1500, 'CASUAL', 0, 2, 1500),
       (6, 3000, 'SUPER_VIP', 0, 2, 3000),
       (151, 750, 'CLASSIC', 0, 1, 750),
       (152, 1000, 'LOW', 10, 3, 900),
       (153, 5000, 'CLASSIC', 0, 3, 5000),
       (154, 10000, 'VIP', 0, 3, 10000);
/*!40000 ALTER TABLE `ticket`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users`
(
    `id`       bigint(20)   NOT NULL,
    `active`   bit(1)       NOT NULL,
    `balance`  bigint(20)   NOT NULL,
    `login`    varchar(255) NOT NULL,
    `password` varchar(255) NOT NULL,
    `roles`    varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_ow0gan20590jrb00upg3va2fn` (`login`),
    UNIQUE KEY `UKow0gan20590jrb00upg3va2fn` (`login`),
    CONSTRAINT `users_chk_1` CHECK ((`balance` >= _utf8mb3'0'))
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users`
    DISABLE KEYS */;
INSERT INTO `users`
VALUES (11, _binary '', 45300, 'Admin', '$2a$10$HHZY0pkbvCpXThbMggDjGe0NUm1N7Q4icpLPW5IgbpQvFrTYlughq', 'ROLE_ADMIN'),
       (17, _binary '', 10000, 'Tima123', '$2a$10$DKGQLdfHq4QSh8VIsnVhKuOdnTSYThlvumYOI..UWq6pnaLlbDwP2', 'ROLE_USER');
/*!40000 ALTER TABLE `users`
    ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2020-02-17 12:36:03
