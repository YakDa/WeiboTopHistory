ALTER TABLE weibotop_table
	ADD category VARCHAR(100)
		AFTER content;

AFTER TABLE weibotop_table
	DROP COLUMN duration_rank;


ALTER TABLE weibotop_table CONVERT TO CHARACTER SET utf8mb4;

-- Create a new user and grant all privileges
grant all privileges on weibotopdb.* TO 'mingda'@'localhost' identified by 'qwerasdf';


-- New format to create a new user and grant all privileges
create user 'mingda'@'localhost' identified by 'qwerasdf';
grant all privileges on weibotopdb.* to 'mingda'@'localhost' with grant option;

