

DROP TABLE IF EXISTS `Airport`;
CREATE TABLE `Airport` (
    `Code` char(3) NOT NULL ,
    `City` varchar(50) ,
    `State` char(2),
    PRIMARY KEY (`Code`)
);

DROP TABLE IF EXISTS `PlaneType`;
CREATE TABLE `PlaneType` (
    `MAKER` varchar(10) NOT NULL,
    `MODEL` varchar(15) NOT NULL ,
    `FlyingSpeed` int(3),
    `GroundSpeed` int(3),
    PRIMARY KEY (`Maker`,`Model`)
);

DROP TABLE IF EXISTS `Plane`;
CREATE TABLE `Plane` (
    `ID` int NOT NULL,
    `MAKER` varchar(10) ,
    `MODEL` varchar(15) ,
    `LastMaint` char(20),
    `LastMaintA` char(20),
    PRIMARY KEY (`ID`),
    FOREIGN KEY(`MAKER`,`MODEL`) REFERENCES `PlaneType` (`MAKER`,`MODEL`),
    FOREIGN KEY(`LastMaintA`) REFERENCES `Airport` (`Code`)
);

DROP TABLE IF EXISTS `PlaneSeats`;
CREATE TABLE `PlaneSeats` (
    `MAKER` varchar(10) NOT NULL,
    `MODEL` varchar(15) NOT NULL,
    `SeatType` char(1) NOT NULL,
    `NoOfSeats` int,
    PRIMARY KEY (`Maker`,`Model`,`SeatType`),
    FOREIGN KEY(`Maker`,`Model`) REFERENCES `PlaneType` (`Maker`,`Model`)
);

DROP TABLE IF EXISTS `Pilot`;
CREATE TABLE `Pilot` (
    `ID` int NOT NULL,
    `NAME` varchar(20),
    `DateHired` char(15),
    PRIMARY KEY (`ID`)
);

DROP TABLE IF EXISTS `Flight`;
CREATE TABLE `Flight` (
    `FLNO` int NOT NULL,
    `Meal` varchar(10),
    `Smoking` char(1),
    PRIMARY KEY (`FLNO`)
);

DROP TABLE IF EXISTS `FlightInstance`;
CREATE TABLE `FlightInstance` (
    `FLNO` int NOT NULL ,
    `FDATE` char(10) NOT NULL,
    PRIMARY KEY (`FLNO`,`FDate`),
    FOREIGN KEY (`FLNO`) REFERENCES `Flight`(`FLNO`)
);

DROP TABLE IF EXISTS `FlightLeg`;
CREATE TABLE `FlightLeg` (
    `FLNO` int NOT NULL ,
    `Seq` char(1) NOT NULL,
    `FromA` varchar(3) ,
    `ToA` varchar(3) ,
    `DeptTime` char(17) ,
    `ArrTime`char(17) ,
    `Plane` varchar(1),
    PRIMARY KEY (`FLNO`,`Seq`),
    FOREIGN KEY(`FromA`) REFERENCES `Airport` (`Code`),
    FOREIGN KEY(`ToA`) REFERENCES `Airport` (`Code`),
    FOREIGN KEY(`Plane`) REFERENCES `Plane` (`ID`)
);

DROP TABLE IF EXISTS `FlightLegInstance`;
CREATE TABLE `FlightLegInstance` (
    `Seq` char(1) NOT NULL ,
    `FLNO` int NOT NULL,
    `FDate` char(10) NOT NULL ,
    `ActDept` char(30),
    `ActArr` char(30) ,
    `Pilot` int(1),
    PRIMARY KEY (`Seq`,`FLNO`,`FDate`),
    FOREIGN KEY(`FLNO`,`Seq`) REFERENCES `FlightLeg` (`FLNO`,`Seq`),
    FOREIGN KEY(`FLNO`,`FDate`) REFERENCES `FlightInstance` (`FLNO`,`FDate`)
);

DROP TABLE IF EXISTS `Passenger`;
CREATE TABLE `Passenger` (
    `ID` int NOT NULL,
    `Name` char(20) ,
    `Phone` char(20),
    PRIMARY KEY (`ID`)
);

DROP TABLE IF EXISTS `Reservation`;
CREATE TABLE `Reservation`(
    `PassID` int(5) NOT NULL,
    `FLNO` int NOT NULL ,
    `FDate` char(10) NOT NULL,
    `FromA` varchar(3) ,
    `ToA` varchar(3) ,
    `SeatClass` varchar(2),
    `DateBooked` char(20),
    `DateCancelled` char(20),
    PRIMARY KEY (`PassID`, `FLNO`,`FDate`),
    FOREIGN KEY(`PassID`) REFERENCES `Passenger` (`ID`),
    FOREIGN KEY (`FLNO`,`FDate`) REFERENCES `FlightInstance` (`FLNO`,`Fdate`),
    FOREIGN KEY(`FromA`) REFERENCES `Airport`(`Code`),
    FOREIGN KEY (`ToA`) REFERENCES `Airport` (`Code`)
    );