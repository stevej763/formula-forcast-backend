CREATE UNIQUE INDEX uk_race_weekend_current_status_race_week
  ON race_weekend_current_status (status)
  WHERE status = 'RACE_WEEK';

CREATE UNIQUE INDEX uk_race_weekend_current_status_live
  ON race_weekend_current_status (status)
  WHERE status = 'LIVE';
