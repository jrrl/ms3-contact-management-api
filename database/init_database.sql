CREATE TABLE Contact (
	id int IDENTITY(1,1),
	firstName nvarchar(255) NOT NULL,
	lastName nvarchar(255) NOT NULL,
	dob date NOT NULL, 
	gender char(1) NOT NULL, 
	title nvarchar(255) NULL,
	CONSTRAINT PK_Contact PRIMARY KEY (id), 
	CONSTRAINT valid_gender CHECK(gender IN ('M', 'F', 'N')),
	CONSTRAINT valid_dob CHECK (dob <= GETUTCDATE())
);

CREATE TABLE Address (
	id int IDENTITY(1,1), 
	contactId int NOT NULL,
	type nvarchar(50) NOT NULL, 
	number int NOT NULL, 
	street nvarchar(255) NOT NULL, 
	unit nvarchar(255) NULL, 
	city nvarchar(100) NOT NULL, 
	state char(2) NOT NULL, 
	zipcode nvarchar(20) NOT NULL, 
	CONSTRAINT PK_Address PRIMARY KEY (id), 
	CONSTRAINT FK_Contact FOREIGN KEY (contactId) REFERENCES Contact(id)
)

CREATE TABLE Communication (
	id int IDENTITY(1, 1), 
	contactId int NOT NULL,
	type nvarchar(20) NOT NULL, 
	value nvarchar(255) NOT NULL, 
	preferred bit NOT NULL DEFAULT 0, 
	CONSTRAINT PK_Communication PRIMARY KEY (id), 
	CONSTRAINT FK_Contact FOREIGN KEY (contactId) REFERENCES Contact(id)
)