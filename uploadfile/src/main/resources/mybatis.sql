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

-- 创建角色表
create table role_uf(
id number(12) primary key,
roleName varchar(20),
createDate date,
status int
);

-- 创建用户角色表
create table user_role_uf(
id number(12) primary key,
account varchar(20),
roleId number(12),
createDate date,
);

-- 创建TablePrimaryKey表格，该表格用来存储各个表的主键增长方式，其他增减新的数据时，都要访问该表获取自身的主键值
create table table_primary_key(
classReferenceName varchar(200) primary key ,
currentTableID varchar(12) not null,
strategy number(1) not null,
createDate date
);

-- 创建File表，用来保存文件信息
-- drop table file_uf;
create table file_uf(
id number(12) primary key,
fileName varchar(200),
storeName varchar(200),
fileType varchar(20),
fileSize varchar(10),
uploadName varchar(20),
uploadAccount varchar(20),
reviseName varchar(20),
reviseAccount varchar(20),
uploadDate date,
status int,
locatePath varchar(400),
downloadCount number
);

-- 创建FileDownload表，用来保存用户下载的文件
-- drop table download;
create table download(
id number(12) primary key,
account varchar(20) not null,
fileId number(12) not null,
downloadDate date
);