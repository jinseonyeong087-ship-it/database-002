DROP TABLE account;
DROP TABLE item;
DROP TABLE service;
DROP TABLE customer;
DROP TABLE branch;

select * from account;
select * from item;
select * from service;
select * from customer;
select * from branch;

CREATE TABLE customer(
c_no		varchar(14) NOT NULL PRIMARY KEY,
c_name		varchar(20) NOT NULL,
c_addr		varchar(60),
c_phone 	varchar(16),
c_dist		CHAR(2)
);

select * from customer;
INSERT INTO customer values('701201-2255441', '강주현',		'서울시 중구',	 '02-3752-2232','00');
INSERT INTO customer values('691115-1223455', '김광철',		'서울시 중구',	 '02-3742-7742','00');
INSERT INTO customer values('790901-2358327', '김수연',		'경기도 구리시',   '031-834-4527','00');
INSERT INTO customer values('660419-1623112', '이민수',		'서울시 용산구',   '02-4436-6761','00');
INSERT INTO customer values('760615-1255447', '한수인',		'경기도 성남시',   '031-562-2265','00');
INSERT INTO customer values('220-82-52237',   '(주)한국전산',	'서울시 용산구',   '02-4436-2424','11');
INSERT INTO customer values('361-22-42687',   '(주)한국리서치',	'서울시 용산구',   '02-4436-8237','11');
INSERT INTO customer values('102-22-51094',   '(주)국제산업',	'서울시 중구',	  '02-3720-8782','11');
INSERT INTO customer values('363-52-45172',   '성남의원',	'경기도 성남시',   '031-563-2573','11');
INSERT INTO customer values('220-82-21094',   '(주)정보통신',	'서울시 중구',	  '02-3733-5567','11');


CREATE TABLE branch(
b_no		varchar(3) NOT NULL PRIMARY KEY,
b_name		varchar(20) NOT NULL,
b_addr		varchar(60),
b_phone 	varchar(16),
b_assets	DECIMAL(13));

INSERT INTO branch values('100','서울중구','서울시 중구',  '02-3727-4420','2150000000');
INSERT INTO branch values('200','서울용산','서울시 용산구','02-4437-4420','2980000000');
INSERT INTO branch values('300','경기구리','경기도 구리시','031-834-4420','850000000');
INSERT INTO branch values('400','경기성남','경기도 성남시','031-562-4420','1350000000');

CREATE TABLE service(
serv_c_no		varchar(14) NOT NULL,
serv_b_no		varchar(3) NOT NULL,
CONSTRAINT service_pk PRIMARY KEY(serv_c_no, serv_b_no),
CONSTRAINT serv_cu_no_fk FOREIGN KEY(serv_c_no) REFERENCES customer(c_no),
CONSTRAINT serv_br_no_fk FOREIGN KEY(serv_b_no) REFERENCES branch(b_no));

INSERT INTO service values('701201-2255441', '100');
INSERT INTO service values('691115-1223455', '200');
INSERT INTO service values('790901-2358327', '300');
INSERT INTO service values('660419-1623112', '400');
INSERT INTO service values('760615-1255447', '100');
INSERT INTO service values('220-82-52237',   '200');
INSERT INTO service values('361-22-42687',   '300');
INSERT INTO service values('102-22-51094',   '400');
INSERT INTO service values('363-52-45172',   '100');
INSERT INTO service values('220-82-21094',   '200');


CREATE TABLE item(
item_name    varchar(20),
item_limit   DECIMAL(13),
item_dist    CHAR(2),
item_c_dist  CHAR(2),
term1   DECIMAL(3),
rate1   DECIMAL(5,3),
term2   DECIMAL(3),
rate2   DECIMAL(5,3),
term3   DECIMAL(3),
rate3   DECIMAL(5,3),
CONSTRAINT item_name_pk PRIMARY KEY(item_name));

INSERT INTO item values('자유저축', null, 'A0', '00',	12, 2,	null,	null,	null, null);
INSERT INTO item values('정기예금', null, 'A0', '00',	12, 6.5,	36,     7.5,	60,  8);
INSERT INTO item values('장기주택마련', 200000000, 'A0', '00',	12, 8,	null,	null,	null, null);
INSERT INTO item values('기업신탁', null,  'A0',  '11',	12,	1,	null,	null,	null, null);
INSERT INTO item values('기업정기적금', null,  'A0', '11', 12,	5.5,	36,	6.5,	60,	7.5);
INSERT INTO item values('예금담보', 30000000, 'L1', '00',	12,	12,	36,	13,	60,	13.5);
INSERT INTO item values('단골고객신용', 30000000, 'L1', '00',	12,	12,	36,	13,	60,	13.5);
INSERT INTO item values('부동산담보',	100000000, 'L1', '00',	12,	10,	36,	11.5,	60,	12.5);
INSERT INTO item values('종합통장',	300000000, 'L1', '11',	12,	12.5,	24,	13,	36,	13.5);


CREATE TABLE account(
a_no		CHAR(8),
a_serial_no	DECIMAL(5),
a_date		DATE,
a_amount 	DECIMAL(13),
a_open_date     DATE not null,
a_total_amount  DECIMAL(13),
a_term		DECIMAL(3),
a_item_dist	varchar(20) not null,
a_item_name	varchar(20) not null,
a_b_no		varchar(3) not null,
a_c_no		varchar(14) not null,
CONSTRAINT ac_no_pk PRIMARY KEY(a_no, a_serial_no),
CONSTRAINT ac_it_fk1 FOREIGN KEY(a_item_name) REFERENCES item(item_name),
CONSTRAINT ac_br_fk FOREIGN KEY(a_b_no) REFERENCES branch(b_no),
CONSTRAINT ac_it_fk2 FOREIGN KEY(a_c_no) REFERENCES customer(c_no));

INSERT INTO account VALUES('AP100001', 1,  '2007-10-1', 100000,  '2007-10-1', null, null, 'A0', '자유저축', '100','701201-2255441');
INSERT INTO account VALUES('AP100001', 2,  '2007-11-19', 800000,  '2007-10-1', null, null, 'A0', '자유저축','100', '701201-2255441');
INSERT INTO account VALUES('AP100001', 3,  '2007-11-21', -700000,  '2007-10-1', null, null, 'A0', '자유저축','100', '701201-2255441');
INSERT INTO account VALUES('AP100001', 4,  '2007-12-1', 200000,  '2007-10-1', null, null, 'A0', '자유저축', '100', '701201-2255441');
INSERT INTO account VALUES('AE100001', 1,  '2006-12-10', 15000000,  '2006-12-10', null, null, 'A0', '기업신탁', '100', '102-22-51094');
INSERT INTO account VALUES('AE100001', 2,  '2007-1-25', 35000000,	 '2006-12-10', null, null, 'A0', '기업신탁', '100', '102-22-51094');
INSERT INTO account VALUES('AE100001', 3,  '2007-10-14', -45000000,  '2006-12-10', null, null, 'A0', '기업신탁', '100', '102-22-51094');
INSERT INTO account VALUES('AE100001', 4,  '2007-12-4', -160000000,  '2006-12-10', null, null, 'A0', '기업신탁', '100', '102-22-51094');
INSERT INTO account VALUES('AP100002', 1,  '2007-11-20', 250000,  '2007-11-20', 
null, 30, 'A0', '장기주택마련','100', '691115-1223455');
INSERT INTO account VALUES('AP100002', 2,  '2007-12-20', 250000,  '2007-11-20',
null, 30, 'A0', '장기주택마련','100', '691115-1223455');
INSERT INTO account VALUES('AP200001', 1,  '2006-11-4', 20000000,	 '2006-11-4', null, null, 'A0', 
'자유저축', '200', '691115-1223455');
INSERT INTO account VALUES('AP200001', 2,  '2007-11-20', -800000,	 '2006-11-4', null, null, 'A0', 
'자유저축','200', '691115-1223455');
INSERT INTO account VALUES('AP200001', 3,  '2007-11-24', -800000,	 '2006-11-4', null, null, 'A0', '자유저축', '200', '691115-1223455');
INSERT INTO account VALUES('AP200001', 4,  '2007-12-20', 1000000,	 '2006-11-4', null, null, 'A0', '자유저축', '200', '691115-1223455');
INSERT INTO account VALUES('AP200001', 5,  '2007-12-23', -800000,	 '2006-11-4', null, null, 'A0', '자유저축', '200', '691115-1223455');
INSERT INTO account VALUES('AP200002', 1,  '2006-12-15', 800000,  '2006-12-15', null, 24, 'A0', '정기예금',	'200', '660419-1623112');
INSERT INTO account VALUES('AP200002', 2,  '2007-1-15', 800000,  '2006-12-15', null, 24, 'A0', '정기예금',	'200', '660419-1623112');
INSERT INTO account VALUES('AP200002', 3,  '2007-2-10', 800000,  '2006-12-15', null, 24, 'A0', '정기예금',	'200', '660419-1623112');
INSERT INTO account VALUES('AE200001', 1,  '2006-10-5', 15000000,  '2006-10-5', null, null, 'A0', '기업신탁', '200', '220-82-52237');
INSERT INTO account VALUES('AE200002', 1,  '2007-11-1', 35000000,  '2007-11-1', null, null, 'A0', '기업신탁', '200', '361-22-42687');
INSERT INTO account VALUES('AE200002',	2,  '2007-12-2', 35000000,  '2007-11-1', null, null, 'A0', '기업신탁', '200', '361-22-42687');
INSERT INTO account VALUES('AE200003', 1,  '2006-12-5', 20000000,  '2006-12-5', null, 12,  'A0', 
'기업정기적금','200', '220-82-52237');
INSERT INTO account VALUES('AE200003', 2,  '2007-1-5', 20000000,  '2006-12-5', null, 12,  'A0', 
'기업정기적금','200', '220-82-52237');
INSERT INTO account VALUES('AP300001', 1,  '2006-1-20', 1000,  '2006-1-20', null, null, 'A0', '자유저축', '300', '790901-2358327');
INSERT INTO account VALUES('AP300001', 2,  '2007-1-20', 500,  '2006-1-20', null, null, 'A0', '자유저축', '300', '790901-2358327');
INSERT INTO account VALUES('AP300001', 3,  '2007-2-23', 35000,  '2006-1-20', null, null, 'A0', '자유저축', '300', '790901-2358327');
INSERT INTO account VALUES('AP300001',	4,  '2007-10-17', 55000,  '2006-1-20', null, null, 'A0', '자유저축', '300', '790901-2358327');
INSERT INTO account VALUES('AP300001',	5,  '2007-11-20', 65000,  '2006-1-20', null, null, 'A0', '자유저축', '300', '790901-2358327');
INSERT INTO account VALUES('AP300002',	1,  '2005-1-4', 150000,  '2005-1-4', null, null, 'A0', '자유저축', '300', '790901-2358327');
INSERT INTO account VALUES('AE400001', 1,  '2006-11-15', 20000000,	 '2006-11-15', null, null, 'A0', '기업신탁', '400', '363-52-45172');
INSERT INTO account VALUES('AE400001', 2,  '2006-12-15', 20000000,	 '2006-11-15', null, null, 'A0', '기업신탁', '400', '363-52-45172');
INSERT INTO account VALUES('AE400001', 3,  '2007-1-15', 20000000,	 '2006-11-15', null, null, 'A0', '기업신탁', '400', '363-52-45172');
INSERT INTO account VALUES('LP100001', 1, null, null,  '2005-02-11', 20000000, 36, 'L1', '예금담보', '100','701201-2255441');
INSERT INTO account VALUES('LP100001', 2,  '2007-02-11', 2000000,  '2005-02-11',
20000000, 36, 'L1', '예금담보', '100', '701201-2255441');
INSERT INTO account VALUES('LP400003',	1,	null, null,	 '2006-06-04',8000000,	10,	'L1', '예금담보',	'400', '760615-1255447');
INSERT INTO account VALUES('LP400003', 2,  '2007-01-04', 500000,	 '2006-06-04',8000000,	10, 'L1', '예금담보',	'400', '760615-1255447');
INSERT INTO account VALUES('LP100002',	1,	null, null,	 '2005-12-08', 27000000,	20, 'L1', '예금담보',	'300', 
'691115-1223455');
INSERT INTO account VALUES('LP100002',	2,	 '2006-12-08', 10000000,	 '2005-12-08', 27000000,	20, 'L1', 
'예금담보', '300', '691115-1223455');
INSERT INTO account VALUES('LE100001',	1,	null, null,	 '2006-02-08', 200000000,	24, 'L1', '종합통장',	'100', 
'102-22-51094');
INSERT INTO account VALUES('LE100001',	2,	 '2007-02-08', 10000000,  '2006-02-08', 200000000,	24,	'L1', '종합통장', '100', '102-22-51094');
INSERT INTO account VALUES('LE100002',	1,	null, null,  '2007-07-02', 150000000,	20, 'L1', '종합통장',	'100', 
'220-82-21094');
INSERT INTO account VALUES('LE100002',	2,	 '2007-12-02', 100000000,	 '2007-07-02', 150000000,	20, 'L1', 
'종합통장', '100', '220-82-21094');
INSERT INTO account VALUES('LE200001',	1,	null, null,	 '2007-10-05', 300000000,	36, 'L1', '종합통장',	'200', 
'220-82-52237');
INSERT INTO account VALUES('LE200001',	2,	 '2007-12-15', 100000000,	 '2007-10-05', 300000000,	36,	'L1', '종합통장',	'200', '220-82-52237');
INSERT INTO account VALUES('LE200002',	1,	null, null,	 '2006-02-18', 250000000,	30, 'L1', '종합통장',	'200', '361-22-42687');
INSERT INTO account VALUES('LE200002',	2,	 '2007-08-18', 150000000,	 '2006-02-18', 250000000,	30,	'L1', '종합통장',	'200', '361-22-42687');
INSERT INTO account VALUES('LP300001',	1,	null, null,	 '2006-11-19', 100000000,	12, 'L1', '종합통장',	'300', 
'790901-2358327');
INSERT INTO account VALUES('LP300001',	2,	 '2007-02-18', 5000000,	 '2006-11-19', 100000000,	12,	'L1', '종합통장',	'300', '790901-2358327');
INSERT INTO account VALUES('LP400002',	1, null, null,  '2007-01-19', 12000000,	12, 'L1', '종합통장', '400', '660419-1623112');
INSERT INTO account VALUES('LP400002',	2,	 '2007-11-19', 5000000,	 '2007-01-19', 12000000,	12, 'L1', 
'종합통장', '400', '660419-1623112');








