create table contact (
	id int identity not null, 
	date_of_birth date not null, 
	first_name varchar(255) not null, 
	gender int, 
	last_name varchar(255) not null, 
	title varchar(255), 
	constraint pk_contact primary key (id)
);

create table address (
	id int identity not null, 
	city varchar(255) not null, 
	state varchar(255) not null, 
	street varchar(255) not null, 
	street_number varchar(255) not null, 
	type int not null, unit varchar(255), 
	zip_code varchar(255) not null, 
	contact_id int, 
	constraint pk_address primary key (id),
	constraint fk_address_contact foreign key (contact_id) references contact
);

create table communication (
	id int identity not null, 
	preferred bit not null, 
	type int not null, 
	value varchar(255) not null, 
	contact_id int not null, 
	constraint pk_communication primary key (id),
	constraint fk_communication_contact foreign key (contact_id) references contact
);
