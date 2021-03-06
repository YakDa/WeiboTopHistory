DROP DATABASE IF EXISTS weibotopdb;
CREATE DATABASE weibotopdb;

USE weibotopdb;
CREATE TABLE weibotop_table(
id VARCHAR(16) NOT NULL,
topic VARCHAR(150) NOT NULL,
content VARCHAR(300),
category VARCHAR(100),
highestrank INT UNSIGNED NOT NULL,
hotpoints INT UNSIGNED NOT NULL,
firsttime DATETIME NOT NULL,
lasttime DATETIME NOT NULL,
duration INT UNSIGNED NOT NULL,
PRIMARY KEY (id),
UNIQUE KEY (topic)
)
CHARACTER SET 'utf8mb4';
CREATE TABLE email_table(
email_id VARCHAR(100) NOT NULL,
PRIMARY KEY (email_id)
)
CHARACTER SET 'utf8mb4';