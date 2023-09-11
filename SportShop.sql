 UNLOCK TABLES;
 drop database  SportShop;
create database SportShop;
  use SportShop;
  
DROP TABLE IF EXISTS user;
CREATE TABLE user (
	userNo int Primary Key,
	userName varchar(32) DEFAULT NULL,
    userEmail varchar(32) unique DEFAULT NULL,
    userPassword varchar(32) DEFAULT NULL,
    userAddress varchar(32) DEFAULT NULL, 
	userPermission varchar(32) DEFAULT NULL
 );
 
LOCK TABLES user WRITE;
INSERT INTO user VALUES (1,'basem','basem99@gmail.com','qwerty','Nablus','Admin'),
(2,'Khaled','khaled87@gmail.com','asdfgh','Tulkarem','Customer'),
(3,'ahmad','ahmad56@gmail.com','asdfgh','Tulkarem','Customer'),
(4,'admin','admin','admin','Ramallah & Al-Bireh','Admin'),
(5,'ahmad','ahmad','123','Nablus','Customer');
UNLOCK TABLES;
 select * from user;

-- -------------------------------------------------------
-- -------------------------------------------------------
DROP TABLE IF EXISTS phone;
Create Table phone(
	userPhone varchar(32) Not Null,
    userNo int Not Null,
    primary key(userPhone, userNo),
    Foreign Key (userNo) REFERENCES user(userNo) ON DELETE CASCADE ON UPDATE CASCADE
);

LOCK TABLES phone WRITE;
INSERT INTO phone VALUES ('0599123456',1),('0597135312',2), ('0596324234',1),('1700800900',4),('05912324',3),('0599',5);
UNLOCK TABLES;
 
-- -------------------------------------------------------
-- -------------------------------------------------------
DROP TABLE IF EXISTS warehouse;
CREATE TABLE  warehouse(
	warehouseID int Not Null Primary Key,
	warehouseName varchar(32) DEFAULT NULL,
    warehouselocation varchar(32) DEFAULT NULL
);

LOCK TABLES warehouse WRITE;
INSERT INTO warehouse VALUES (1,'K','Ramallah'),(2,'M','Tulkarem');
UNLOCK TABLES;

DROP TABLE IF EXISTS item;
CREATE TABLE  item(
	itemID int Not Null Primary Key,
	itemName varchar(32) DEFAULT NULL,
    brand varchar(32) DEFAULT NULL,
    color varchar(32) DEFAULT NULL, 
	category varchar(32) DEFAULT NULL,
    size real DEFAULT 30,
    price real default null,
    quantity int default 1,
    warehouseID int not null,
	Foreign Key (warehouseID) REFERENCES warehouse(warehouseID) ON DELETE CASCADE ON UPDATE CASCADE

) ;

LOCK TABLES item WRITE;
INSERT INTO item VALUES (1,'nike sport NK','Nike','Red', 'Boot',35,200,200,1),
(2,'K20','Nike','Blue', 'Short',35,150,5,2);

INSERT INTO item VALUES (4,'ttt','Nike','Red', 'T-Shirt',35,300,200,1),
(5,'nikell','Nike','Red', 'Gloves',35,50,200,1),(6,'ttt','Nike','Red', 'Pant',35,100,200,1)
,(7,'ttt','Nike','Red', 'Bag',35,20,200,1);

INSERT INTO item VALUES (8,'ttt','Nike','Red', 'Pant',35,100,200,1);
UNLOCK TABLES;

select * from item;

DROP TABLE IF EXISTS cart;
CREATE TABLE  cart(
	userNo int Not Null Primary Key,
    noOfItems int DEFAULT 0,
    totalPrice real DEFAULT 0,
	Foreign Key (userNo) REFERENCES user(userNo) ON DELETE CASCADE ON UPDATE CASCADE

) ;

LOCK TABLES cart WRITE;
INSERT INTO cart VALUES (1,0,0),
(2,0,0),
(3,0,0),
(4,0,0),
(5,0,0);
UNLOCK TABLES;

DROP TABLE IF EXISTS deliveryCompany;
CREATE TABLE deliveryCompany(
    deliveryID int Not Null Primary Key,
    deliveryName varchar(32) DEFAULT null,
    location varchar(32) DEFAULT Null,
    noOfVehicle int DEFAULT 0
    	
) ;

Insert into deliveryCompany values(1,'aramix','jenin',5);


DROP TABLE IF EXISTS orders;
CREATE TABLE orders(
	orderID int Not Null Primary Key,
	confirmationDate DATETIME,
    totalPrice real DEFAULT 0,
    userNo int,
    deliveryID int,
	Foreign Key (userNo) REFERENCES cart(userNo) ON DELETE CASCADE ON UPDATE CASCADE,
	Foreign Key (deliveryID) REFERENCES deliveryCompany(deliveryID) ON DELETE CASCADE ON UPDATE CASCADE
) ;

-- Insert into orders values(2,'2022-03-22 10:30:33',20,1,1);


DROP TABLE IF EXISTS addToCart;
CREATE TABLE addToCart(
    userNo int Not NUll,
	itemID int Not Null,
    quantity int default 1,
    itemPrice real DEFAULT 0,
    finalPrice real DEFAULT 0,
    Primary Key(userNo, itemID),
	Foreign Key (userNo) REFERENCES cart(userNo) ON DELETE CASCADE ON UPDATE CASCADE,
	Foreign Key (itemID) REFERENCES item(itemID) ON DELETE CASCADE ON UPDATE CASCADE
    
) ;

CREATE TABLE orderedItem(
	orderID int Not Null,
	itemID int Not Null,
    quantity int DEFAULT 0,
    itemPrice real DEFAULT 0,
    finalPrice real DEFAULT 0,
    Primary Key(orderID, itemID),
	Foreign Key (orderID) REFERENCES orders(orderID) ON DELETE CASCADE ON UPDATE CASCADE,
	Foreign Key (itemID) REFERENCES item(itemID)
    
);

SELECT o1.orderID,o1.itemID,o1.quantity,o1.itemPrice,o1.finalPrice 
					FROM OrderedItem o1, Orders o2, Cart c 
					where o1.orderID = o2.orderID  AND o2.userNo = c.userNo 
					AND c.userNo= 5;
select * from addToCart;
select * from item;
select * from cart;
select * from warehouse;
select * from deliveryCompany;
select * from orders;
select * from orderedItem;



