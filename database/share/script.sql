DROP DATABASE IF EXISTS helpdesk;
CREATE DATABASE helpdesk;
USE helpdesk;


CREATE TABLE departments(
	value varchar(50) NOT NULL UNIQUE,
	PRIMARY KEY(value));
	
INSERT INTO departments(value) 
	VALUES('IT'),
			('HR'),
			('Marketing'),
			('Production'),
			('Maintenance');

CREATE TABLE roles(
	value varchar(50) NOT NULL UNIQUE,
	PRIMARY KEY(value));
	
INSERT INTO roles(value) 
	VALUES('User'),
			('Admin'),
			('Owner');

CREATE TABLE users(
	ID int NOT NULL AUTO_INCREMENT UNIQUE,
	email varchar(150) NOT NULL UNIQUE,
	department varchar(50) NOT NULL,
	role varchar(50) NOT NULL,
	password varchar(255),
	PRIMARY KEY(ID),
	FOREIGN KEY (department) REFERENCES departments(value),
	FOREIGN KEY (role) REFERENCES roles(value));

INSERT INTO users(email, department, role, password)
	VALUES('radu@helpdesk.com', 'IT', 'Owner', '12345'),
		('dorel@helpdesk.com', 'IT', 'User', '12345'),
		('test@helpdesk.com', 'IT', 'User', '12345');

CREATE TABLE status(
	value varchar(50) NOT NULL UNIQUE,
	PRIMARY KEY(value));
	
INSERT INTO status(value)
	VALUES('Open'),
			('Pending'),
			('Solved'),
			('Unsolved');

CREATE TABLE tickets(
	ID int NOT NULL AUTO_INCREMENT UNIQUE,
	issue varchar(255),
	opened_by int NOT NULL,
	closed_by int,
	assigned_to int,
	status varchar(50) DEFAULT 'Open',
	PRIMARY KEY (ID),
	FOREIGN KEY (opened_by) REFERENCES users (ID),
	FOREIGN KEY (closed_by) REFERENCES users (ID),
	FOREIGN KEY (assigned_to) REFERENCES users (ID),
	FOREIGN KEY (status) REFERENCES status (value));
	
INSERT INTO tickets (issue, opened_by)
	VALUES('Apa e prea rece', 1),
			('Nu mai porneste calculatorul', 2);
			
INSERT INTO tickets (status, issue, opened_by, assigned_to)
	VALUES('Pending', 'S-a rupt coclenderul', 2, 3);
	
INSERT INTO tickets (status, issue, opened_by, closed_by)
	VALUES('Solved', 'A cazut jigleru-n baie', 2, 1),
			('Unsolved', 'Am o problemaSi problema nu-i mica', 2,1);
