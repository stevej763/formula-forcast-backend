ALTER TABLE public.driver_constructor_mapping DROP COLUMN season;
ALTER TABLE public.driver_constructor_mapping ADD COLUMN season_id BIGINT NOT NULL;

ALTER TABLE public.driver_constructor_mapping ADD CONSTRAINT uk_driver_constructor_mapping_unique_season_driver UNIQUE (season_id, driver_id);

ALTER TABLE public.driver_constructor_mapping ADD CONSTRAINT fk_driver_constructor_mapping_driver_id FOREIGN KEY(driver_id) REFERENCES driver(id);
ALTER TABLE public.driver_constructor_mapping ADD CONSTRAINT fk_driver_constructor_mapping_championship_season_id FOREIGN KEY(season_id) REFERENCES championship_season(id);
ALTER TABLE public.driver_constructor_mapping ADD CONSTRAINT fk_driver_constructor_mapping_constructor_id FOREIGN KEY(constructor_id) REFERENCES constructor(id);
