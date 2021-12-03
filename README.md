# CSE412-G15-Project

User Manual for the CSE 412 Group Project.

## Indtruoduction

This is a web-based application offers the service of searching book information, renting books, and sharing comments. This repository contains the full stack application and complete source codes. We also offer the database dump so you can build the local database with PostgreSQL. The main purpose of this project is to demonstrate our understanding of modern databases and our ability to apply them in practice after learning in CSE412 with Dr.Jia Zou during Fall 2021.

## Contents

 - [Prerequisites](#Prerequisites)
 - [Database Details](#Database-Details)
 - [User Manual](#User-Manual)
 - [Development Environment](#Development-Environment)

## Prerequisites

 - PostgreSQL
 - IDEA
 - HbuilderX
 - Java1.8
 - SpringBoot
 - Vue2
 - Node.js
 - npm

## Database Details
### Schema

For this project, we create the database on local server. We also provide the code and tutorial for users to build the database on their own local machine. The relational database model contains 4 entities. Because we need to implement some basic features of a system in a library, we need the four entities to interact with each other. They are Administrator, Reader, Book, and BookComment.

### ER Diagram

![Alt text](./img/ER.png)

### ER-to-Realational

- Administrator: (ID, Name, Gender, password, contactInfo, accountNumber)
- Reader: (ID, numOfBookCanBorrow, Name, Gender, password, contactInfo, accountNumber)
- updateBook: (ID, ISBN)
- Borrow: (ID, ISBN, BorrowTime, ReturnTime)
- Book: (ISBN, author, Title, publisher, type, numInStock)
- BookComment: (commentID, rate, content, postTime)
- Post: (commentID, ID)
- HasA: (commentID, ISBN)

In order to create the database system in PostgreSQL, we need to transform them to following SQL DDL:

#### Administrator Table

~~~~sql
CREATE TABLE Administrator (
ID INTEGER NOT NULL,
Name VARCHAR(20),
Gender CHAR(1),
password VARCHAR(20),
contactInfo VARCHAR(20),
accountNumber VARCHAR(20),
PRIMARY KEY (ID)
);
~~~~

#### Reader Table

~~~~sql
CREATE TABLE Reader (
ID INTEGER NOT NULL,
Name VARCHAR(20),
Gender CHAR(1),
password VARCHAR(20),
contactInfo VARCHAR(20),
accountNumber VARCHAR(20),
numOfBookCanBorrow INTEGER,
PRIMARY KEY (ID)
);
~~~~

#### Book Table

~~~~sql
CREATE TABLE Book(
ISBN CHAR(13),
author VARCHAR(30),
Title VARCHAR(100),
publisher VARCHAR(100),
bookType VARCHAR(100),
numInStock INTEGER,
PRIMARY KEY (ISBN)
);
~~~~

#### updateBook Table

~~~~sql
CREATE TABLE updateBook (
ID INTEGER NOT NULL,
ISBN CHAR(13),
PRIMARY KEY (ID, ISBN),
FOREIGN KEY (ID)
REFERENCES Administrator ON DELETE CASCADE,
FOREIGN KEY (ISBN)
REFERENCES Book ON DELETE CASCADE
);
~~~~

#### Borrow Table

~~~~sql
CREATE TABLE Borrow(
ID INTEGER NOT NULL,
ISBN CHAR(13),
BorrowTime DATE,
ReturnTime DATE,
PRIMARY KEY (ID, ISBN),
FOREIGN KEY(ID)
REFERENCES Reader ON DELETE CASCADE,
FOREIGN KEY (ISBN)
REFERENCES Book ON DELETE CASCADE
);
~~~~

#### bookComment Table

~~~~sql
CREATE TABLE bookComment(
commentID INTEGER NOT NULL,
rate INTEGER,
content VARCHAR(300),
postTime DATE,
PRIMARY KEY(commentID)
);
~~~~

#### Post Table

~~~~sql
CREATE TABLE Post(
commentID INTEGER NOT NULL,
ID INTEGER NOT NULL,
PRIMARY KEY (commentID),
FOREIGN KEY (commentID)
REFERENCES bookComment ON DELETE CASCADE,
FOREIGN KEY (ID)
REFERENCES Reader ON DELETE CASCADE
);
~~~~

#### HasA Table

~~~~sql
CREATE TABLE HasA(
commentID INTEGER NOT NULL,
ISBN CHAR(13),
PRIMARY KEY(commentID),
FOREIGN KEY(commentID)
REFERENCES bookComment ON DELETE CASCADE,
FOREIGN KEY(ISBN)
REFERENCES Book ON DELETE CASCADE
);
~~~~

## User Manual
### Functionality
#### Log-in & Sign-in
On this page, you will see two options, one is Log-in, another is Sign-in. You can sign-in as a user. After sign-in, you should be able to lg in with the information you just filled in. This will navigate you to the reader version interface, which doesn't have perssion to edit the information. If you log in as a manager, you should be able have the permission to edit the books and comments.

#### Reader & manager homepage
1. Reader
- At this page, you will see a list and a form of existing books. 
- You could see the information including ISBN number, author, title, publisher, book type, and number in stock.
- As a user, you can also borrow a book by clicking the borrow buttion at the most right side.
![Alt text](./img/rsearch.jpg)
2. Manager
![Alt text](./img/msearch.jpg)

#### Personal Information

![Alt text](./img/pinfo.jpg)

#### Book Return

![Alt text](./img/return.jpg)

## Development Environment
About our project, the tools that we used for development include: "IDEA", "HBuilder X", "java", "Node.js" and "npm".
For "IDEA", if you haven't install it yet, you can download from this link: https://www.jetbrains.com/idea/download/#section=windows and install to your computer
For "HBuilder X", if you haven't install it yet, you can download from this link: https://www.dcloud.io/hbuilderx.html
For "java", you can first check whether you install it by input the command: "java -version" in your terminal, if it show up the version, you don't need to install it again, else you can download and install from this link: https://www.oracle.com/java/technologies/downloads/#java8
For "Node.js", you can first check whether you install it by input the command: "node -v" in your terminal, if it show up the version, you don't need to install it again, else you can download and install from this link: https://nodejs.org/en/
For "npm", you can first check whether you install it by input the command: "npm -v" in your terminal, if it show up the version, you don't need to install it again, else you can download the latest version of npm, on the command line, run the following command: "npm install -g npm"
