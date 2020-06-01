ALTER TABLE weibotop_table
	ADD category VARCHAR(100)
		AFTER content;

AFTER TABLE weibotop_table
	DROP COLUMN duration_rank;


ALTER TABLE weibotop_table CONVERT TO CHARACTER SET utf8mb4;

-- Create a new user and grant all privileges
grant all privileges on weibotopdb.* TO 'mingda'@'localhost' identified by 'qwerasdf';

