CREATE DATABASE `col`

CREATE TABLE `users` (
                         `US_ID` int(11) NOT NULL AUTO_INCREMENT,
                         `US_NAME` varchar(50) NOT NULL,
                         `US_SURNAME` varchar(50) NOT NULL,
                         `US_BIRTHDAY` date DEFAULT NULL,
                         `US_SEX` varchar(10) DEFAULT NULL,
                         `US_DOCUMENT_NUMBER` int(11) NOT NULL,
                         PRIMARY KEY (`US_ID`),
                         UNIQUE KEY `User_already_exists` (`US_DOCUMENT_NUMBER`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3

CREATE TABLE `candidatures` (
                                `CA_ID` int(11) NOT NULL AUTO_INCREMENT,
                                `US_ID` int(11) NOT NULL,
                                `CA_PROPOSAL` varchar(200) NOT NULL,
                                `CA_PARTY` varchar(20) NOT NULL,
                                PRIMARY KEY (`CA_ID`),
                                UNIQUE KEY `candidatures_un` (`US_ID`),
                                CONSTRAINT `CANDIDATURES_FK` FOREIGN KEY (`US_ID`) REFERENCES `users` (`US_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3

CREATE TABLE `votes` (
                         `VO_ID` int(11) NOT NULL AUTO_INCREMENT,
                         `US_ID` int(11) NOT NULL,
                         `CA_ID` int(11) NOT NULL,
                         `VO_COUNT` int(11) DEFAULT NULL,
                         PRIMARY KEY (`VO_ID`),
                         UNIQUE KEY `votes_un` (`US_ID`),
                         KEY `VOTES_FK_1` (`CA_ID`),
                         CONSTRAINT `VOTES_FK` FOREIGN KEY (`US_ID`) REFERENCES `users` (`US_ID`),
                         CONSTRAINT `VOTES_FK_1` FOREIGN KEY (`CA_ID`) REFERENCES `candidatures` (`CA_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3
