DROP DATABASE ZooCity;

CREATE DATABASE ZooCity;

USE ZooCity;

CREATE TABLE Admin(
	AdminId VARCHAR(6) PRIMARY KEY,
	AdminName VARCHAR(30),
	Password VARCHAR(10)
);

CREATE TABLE Ticket(
	TicketNo VARCHAR(6) PRIMARY KEY,
	TicketType VARCHAR(30),
	Price DOUBLE,
	Date DATE,
	AdminId VARCHAR(6),
	CONSTRAINT FOREIGN KEY (AdminId) REFERENCES Admin(AdminId) on Delete Cascade on Update Cascade
);

CREATE TABLE Schedule(
	ScheduleId VARCHAR(6) PRIMARY KEY,
	Hours INT,
	Date DATE
);

CREATE TABLE Employee(
	EmpId VARCHAR(6)PRIMARY KEY,
	EmpName VARCHAR(25),
	Address VARCHAR(30),
	Contact INT,
	Category VARCHAR(15),
	ScheduleId VARCHAR(6),
	AdminId VARCHAR(6),
	CONSTRAINT FOREIGN KEY (AdminId) REFERENCES Admin(AdminId) on Delete Cascade on Update Cascade,
	CONSTRAINT FOREIGN KEY (ScheduleId) REFERENCES Schedule(ScheduleId) on Delete Cascade on Update Cascade
);

CREATE TABLE Salary(
	SalaryId VARCHAR(6) PRIMARY KEY,
	EmpId VARCHAR(6),
	Payment INT,
	CONSTRAINT FOREIGN KEY (EmpId) REFERENCES Employee(EmpId) on Delete Cascade on Update Cascade
);

CREATE TABLE Slip(
	SlipId VARCHAR(6) PRIMARY KEY,
	BankName VARCHAR(20),
	Date DATE,
	Time TIME,
	SalaryId VARCHAR(6),
	CONSTRAINT FOREIGN KEY (SalaryId) REFERENCES Salary(SalaryId) on Delete Cascade on Update Cascade
);

CREATE TABLE Cages(
	CageId VARCHAR(6) PRIMARY KEY,
	Type VARCHAR(30),
	NumOfAnimals INT
);

CREATE TABLE CageControl(
	EmpId VARCHAR(6) ,
	CageId VARCHAR(6),
	Date DATE,
	Time TIME,
	Status VARCHAR (30),
	CONSTRAINT FOREIGN KEY (EmpId) REFERENCES Employee(EmpId) on Delete Cascade on Update Cascade,
	CONSTRAINT FOREIGN KEY (CageId) REFERENCES Cages(CageId) on Delete Cascade on Update Cascade
);

CREATE TABLE Animals(
	AnimalTg VARCHAR(20) PRIMARY KEY,
	Category VARCHAR(15),
	AnimalType VARCHAR(35),
	CageId VARCHAR(6),
	AdminId VARCHAR(6),
	CONSTRAINT FOREIGN KEY (AdminId) REFERENCES Admin(AdminId) on Delete Cascade on Update Cascade,
	CONSTRAINT FOREIGN KEY (CageId) REFERENCES Cages(CageId) on Delete Cascade on Update Cascade
);

CREATE TABLE Food(
	FoodId VARCHAR(6) PRIMARY KEY,
	Name VARCHAR(30),
	Price DOUBLE,
	Qty INT,
	StockStatus VARCHAR(10)
);

CREATE TABLE AnimalFoods(
	AnimalTg VARCHAR (20),
	FoodId VARCHAR (6),
	Date DATE,
	Time TIME,
	Qty INT,
	Status VARCHAR(10),
	CONSTRAINT FOREIGN KEY (ANimalTg) REFERENCES Animals(ANimalTg) on Delete Cascade on Update Cascade,
	CONSTRAINT FOREIGN KEY (FoodId) REFERENCES Food(FoodId) on Delete Cascade on Update Cascade
);

CREATE TABLE Medicine(
	MediId VARCHAR(6) PRIMARY KEY,
	Name VARCHAR(30),
	Price DOUBLE,
	Qty INT,
	StockStatus VARCHAR(10)
);

CREATE TABLE AnimalMedicine(
	ANimalTg VARCHAR(20),
	MediId VARCHAR(6),
	Date DATE,
	Time TIME,
	Qty INT,
	Status VARCHAR(10),
	CONSTRAINT FOREIGN KEY (ANimalTg) REFERENCES Animals(ANimalTg) on Delete Cascade on Update Cascade,
	CONSTRAINT FOREIGN KEY (MediId) REFERENCES Medicine(MediId) on Delete Cascade on Update Cascade
);

SHOW TABLES;

DESC Admin;
DESC AnimalMedicine;
DESC Animals;
DESC CageControl;
DESC Cages;
DESC Employee;
DESC Food;
DESC Medicine;
DESC Salary;
DESC Schedule;
DESC Slip;
DESC Ticket;

INSERT INTO Admin VALUES ('A001','Nishala','@1234');