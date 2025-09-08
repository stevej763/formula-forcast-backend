CREATE TABLE race_weekend (
	id BIGINT GENERATED ALWAYS AS IDENTITY,
	race_weekend_uid UUID NOT NULL,
	race_weekend_start_date DATE NOT NULL,
	race_weekend_end_date DATE NOT NULL,
	race_name TEXT NOT NULL,
	race_location TEXT NOT NULL,

	CONSTRAINT pk_race_weekend_id PRIMARY KEY (id),
	CONSTRAINT uk_race_weekend_race_weekend_uid UNIQUE (race_weekend_uid)
);