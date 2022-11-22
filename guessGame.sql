DROP DATABASE IF EXISTS guessGame;
CREATE DATABASE guessGame;
USE guessGame;

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


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE Games(
	id int PRIMARY KEY AUTO_INCREMENT,
    answer varchar(25) NOT NULL,
	isFinished BOOLEAN NOT NULL DEFAULT 0
);
/*!40101 SET character_set_client = @saved_cs_client */;

CREATE TABLE Rounds (
	id int PRIMARY KEY AUTO_INCREMENT,
    gameID int,
    Timestamp timeStamp,
    guess varchar(10),
    guessResult VarcHAR(8)
);

ALTER TABLE Rounds
	ADD CONSTRAINT fk_round_game
		FOREIGN KEY (gameID)
		REFERENCES Games(id);


DROP DATABASE IF EXISTS guessGame_tests;
CREATE DATABASE guessGame_tests;
USE guessGame_tests;

CREATE TABLE Games(
	id int PRIMARY KEY AUTO_INCREMENT,
    answer varchar(25) NOT NULL,
	isFinished BOOLEAN NOT NULL DEFAULT 0
);

CREATE TABLE Rounds(
	id int PRIMARY KEY AUTO_INCREMENT,
    gameID int,
    Timestamp timeStamp,
    guess varchar(10),
    guessResult VarcHAR(8)
);

ALTER TABLE Rounds
	ADD CONSTRAINT fk_round_game
		FOREIGN KEY (gameID)
		REFERENCES Games(id);
        
drop table rounds;
drop table games;
LOCK TABLES Games WRITE;
/*!40000 ALTER TABLE Games DISABLE KEYS */;
INSERT INTO Games (id,answer,isFinished) VALUES (1,'3290',0),(2,'9807',1),(3,'1290',1),(4,'4352',1),(5,'9876',1),(6,'1254',0),(7,'2689',0),(8,'5087',0),(9,'2383',0),(10,'6835',0);
/*!40000 ALTER TABLE Games ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;


LOCK TABLES Rounds WRITE;
/*!40000 ALTER TABLE Rounds DISABLE KEYS */;
INSERT INTO Rounds VALUES (1,1,'2021-08-10 13:20:11','1254','e:4:p:0'),(2,2,'2021-04-11 08:47:31','5181','e:0:p:0'),(3,3,'2021-05-15 04:36:59','9876','e:4:p:0'),(4,4,'2021-01-31 10:03:01','1254','e:4:p:0'),(5,5,'2021-10-01 17:55:02','1259','e:1:p:1'),(6,6,'2021-08-04 10:20:49','4352','e:4:p:0');
/*!40000 ALTER TABLE Rounds ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
