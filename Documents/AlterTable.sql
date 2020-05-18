ALTER TABLE weibotop_table
	ADD category VARCHAR(100)
		AFTER content;

AFTER TABLE weibotop_table
	DROP COLUMN duration_rank;

