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
       (2, '2020-01-17', 'name1', '2020-01-09', 2, 'Normal cruise', 'Нормальный круиз'),
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
VALUES (1, '1', 'name1', 1, 100),
       (2, '2', 'name2', 1, 100),
       (3, '3', 'name3', 1, 100),
       (4, '4', 'name4', 1, 100),
       (5, '3', 'name5', 2, 100),
       (6, '2', 'name16', 3, 100),
       (7, '1', 'name7', 2, 100),
       (8, '1', 'name8', 2, 100),
       (9, '3', 'name9', 2, 100),
       (10, '2', 'name20', 1, 100),
       (11, '6', 'name12', 3, 100);
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
VALUES (234);
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
VALUES (217, 1, 11, 1, 'вфывфы', 'вфывфыв', 100),
       (227, 1, 11, 1, 'dasd', 'asdasda', 1100),
       (228, 1, 11, 1, 'dasda', 'sdasda', 1100),
       (229, 1, 11, 1, 'sdasd', 'asdadasd', 1000),
       (231, 1, 11, 1, 'dasdas', 'dasdasda', 900);
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
       (229, 6);
/*!40000 ALTER TABLE `orders_excursions`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `passengers`
--

DROP TABLE IF EXISTS `passengers`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `passengers`
(
    `id`          bigint(20)   NOT NULL,
    `first_name`  varchar(255) NOT NULL,
    `second_name` varchar(255) NOT NULL,
    `ship_id`     bigint(20) DEFAULT NULL,
    `ticket_id`   bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK3s3hlghcfyi901s9tl8l2wx3m` (`ship_id`),
    KEY `FKq7fkg9cyd58jrdc3dlrbc1j6o` (`ticket_id`),
    CONSTRAINT `FK3s3hlghcfyi901s9tl8l2wx3m` FOREIGN KEY (`ship_id`) REFERENCES `ships` (`id`),
    CONSTRAINT `FKq7fkg9cyd58jrdc3dlrbc1j6o` FOREIGN KEY (`ticket_id`) REFERENCES `ticket` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `passengers`
--

LOCK TABLES `passengers` WRITE;
/*!40000 ALTER TABLE `passengers`
    DISABLE KEYS */;
INSERT INTO `passengers`
VALUES (62, 'tima', 'tima', 1, 1),
       (64, 'dasdas', 'dasdasda', 1, 2),
       (66, 'dadad', 'asdasdas', 1, 3),
       (68, 'dwdadasd', 'asdasdadasda', 1, 1),
       (70, 'dasda', 'dasdad', 1, 1),
       (71, 'tima', 'tima', 2, 1),
       (73, 'tima', 'tima', 1, 1),
       (134, 'dasdasdad', 'dasdasda', 1, 1),
       (136, 'sddadas', 'dasdasdsada', 1, 1),
       (138, 'dsadasdasdasd', 'asdasdasdas', 1, 4),
       (140, 'sdasda', 'adasdasd', 2, 2),
       (148, 'dsadasdas', 'sdasdasdasda', 1, 4),
       (159, 'Dasdasda', 'Asdsadasdas', 1, 3),
       (162, 'Tymofii', 'Hodik', 1, 3),
       (165, 'Tymofii', 'Da', 1, 4),
       (167, 'Dadada', 'Dsdasdasda', 1, 2);
/*!40000 ALTER TABLE `passengers`
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
       (1, 3);
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
VALUES (1, 100, 'Costa', 80),
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
       (151, 750, 'Classic', 0, 1, 750),
       (152, 1000, 'LOW', 10, 3, 900),
       (153, 5000, 'Classic', 0, 3, 5000),
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
    CONSTRAINT `users_chk_1` CHECK ((`balance` >= '0'))
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
VALUES (1, _binary '', 0, 'second', '$2a$10$4/UBSuWJQ1fXT0fKPUsVpuSBaeRfeNCdK3JEeI6l.ALodAJFyzvtm', 'ROLE_USER'),
       (7, _binary '', 0, 'user', '$2a$10$zGI4mkJ23IA4OGFrZtVjseWB02Jau8fXenLJX42aH3XUuHg8o7qkO', 'ROLE_USER'),
       (8, _binary '', 0, 'tima', '$2a$10$FVjxY9wjzYTD8WbqnXbBNebM5Gsxl1qV.4BaNI.oEVQRyMY02PMNC', 'ROLE_USER'),
       (9, _binary '', 0, 'alex', '$2a$10$jrK3lMD94tGKInrGlcAaZe2QYLD99Ndwr0AcwQesSgC2gINjg9lcW', 'ROLE_USER'),
       (10, _binary '', 0, 'Gtm417', '$2a$10$NeH/S.cQUUsWXA3nidfEVugKt70Y3UrU09/3TwIbaYlOn5WdVGBTG', 'ROLE_USER'),
       (11, _binary '', 46500, 't', '$2a$10$fRHGOjZJV50JmMb/CZP9C.pmbIyggBW2n.ltGFZhY0XKzx5joJe4q', 'ROLE_ADMIN'),
       (32, _binary '', 0, 'Hodik', '$2a$10$6IIRIqMwh2Ik9D/2ipBCtOjr3u3pSCJcLO78HWWLKN/hU2hYsn4Ny', 'ROLE_USER'),
       (33, _binary '', 0, 'ivan', '$2a$10$zedVzfx1dYVgU4hx6nnghugNeZ4amGAydJh7fvOEVr.oG5xJwoieW', 'ROLE_USER'),
       (46, _binary '', 0, 'h', '$2a$10$Q56O0gAmr3sCF/Qyqy/my.pOfKf6TUkW4OAIwgvxsxWbpnwdBvgF.', 'ROLE_USER'),
       (47, _binary '', 0, 'l', '$2a$10$xiElM/Ggm4nTHRPrcTufbuj8k9307xFX4Zx0fdd9poWXq73.tUToi', 'ROLE_USER'),
       (48, _binary '', 0, 'tt', '$2a$10$iLwhVKXLvf8/O53QuyxDduHdPtXA8l/0vRghIysNV3FzNIOaWGinK', 'ROLE_USER'),
       (50, _binary '', 0, 'jhjh', '$2a$10$2lwbv6h7Eb7bHxd3Fhjfv.fvD/t/Pf9eq9OwEd9seOPJMDzKZdQs.', 'ROLE_USER'),
       (51, _binary '', 0, 'jj', '$2a$10$vD7mJ/994WQQynZ63Bpa3e0H5e0AiOA9B7UjcNSYK8PALOqhYHe/u', 'ROLE_USER'),
       (52, _binary '', 0, 'sdasd', '$2a$10$ImtGzKpoDyZ5.asTbHEMBeild0dim9x0yr3KmbmckxpO4AU4K9Pf6', 'ROLE_USER'),
       (53, _binary '', 0, 'dasdas', '$2a$10$8j9SSNq1/LgBcJDWLK5XUeB6eqqbYMzt1Jm6qRZxOQOe/AMktO6qK', 'ROLE_USER'),
       (54, _binary '', 0, 'ccZsdas', '$2a$10$JCFSw93yyKH1ahqmBsMvXeGWfqnx2OHG2HSxJNs/tDEopKh/26xuy', 'ROLE_USER'),
       (55, _binary '', 0, 'asdasd', '$2a$10$bfn.SQ6FCRBIB07ib4EipOsgXHyqJyfq7rccquOtP8v9jKYVnlxIG', 'ROLE_USER'),
       (56, _binary '', 0, 'dadas', '$2a$10$sKTY7fhcakEsY4i9sDghou04CBri5NynXTGCpvVYBakHOYa6hmpZy', 'ROLE_USER'),
       (189, _binary '', 0, 'Test', '$2a$10$UC0LhF54zdg9eVqGoSj6uOR2B0NDt/PgYP.7euCevY2VMj4vq7J5G', 'ROLE_USER'),
       (192, _binary '', 0, 'test2', '$2a$10$PtGvEFSEBm8fH2r.M06OwOvsdihczd7EwaeKDevn.lklCTmAV8YTi', 'ROLE_USER'),
       (195, _binary '', 0, '', '$2a$10$.tTgQo3sEBqj0NgulR09uuSgTmyCvJ0.ombEl6MCZF5kYjyCi.Dxy', 'ROLE_USER'),
       (206, _binary '', 0, 'dasdasdasda', '$2a$10$2id02m2xVAYxIXpmp1uLNeA2hhaTMpkQmqJUjEh.rar/8eOcl8aWa',
        'ROLE_USER'),
       (226, _binary '', 0, 'вфывфыв', '$2a$10$cWAjE65F08Rhff.JnCvmju92fUN/u6Njo0fF1erUNwIvQiADt/5LC', 'ROLE_USER'),
       (232, _binary '', 0, 'Hello', '$2a$10$yHgL2kVhvmA80FjmbE0V1OSt683jntf2.3j0mIJLmGwOAKsW1fmvi', 'ROLE_USER'),
       (233, _binary '', 0, 'Test312', '$2a$10$kjlax8tEmqpD6maP8glSJ.nHXbbhwnUsAEvjBNzTiD9.JIdL4.g7i', 'ROLE_USER');
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

-- Dump completed on 2020-02-11 15:13:10
