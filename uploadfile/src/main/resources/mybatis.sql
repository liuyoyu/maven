-- 表主键长度统一12字节

-- 创建UserUF类对应的表
drop table user_uf;
create table user_uf(
id number(12) primary key,
account varchar(20) not null,
password varchar(30) not null,
name varchar(20) not null,
sex varchar(4),
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
insert into role_uf(id, roleName, createDate, status) values(1, '系统用户', null, 0);
insert into role_uf(id, roleName, createDate, status) values(2, '后台用户', null, 0);
insert into role_uf(id, roleName, createDate, status) values(3, '普通用户', null, 0);

-- 创建用户角色表
create table user_role_uf(
id number(12) primary key,
account varchar(20),
roleId number(12),
createDate date
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

-- 创建菜单表
drop table menu;
create table menu(
id number(12) primary key,
name varchar(100),
url varchar(200),
parentId number(12),
createDate date,
seq numeric (3, 1),
status int
);

-- 创建email表
drop table email_store;
create table email_store(
id number(12) primary key,
account varchar(20),
froEmailAddr varchar(20), -- 发送邮箱
toEmailAddr varchar(20),  -- 接收邮箱
contextHtml varchar(200),
context varchar(20),
createDate date
);