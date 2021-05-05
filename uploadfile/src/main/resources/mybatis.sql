-- 表主键长度统一12字节

-- 创建UserUF类对应的表
create table user_uf(
id number(12) primary key,
account varchar(20) not null,
password varchar(30) not null,
name varchar(20) not null,
sex varchar(2),
telephone number(11),
email varchar(30) not null,
createDate date
);

-- 创建TablePrimaryKey表格，该表格用来存储各个表的主键增长方式，其他增减新的数据时，都要访问该表获取自身的主键值
create table table_primary_key(
classReferenceName varchar(200) primary key ,
currentTableID varchar(12) not null,
strategy number(1) not null,
createDate date
);



