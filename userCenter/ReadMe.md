# 创建表
-- 建表
CREATE TABLE nbcb_user (
id INT,
NAME VARCHAR (4),
phone VARCHAR(32),
password VARCHAR(32),
sex CHAR (1) NOT NULL  DEFAULT '男',
birthday DATE,
entry_date DATE,
salary DOUBLE,
`resume` TEXT
) CHARSET utf8 ENGINE INNODB

alter table nbcb_user add primary key(id);
alter table nbcb_user modify id int auto_increment;

CREATE TABLE nbcb_otp (
id INT,
phone VARCHAR(32),
otpCode VARCHAR(32),
updateTime VARCHAR(32)
) CHARSET utf8 ENGINE INNODB;

alter table nbcb_otp add primary key(id);
alter table nbcb_otp modify id int auto_increment;



