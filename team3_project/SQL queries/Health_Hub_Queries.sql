use healthhub;

-- create table wishlist(
-- username varchar(40),
-- pname varchar(40),
-- pid varchar(40),
-- ptype varchar(40),
-- paddress varchar(40));

-- create table bookinglist(
-- fullname varchar(40),
-- date varchar(10),
-- time varchar(10),
-- pname varchar(40),
-- pid varchar(40));

-- create table latlonlist(
-- latitude varchar(20),
-- longitude varchar(20),
-- zipcode varchar(5),
-- primary key(zipcode)); 

-- create table providerslist(
-- id varchar(40),
-- name varchar(40),
-- staddress varchar(50),
-- city varchar(30),
-- state varchar(10),
-- zipcode varchar(5),
-- speciality varchar(40),
-- image varchar(40),
-- experience varchar(40),
-- fees double,
-- type varchar(30),
-- primary key(id)
-- );

-- CREATE TABLE registration (
-- username varchar(40) DEFAULT NULL,
-- password varchar(40) DEFAULT NULL,
-- repassword varchar(40) DEFAULT NULL,
-- firstname varchar(40) DEFAULT NULL,
-- lastname varchar(40) DEFAULT NULL,
-- email varchar(40) DEFAULT NULL,
-- usertype varchar(40) DEFAULT NULL
-- );

-- insert into doctorslist values ("vaish", "vaish", "2901 S king dr, 1301, Chicago, IL, US, 60616", "General Physician", "doct1.png", "16", 150.00);
-- insert into doctorslist values ("ajith", "ajith", "3001 S king dr, 1301, Chicago, IL, US, 60612", "General Surgeon", "doct2.png", "20", 180.00);
-- insert into doctorslist values ("hosp3", "Ann and Robert H. Lurie Children’s Hospital of Chicago", "225 E Chicago Ave, Chicago, IL 60611", "All Specialities", "doct3.png", "22", 180.00, "Hospital");

-- insert into latlonlist values ("41.840340", "-87.613701", "60616");

-- alter table providerslist add latitude varchar(20);
-- alter table providerslist add longitude varchar(20);

SET SQL_SAFE_UPDATES = 0;

delete from wishlist;
-- alter table wishlist add column paddress varchar(100);

-- alter table bookinglist add cid varchar(20);
-- alter table bookinglist add crn varchar(20);
-- alter table bookinglist add ptype varchar(40);
-- delete from providerslist;
-- update providerslist set latitude='41.842641' where id='doct2';
-- update providerslist set longitude='-87.618752' where id='doct2';

-- update bookinglist set cid='cust1' where cid is null;
-- update bookinglist set crn='341' where time='11:00';
-- update bookinglist set ptype='Doctor' where ptype is null;

-- select * from providerslist where zipcode='60616';

-- select * from latlonlist;

-- select * from bookinglist;

select * from wishlist;